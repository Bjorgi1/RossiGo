package eccezioni;
public class UtenteNonTrovatoException extends RossiGoException {
    public UtenteNonTrovatoException(String messaggio) { super(messaggio); }
    public UtenteNonTrovatoException(String messaggio, Throwable causa) { super(messaggio, causa); }
}
