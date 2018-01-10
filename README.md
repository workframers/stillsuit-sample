Sample project demonstrating the use of
[stillsuit](https://github.com/workframers/stillsuit),
[catchpocket](https://github.com/workframers/catchpocket),
and [inkvine](https://github.com/workframers/inkvine).

This mostly just fires up a rudimentary server and a graphiql browser
you can use to issue queries.

# inkvine

`./run inkvine`

# stillsuit

* Install datomic client libs locally
* Install the mbrainz sample database: https://github.com/Datomic/mbrainz-sample
* `./run stillsuit datomic:dev://localhost:4334/mbrainz-1968-1973`
