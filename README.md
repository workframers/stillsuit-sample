Sample project demonstrating the use of
[stillsuit](https://github.com/workframers/stillsuit),
[catchpocket](https://github.com/workframers/catchpocket),
and [inkvine](https://github.com/workframers/inkvine).

This mostly just fires up a rudimentary server and a graphiql browser
you can use to issue queries.

# Note

This repository is currently a little behind stillsuit and catchpocket, and
the stillsuit example code is barely present - sorry about that! It does
demonstrate how to hook up stillsuit to a lacinia-pedestal server, however.

# Installation

Before you run this you'll need to
[install the datomic peer libraries locally](https://docs.datomic.com/on-prem/integrating-peer-lib.html#maven-setup)
and [run a local transactor](https://docs.datomic.com/on-prem/dev-setup.html#run-dev-transactor)
to host the databases. The sample runs with [tools.deps]().

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
of the data [online on github](https://github.com/Datomic/datomic-java-examples/tree/master/src/resources/datomic-java-examples).
The sample project includes a little utility namespace to load the data up
from the files in the datomic distribution. To load the data, type in this:

```
clojure -A:load-seattle /Users/tim/datomic/datomic-pro-0.9.5561
```

...where `/Users/tim/datomic/datomic-pro-0.9.5561` is the path to your datomic
installation directory. The seattle database will be loaded at the URI
`datomic:dev://localhost:4334/seattle`.

# Sample applications: stillsuit

In the stillsuit applications, a hand-crafted stillsuit configuration file is used
to access the databases. To start this, load the appropriate database as above and run:

```
clojure -A:stillsuit seattle
```

...or

```
clojure -A:stillsuit mbrainz
```

# Sample applications: catchpocket

The catchpocket samples will require you to run catchpocket before you start
the sample application to extract a configuration file from them. The catchpocket
configuration files for the two databases live in the `catchpocket` directory.

## seattle

Run catchpocket to generate the stillsuit schema:

```
% clojure -A:catchpocket-generate ./catchpocket/seattle.edn
21:07:09 INFO  c.g.core - Connecting to datomic:dev://localhost:4334/seattle...
[...]
21:07:15 INFO  c.g.core - Saving schema to target/seattle/stillsuit.edn...
21:07:15 INFO  c.g.core - Finished generation.
```

Now run the sample app to serve graphiql:

```
% clojure -A:catchpocket-serve seattle
19:39:33 INFO  s.main - Connecting to datomic at datomic:dev://localhost:4334/seattle...
19:39:36 INFO  s.main - Starting catchpocket server...
19:39:37 INFO  s.main - Serving graphiql at: http://localhost:8888/graphiql/index.html
19:39:37 INFO  s.main - GraphQL Voyager:     http://localhost:8888/voyager/index.html
19:39:37 INFO  s.main - Ready.
```

## mbrainz

Run catchpocket:
```
% clojure -A:catchpocket-generate ./catchpocket/mbrainz.edn
21:11:58 INFO  c.g.core - Connecting to datomic:dev://localhost:4334/mbrainz-1968-1973...
[...]
21:12:08 INFO  c.g.core - Saving schema to target/mbrainz/stillsuit.edn...
21:12:08 INFO  c.g.core - Finished generation.
```

Run the app:

```
% clojure -A:catchpocket-serve mbrainz
19:36:29 INFO  s.main - Connecting to datomic at datomic:dev://localhost:4334/mbrainz-1968-1973...
19:36:32 INFO  s.main - Starting catchpocket server...
19:36:33 INFO  s.main - Serving graphiql at: http://localhost:8888/graphiql/index.html
19:36:33 INFO  s.main - GraphQL Voyager:     http://localhost:8888/voyager/index.html
19:36:33 INFO  s.main - Ready.
```
