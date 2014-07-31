(ns {{name}}.web
  (:require [com.duelinmarkers.ring-request-logging :refer [wrap-request-logging]]
            [{{name}}.handler :refer [app]]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.format-params :refer [wrap-json-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.reload :refer [wrap-reload]]))

(defn -main [& [port]]
  (let [port (Integer. (or port (System/getenv "port") 3001))]
    (jetty/run-jetty
     (wrap-request-logging (wrap-reload #'app)
                           :param-middleware #(-> % wrap-keyword-params wrap-params wrap-json-params))
     {:port port :join? false})))
