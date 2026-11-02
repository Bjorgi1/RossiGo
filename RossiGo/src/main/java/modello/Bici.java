package modello;
public class Bici extends Veicolo {
    private final int numeroMarce;
    public Bici(String codice, double batteria, int numeroMarce) {
        super(codice, batteria);
        if (!codice.startsWith("BK-") || numeroMarce < 1) throw new IllegalArgumentException("Dati bici non validi");
        this.numeroMarce = numeroMarce;
    }
    public int getNumeroMarce() { return numeroMarce; }
    @Override public double calcolaCosto(int minuti) { return minuti * 0.15; }
    @Override public double consumoBatteriaPerMinuto() { return 0.3; }
}
