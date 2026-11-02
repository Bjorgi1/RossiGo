# RossiGo

Progetto backend Java 25 e Maven per il noleggio di bici e monopattini elettrici.

## Requisiti
- JDK 25
- Maven 3.9+ oppure Maven integrato in IntelliJ IDEA

## Avvio
Aprire la cartella del progetto in IntelliJ, importare il `pom.xml` come progetto Maven e avviare `app.Main`.

Da terminale:
```bash
mvn clean compile
mvn exec:java
```

## Librerie
- **JFiglet (`com.github.lalyos:jfiglet`)**: mostra il titolo RossiGo in ASCII art all'avvio di `app.Main`.
- **Apache Commons Lang3 (`org.apache.commons:commons-lang3`)**: `StringUtils` valida i campi dell'utente e `Validate` controlla il formato del codice veicolo.

## Architettura
- `modello`: Veicolo astratto, Bici, Monopattino, StatoVeicolo, Utente, Noleggio.
- `eccezioni`: RossiGoException e cinque eccezioni specializzate.
- `servizio`: FlottaService, gestisce registrazioni, sblocco, fine noleggio, ricarica, stima e storico.
- `app`: Main e simulazione.

## Scenari simulati
1. Registrazione utenti validi.
2. Registrazione con codice fiscale errato (eccezione).
3. Sblocco con credito sotto 1 euro (eccezione).
4. Sblocco con batteria sotto il 15% (eccezione).
5. Sblocco bici riuscito.
6. Tentativo di sbloccare bici già in uso (eccezione).
7. Sblocco con ID utente inesistente (eccezione).
8. Fine noleggio e ricarica.
9. Noleggio monopattino riuscito.
10. Stima costo e stato flotta.

## Tariffe
Sblocco: 1 euro; bici: 0,15 euro/minuto, 0,3% batteria/minuto; monopattino: 0,25 euro/minuto, 0,6% batteria/minuto.

## Note
Il codice fiscale viene controllato per struttura e carattere di controllo, non per corrispondenza anagrafica. I dati sono in memoria e vengono azzerati al riavvio; SQLite è un bonus non incluso. Se il credito non basta alla chiusura, il noleggio rimane attivo finché l'utente ricarica il credito.

## GitHub
Creare un repository chiamato `COGNOME_rossigo` (sostituire COGNOME con il proprio cognome) e caricare tutti i file tranne `target/`.
