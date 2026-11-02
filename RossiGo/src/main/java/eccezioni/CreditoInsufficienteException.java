package eccezioni;
public class CreditoInsufficienteException extends RossiGoException {
    public CreditoInsufficienteException(String messaggio) { super(messaggio); }
    public CreditoInsufficienteException(String messaggio, Throwable causa) { super(messaggio, causa); }
}
