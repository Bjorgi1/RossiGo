package app;
import com.github.lalyos.jfiglet.FigletFont;
import eccezioni.*;
import modello.*;
import servizio.FlottaService;

public class Main {
    @FunctionalInterface interface Scenario { void esegui() throws RossiGoException; }
    private static void scenario(String titolo, Scenario azione) {
        System.out.println("\n--- " + titolo + " ---");
        try { azione.esegui(); }
        catch (BatteriaScaricaException e) { System.out.printf("Attenzione: batteria al %.0f%%. Scegli un altro mezzo.%n",e.getLivelloAttuale()); }
        catch (RossiGoException | IllegalArgumentException e) { System.out.println("Attenzione: " + e.getMessage()); }
    }
    public static void main(String[] args) {
        try { System.out.println(FigletFont.convertOneLine("RossiGo")); }
        catch (Exception e) { System.out.println("=== RossiGo ==="); }
        FlottaService flotta=new FlottaService();
        flotta.registraVeicolo(new Bici("BK-0042",85,7));
        flotta.registraVeicolo(new Bici("BK-0002",8,6));
        flotta.registraVeicolo(new Monopattino("SC-0107",70,25));
        flotta.registraVeicolo(new Monopattino("SC-0002",90,20));
        // Codici fiscali di fantasia con carattere di controllo coerente.
        scenario("1 - Registrazione utenti", () -> {
            flotta.registraUtente(new Utente("U1","Mario","mario@example.com","RSSMRA80A01H501U",20));
            flotta.registraUtente(new Utente("U2","Luca","luca@example.com","VRDLGU85B12F205D",0.50));
            System.out.println("Utenti registrati.");
        });
        scenario("2 - Codice fiscale errato", () -> flotta.registraUtente(new Utente("U3","Anna","anna@example.com","CODICE-SBAGLIATO",10)));
        scenario("3 - Credito insufficiente", () -> flotta.sbloccaVeicolo("SC-0107","U2"));
        scenario("4 - Batteria scarica", () -> flotta.sbloccaVeicolo("BK-0002","U1"));
        scenario("5 - Sblocco bici", () -> flotta.sbloccaVeicolo("BK-0042","U1"));
        scenario("6 - Bici già in uso", () -> flotta.sbloccaVeicolo("BK-0042","U2"));
        scenario("7 - Utente inesistente", () -> flotta.sbloccaVeicolo("SC-0107","U999"));
        scenario("8 - Termina noleggio e ricarica", () -> {
            flotta.terminaNoleggio("BK-0042",20);
            flotta.mettiInRicarica("BK-0042");
            flotta.completaRicarica("BK-0042");
            System.out.println("Bici ricaricata al 100%.");
        });
        scenario("9 - Noleggio monopattino", () -> {
            flotta.sbloccaVeicolo("SC-0107","U1");
            flotta.terminaNoleggio("SC-0107",10);
        });
        scenario("10 - Stima costo", () -> System.out.printf("Stima 15 minuti in bici: %.2f euro%n",flotta.stimaCosto("BK-0042",15,"U1")));
        System.out.println("\n=== STATO FLOTTA ===");
        for (Veicolo v : flotta.getVeicoli()) v.mostraInfo();
        System.out.println("Noleggi completati: " + flotta.getStorico().size());
    }
}
