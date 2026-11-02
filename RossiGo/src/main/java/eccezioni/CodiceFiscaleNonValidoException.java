package eccezioni;
public class CodiceFiscaleNonValidoException extends RossiGoException {
    public CodiceFiscaleNonValidoException(String messaggio) { super(messaggio); }
    public CodiceFiscaleNonValidoException(String messaggio, Throwable causa) { super(messaggio, causa); }
}
