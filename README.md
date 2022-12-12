# JokesDB


in this project I would be satisfied with a grade 4. I felt more and more lost as the module progressed, and clicked off as time went on. Coding felt really hard this time as I didn't know what I was doing exactly.



A minimal application to play with JPA and spring data topics.

## 🐳 Postgres with Docker

A simple solution expects a https://www.baeldung.com/linux/docker-run-without-sudo[running docker without sudo].
To get a Database connection (and associated JPA-autocomplete), run `./gradlew bootRun` (it will hang).

Alternatively launch a postgres docker container similar to the `dockerPostgres`-Task in `build.gradle` by hand.

## 🪣 IntelliJ Database View

View | Tool Windows | Database | + | Data Source from URL
```
jdbc:postgresql://localhost:5432/localdb
User: localuser, Password: localpass
```
## Requirements
Basierend auf der JokesDB (https://gitlab.com/kkseven/bbw-151-jokesdb) sollen gängige EE-Erweiterungen implementiert werden. Die Schwerpunktthemen können selbst gewählt werden, aber "min"-Anforderungen werden erwartet.

. WebFlux-Client-Anbindung an https://jokeapi.dev (read-only, d.h. ohne `/submit`)
- min: hardcoded client für ein paar Usecases
- think-tank: generischer Jokes-Client, mehrere Parameter (zB `lang` werden supportet), Rate-Limit-Awareness
. Jokes werden local cached.
- min: Remote Jokes werden in der lokalen DB gespeichert und Duplikate werden verhindert.
- think-tank: webclient-connection-pooling, explizites `@Transactional`-Handling
. Die Datenbank wird durch sinnvolle Felder erweitert und Jokes können mit einem Sterne-Rating pro User versehen werden.
- min: Technische Datenbankfelder creation-timestamp, modified-timestamp (and friends) per Tabelle, joke-ratings in einer zweiten Tabelle
- think-tank: Techfelder in einer Basis-Klasse, weitere Columns wie "Category" entsprechend jokeapi.dev, Ratings per User und User login via BasicAuth
. Verwendet sinnvolle https://projectlombok.org/[Lombok] Features
- min: keine Getter/Setters in Code
- besser: Loggers, ToString,...
. JUnit Testing mit `@SpringBootTest` und https://assertj.github.io/doc/[AssertJ]
- min: `WebTestClient` Tests der eigenen Endpunkte
- think-tank: automatisierte CI-Tests bei jedem Commit und sinnvolle Coverage
. Dokumentation
- min: README mit einer Selbsteinschätzung, Diskussion der verwendeten Features und wo der Fokus gesetzt wurde.
- think-tank: ein kleines UI mit dem Joke-of-the-day oder einer ähnlichen Beispielanwendung
