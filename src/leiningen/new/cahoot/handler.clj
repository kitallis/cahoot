(ns {{name}}.handler
    (:require [compojure.api.sweet :refer :all]
              [ring.util.http-response :refer :all]
              [schema.core :as s]))

(s/defschema {{name}} {:id java.util.UUID
                       (s/optional-key :name) s/Str
                       :storage_key s/Str})

(defapi app
  (swagger-ui)
  (swagger-docs
   :title "{{name}} API"
   :description "This is {{name}} API.")
  (swaggered "Invoices"
             :description "Operations for {{name}}"
             (POST* "/invoices" []
                    :return {{name}}
                    :summary " "
                    (created ({:something "foobar"})))))
