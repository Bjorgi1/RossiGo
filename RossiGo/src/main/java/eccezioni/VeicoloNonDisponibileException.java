package eccezioni;
public class VeicoloNonDisponibileException extends RossiGoException {
    public VeicoloNonDisponibileException(String messaggio) { super(messaggio); }
    public VeicoloNonDisponibileException(String messaggio, Throwable causa) { super(messaggio, causa); }
}
