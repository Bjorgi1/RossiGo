package modello;
import java.time.LocalDateTime;
public class Noleggio {
    private final Utente utente;
    private final Veicolo veicolo;
    private final LocalDateTime inizio;
    private LocalDateTime fine;
    private int minutiUsati;
    private double costo;
    public Noleggio(Utente utente, Veicolo veicolo) { this.utente=utente; this.veicolo=veicolo; this.inizio=LocalDateTime.now(); }
    public void termina(int minuti, double costo) { this.minutiUsati=minuti; this.costo=costo; this.fine=LocalDateTime.now(); }
    public Utente getUtente() { return utente; }
    public Veicolo getVeicolo() { return veicolo; }
    public LocalDateTime getInizio() { return inizio; }
    public LocalDateTime getFine() { return fine; }
    public int getMinutiUsati() { return minutiUsati; }
    public double getCosto() { return costo; }
}
