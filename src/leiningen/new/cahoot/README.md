# kulu-backend

The main backend service for the Kulu app.

## Set up

* Set up dev and test dbs:
```
$ psql
create database kulu_backend_dev;
\c kulu_backend_dev
create extension "uuid-ossp";

create database kulu_backend_test;
\c kulu_backend_test
create extension "uuid-ossp";
```
* `lein deps` to get all the dependencies
* `lein clj-sql-up migrate`

### Running

In dev, run `bin/server-start`. It sets up some needed env vars and
starts the server with lein. Then visit [http://localhost:3001/](http://localhost:3001/)

#### In heroku

* Push to heroku with `git push heroku master`
* Interact on
  [kulu-backend.herokuapp.com](https://kulu-backend.herokuapp.com).
* To run migrations, `heroku run lein clj-sql-up migrate`
