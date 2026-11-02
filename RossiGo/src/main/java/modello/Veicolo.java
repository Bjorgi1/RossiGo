package modello;
import org.apache.commons.lang3.Validate;

public abstract class Veicolo {
    private final String codice;
    private double batteria;
    private StatoVeicolo stato;

    public Veicolo(String codice, double batteria) {
        Validate.isTrue(codice != null && codice.matches("(BK|SC)-\\d{4}"), "Codice mezzo non valido");
        Validate.isTrue(Double.isFinite(batteria) && batteria >= 0 && batteria <= 100, "Batteria non valida");
        this.codice = codice;
        this.batteria = batteria;
        this.stato = StatoVeicolo.LIBERO;
    }
    public String getCodice() { return codice; }
    public double getBatteria() { return batteria; }
    public StatoVeicolo getStato() { return stato; }
    public void setBatteria(double batteria) {
        if (!Double.isFinite(batteria)) throw new IllegalArgumentException("Batteria non valida");
        this.batteria = Math.max(0, Math.min(100, batteria));
    }
    public void setStato(StatoVeicolo stato) { this.stato = java.util.Objects.requireNonNull(stato); }
    public abstract double calcolaCosto(int minuti);
    public abstract double consumoBatteriaPerMinuto();
    public void mostraInfo() {
        System.out.printf("%s | %s | batteria %.1f%% | %s%n", getClass().getSimpleName(), codice, batteria, stato);
    }
}
