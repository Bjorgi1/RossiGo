package servizio;
import modello.*;
import eccezioni.*;
import java.util.*;

public class FlottaService {
    private final Map<String, Veicolo> veicoli = new LinkedHashMap<>();
    private final Map<String, Utente> utenti = new LinkedHashMap<>();
    private final Map<String, Noleggio> attivi = new HashMap<>();
    private final List<Noleggio> storico = new ArrayList<>();

    public void registraVeicolo(Veicolo v) {
        Objects.requireNonNull(v);
        if (veicoli.putIfAbsent(v.getCodice(), v) != null) throw new IllegalArgumentException("Veicolo già registrato");
    }
    public void registraUtente(Utente u) throws CodiceFiscaleNonValidoException {
        Objects.requireNonNull(u);
        if (!codiceFiscaleValido(u.getCodiceFiscale())) throw new CodiceFiscaleNonValidoException("Codice fiscale non valido: " + u.getNome());
        if (utenti.putIfAbsent(u.getId(), u) != null) throw new IllegalArgumentException("ID utente già registrato");
    }
    // Controlla struttura, caratteri ammessi e carattere finale di controllo.
    private boolean codiceFiscaleValido(String cf) {
        if (cf == null || !cf.matches("[A-Z]{6}[0-9LMNPQRSTUV]{2}[ABCDEHLMPRST][0-9LMNPQRSTUV]{2}[A-Z][0-9LMNPQRSTUV]{3}[A-Z]")) return false;
        String pari="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String dispari="BAKPLCQDREVOSFTGUHMINJWZYX";
        int[] numeriDispari={1,0,5,7,9,13,15,17,19,21};
        int somma=0;
        for (int i=0;i<15;i++) {
            char c=cf.charAt(i);
            if (i%2==0) somma += Character.isDigit(c) ? numeriDispari[c-'0'] : dispari.indexOf(c);
            else somma += Character.isDigit(c) ? c-'0' : pari.indexOf(c);
        }
        return cf.charAt(15)==(char)('A'+somma%26);
    }
    public Veicolo trovaVeicolo(String codice) {
        Veicolo v=veicoli.get(codice);
        if (v==null) throw new IllegalArgumentException("Veicolo inesistente: " + codice);
        return v;
    }
    public Utente trovaUtente(String id) throws UtenteNonTrovatoException {
        Utente u=utenti.get(id);
        if (u==null) throw new UtenteNonTrovatoException("Utente non trovato: " + id);
        return u;
    }
    public void sbloccaVeicolo(String codice, String idUtente) throws RossiGoException {
        Veicolo v=trovaVeicolo(codice);
        Utente u=trovaUtente(idUtente);
        if (v.getStato()!=StatoVeicolo.LIBERO) throw new VeicoloNonDisponibileException("Il mezzo " + codice + " non è disponibile.");
        if (v.getBatteria()<15) throw new BatteriaScaricaException("Batteria insufficiente: " + v.getBatteria() + "%",v.getBatteria());
        if (u.getCredito()<1) throw new CreditoInsufficienteException("Credito insufficiente per lo sblocco (minimo 1 euro).");
        if (attivi.values().stream().anyMatch(n -> n.getUtente().getId().equals(idUtente))) throw new VeicoloNonDisponibileException("Hai già un noleggio attivo.");
        v.setStato(StatoVeicolo.IN_USO);
        attivi.put(codice,new Noleggio(u,v));
        System.out.println("Sbloccato " + codice + " per " + u.getNome());
    }
    public double terminaNoleggio(String codice, int minutiUsati) throws RossiGoException {
        if (minutiUsati<=0) throw new IllegalArgumentException("I minuti devono essere positivi");
        Noleggio n=attivi.get(codice);
        if (n==null) throw new VeicoloNonDisponibileException("Nessun noleggio attivo per " + codice);
        Veicolo v=n.getVeicolo();
        double costo=Math.round((1+v.calcolaCosto(minutiUsati))*100.0)/100.0;
        if (n.getUtente().getCredito()<costo) throw new CreditoInsufficienteException(String.format("Servono %.2f euro per terminare il noleggio.",costo));
        n.getUtente().addebita(costo);
        v.setBatteria(v.getBatteria()-minutiUsati*v.consumoBatteriaPerMinuto());
        v.setStato(StatoVeicolo.LIBERO);
        n.termina(minutiUsati,costo);
        storico.add(n);
        attivi.remove(codice);
        System.out.printf("Noleggio terminato: %s, %d minuti, %.2f euro. Credito residuo: %.2f euro%n", codice,minutiUsati,costo,n.getUtente().getCredito());
        return costo;
    }
    public void mettiInRicarica(String codice) throws VeicoloNonDisponibileException {
        Veicolo v=trovaVeicolo(codice);
        if (v.getStato()==StatoVeicolo.IN_USO) throw new VeicoloNonDisponibileException("Non puoi ricaricare un mezzo in uso.");
        v.setStato(StatoVeicolo.IN_RICARICA);
        System.out.println("Mezzo " + codice + " messo in ricarica.");
    }
    public void completaRicarica(String codice) {
        Veicolo v=trovaVeicolo(codice);
        if (v.getStato()!=StatoVeicolo.IN_RICARICA) throw new IllegalStateException("Il mezzo non è in ricarica");
        v.setBatteria(100);
        v.setStato(StatoVeicolo.LIBERO);
    }
    public double stimaCosto(String codice,int minuti,String idUtente) throws RossiGoException {
        if (minuti<=0) throw new IllegalArgumentException("Durata non valida");
        Veicolo v=trovaVeicolo(codice);
        Utente u=trovaUtente(idUtente);
        double costo=Math.round((1+v.calcolaCosto(minuti))*100.0)/100.0;
        if (u.getCredito()<costo) throw new CreditoInsufficienteException(String.format("Costo previsto %.2f euro, credito %.2f euro",costo,u.getCredito()));
        return costo;
    }
    public List<Noleggio> getStorico() { return Collections.unmodifiableList(storico); }
    public Collection<Veicolo> getVeicoli() { return Collections.unmodifiableCollection(veicoli.values()); }
}
