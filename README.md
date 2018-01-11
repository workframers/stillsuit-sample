Sample project demonstrating the use of
[stillsuit](https://github.com/workframers/stillsuit),
[catchpocket](https://github.com/workframers/catchpocket),
and [inkvine](https://github.com/workframers/inkvine).

This mostly just fires up a rudimentary server and a graphiql browser
you can use to issue queries.

# inkvine

`lein run inkvine`

# stillsuit

* Install datomic client libs locally
* Install the mbrainz sample database: https://github.com/Datomic/mbrainz-sample
* `lein run stillsuit datomic:dev://localhost:4334/mbrainz-1968-1973`

# catchpocket

* Install datomic client libs locally
* Install the mbrainz sample database: https://github.com/Datomic/mbrainz-sample
* Check out [catchpocket](https://github.com/workframers/catchpocket)
* From the catchpocket root, run against the mbrainz database:
  * `lein run catchpocket config/mbrainz.edn`
* From the stillsuit-sample directory, run against the generated EDN:
* `lein run catchpocket ../catchpocket/config/mbrainz.edn ../catchpocket/target/mbrainz/schema.edn`
