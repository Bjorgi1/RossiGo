package modello;
import org.apache.commons.lang3.StringUtils;
public class Utente {
    private final String id, nome, email, codiceFiscale;
    private double credito;
    public Utente(String id, String nome, String email, String codiceFiscale, double credito) {
        if (StringUtils.isAnyBlank(id, nome, email, codiceFiscale) || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$") || !Double.isFinite(credito) || credito < 0)
            throw new IllegalArgumentException("Dati utente non validi");
        this.id=id; this.nome=nome; this.email=email; this.codiceFiscale=codiceFiscale.toUpperCase(java.util.Locale.ROOT); this.credito=credito;
    }
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getCodiceFiscale() { return codiceFiscale; }
    public double getCredito() { return credito; }
    public void addebita(double importo) { if (importo < 0 || importo > credito) throw new IllegalArgumentException("Credito non sufficiente"); credito = Math.round((credito-importo)*100.0)/100.0; }
    public void ricaricaCredito(double importo) { if (!Double.isFinite(importo) || importo <= 0) throw new IllegalArgumentException("Importo non valido"); credito += importo; }
}
