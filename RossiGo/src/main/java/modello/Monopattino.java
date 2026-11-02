package modello;
public class Monopattino extends Veicolo {
    private final int velocitaMassima;
    public Monopattino(String codice, double batteria, int velocitaMassima) {
        super(codice, batteria);
        if (!codice.startsWith("SC-") || velocitaMassima < 1) throw new IllegalArgumentException("Dati monopattino non validi");
        this.velocitaMassima = velocitaMassima;
    }
    public int getVelocitaMassima() { return velocitaMassima; }
    public boolean isLimitatoInZonaPedonale() { return velocitaMassima > 6; }
    @Override public double calcolaCosto(int minuti) { return minuti * 0.25; }
    @Override public double consumoBatteriaPerMinuto() { return 0.6; }
}
