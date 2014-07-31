(ns kulu-backend.test-helper
  (:require [clj-sql-up.migrate :as m]
            [clojure.java.jdbc :as j]
            [clojure.string :as s :only [replace]]
            [clojure.test :refer :all]
            [environ.core :as environ]
            [kulu-backend.db :as db]))

(defn db-spec
  []
  (db/parse-config (s/replace (environ/env :database-url)
                              #"_dev$" "_test")))

(defn wrap-once
  [f]
  (m/migrate (db-spec))
  (f))

(defn wrap-each
  [f]
  (j/with-db-transaction [conn (db-spec)]
    (j/db-set-rollback-only! conn)
    (with-redefs [db/connection (fn [] conn)]
      (f))))
