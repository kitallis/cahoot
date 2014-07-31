(ns {{name}}.db
  (:require [environ.core :as environ]
            [clojure.java.jdbc :as j])
  (:import [com.jolbox.bonecp BoneCPDataSource]))

(defn parse-config
  "Parses username/pwd etc. from a DB URL"
  [url]
  (let [[_ user password host port db]
        (re-find #"^.*://(.*):(.*)@(.*):(.*)/(.*)$" url)]
    {:classname "org.postgresql.Driver"
     :subprotocol "postgresql"
     :subname (str "//" host ":" port "/" db)
     :make-pool? true
     :user user
     :password password}))

(defn spec
  []
  (parse-config (environ/env :database-url)))

(def min-pool 3)

(def max-pool 12)

(defn pool
  [spec]
  (let [partitions 3
        cpds (doto (BoneCPDataSource.)
               (.setJdbcUrl (str "jdbc:" (:subprotocol spec) ":" (:subname spec)))
               (.setUsername (:user spec))
               (.setPassword (:password spec))
               (.setMinConnectionsPerPartition (inc (int (/ min-pool partitions))))
               (.setMaxConnectionsPerPartition (inc (int (/ max-pool partitions))))
               (.setPartitionCount partitions)
               (.setStatisticsEnabled true)
               ;; test connections every 25 mins (default is 240):
               (.setIdleConnectionTestPeriodInMinutes 25)
               ;; allow connections to be idle for 3 hours (default is 60 minutes):
               (.setIdleMaxAgeInMinutes (* 3 60))
               ;; consult the BoneCP documentation for your database:
               (.setConnectionTestStatement "/* ping *\\/ SELECT 1"))]
    {:datasource cpds}))

(def pooled-db (delay (pool (spec))))

(defn connection
  []
  @pooled-db)

(defn exec-transaction
  [jdbc-fn args]
  (j/with-db-transaction [conn (connection)]
    (apply (partial jdbc-fn conn) args)))

(defn query
  [& args]
  (exec-transaction j/query args))

(defn insert!
  [& args]
  (exec-transaction j/insert! args))
