Sample project demonstrating the use of
[stillsuit](https://github.com/workframers/stillsuit),
[catchpocket](https://github.com/workframers/catchpocket),
and [inkvine](https://github.com/workframers/inkvine).

This mostly just fires up a rudimentary server and a graphiql browser
you can use to issue queries.

# Installation

Before you run this you'll need to
[install the datomic peer libraries locally](https://docs.datomic.com/on-prem/integrating-peer-lib.html#maven-setup)
and [run a local transactor](https://docs.datomic.com/on-prem/dev-setup.html#run-dev-transactor)
to host the databases.

The sample application runs against two main open-source databases, which
you can set up as follows:

## mbrainz setup

This database uses the [mbrainz-sample](https://github.com/Datomic/mbrainz-sample)
database. You should
[follow the github instructions](https://github.com/Datomic/mbrainz-sample#getting-the-data)
to load up the datomic schema and its data. Note that the sample application assumes
you've installed datomic at its suggested location:
`datomic:dev://localhost:4334/mbrainz-1968-1973`.

## seattle setup

This database is included in the datomic distribution; there is also a version
of this data [online on github](https://github.com/Datomic/datomic-java-examples/tree/master/src/resources/datomic-java-examples).
The sample project includes a little utility namespace to load the data up
from the files in the datomic distribution. To load the data, type in this:

```
clojure -m stillsuit-sample.load-seattle /Users/tim/datomic/datomic-pro-0.9.5561
```

...where `/Users/tim/datomic/datomic-pro-0.9.5561` is the path to your datomic
installation directory. The seattle database will be loaded at the URI
`datomic:dev://localhost:4334/seattle`.

# Sample applications: stillsuit

In the stillsuit applications, a hand-crafted stillsuit configuration file is used
to access the databases. To start this, run:

```
clojure -m stillsuit-sample.main stillsuit seattle
```

...or

```
clojure -m stillsuit-sample.main stillsuit mbrainz
```

# Sample applications: catchpocket

The catchpocket samples will require you to run catchpocket before you start
the sample application to extract a configuration file from them. The catchpocket
configuration files for the two databases live in the `catchpocket` directory.

## seattle

Run catchpocket:
```
$ clojure -R:catchpocket -m catchpocket.main ./catchpocket/seattle.edn
14:53:28 INFO  c.g.core - Connecting to datomic:dev://localhost:4334/seattle...
[...]
14:53:32 INFO  c.g.core - Saved schema to target/seattle/stillsuit.edn
14:53:32 INFO  c.g.core - Finished generation.
```

Now run the sample app

## mbrainz

Run catchpocket:
```
$ clojure -R:catchpocket -m catchpocket.main ./catchpocket/mbrainz.edn
14:54:24 INFO  c.g.core - Connecting to datomic:dev://localhost:4334/mbrainz-1968-1973...
[...]
14:54:27 INFO  c.g.core - Saved schema to target/mbrainz/stillsuit.edn
14:54:27 INFO  c.g.core - Finished generation.
```

Run the app:

```

```
