(defproject {{name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring/ring-jetty-adapter "1.3.0"]
                 [ring/ring-devel "1.3.0"]
                 [com.duelinmarkers/ring-request-logging "0.2.0"]
                 [ring-middleware-format "0.3.2"]
                 [metosin/compojure-api "0.14.0"]
                 [metosin/ring-http-response "0.4.1"]
                 [metosin/ring-swagger-ui "2.0.17"]
                 [org.clojure/java.jdbc "0.3.4"]
                 [org.postgresql/postgresql "9.3-1100-jdbc4"]
                 [com.jolbox/bonecp "0.8.0.RELEASE"]
                 [org.clojure/tools.logging "0.3.0"]
                 [org.slf4j/slf4j-log4j12 "1.6.6"]
                 [honeysql "0.4.3"]
                 [clj-sql-up "0.3.3"]
                 [honeysql "0.4.3"]
                 [environ "0.5.0"]
                 [javax.servlet/servlet-api "2.5"]]
  :plugins [[lein-ring "0.8.11"]
            [clj-sql-up "0.3.3"]]
  :profiles {:uberjar {:resource-paths ["swagger-ui"]}}
  :uberjar-name "{{name}}-standalone.jar"
  :clj-sql-up {:database ~(str "jdbc:" (System/getenv "DATABASE_URL"))
               :deps [[org.postgresql/postgresql "9.3-1100-jdbc4"]]}
  :resource-paths ["resources"])
