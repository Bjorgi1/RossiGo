package eccezioni;
public class BatteriaScaricaException extends RossiGoException {
    public BatteriaScaricaException(String messaggio) { super(messaggio); }
    public BatteriaScaricaException(String messaggio, Throwable causa) { super(messaggio, causa); }
    private double livelloAttuale = -1;
    public BatteriaScaricaException(String messaggio, double livelloAttuale) { super(messaggio); this.livelloAttuale=livelloAttuale; }
    public double getLivelloAttuale() { return livelloAttuale; }

}
