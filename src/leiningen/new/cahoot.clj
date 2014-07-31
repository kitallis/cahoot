(ns leiningen.new.cahoot
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "cahoot"))

(defn cahoot
  "Bootstrap your clojure application with a few essentials."
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' cahoot project.")
    (->files data
             ["bin/"]
             ["resources/"]
             ["src/{{name}}/"]
             ["test/{{name}}/"]
             ["bin/server-start" (render "server-start")]
             ["src/{{sanitized}}/db.clj" (render "db.clj")]
             ["src/{{sanitized}}/web.clj" (render "web.clj")]
             ["resources/log4j.properties" (render "log4j.properties")]
             ["src/{{sanitized}}/handler.clj" (render "handler.clj")]
             ["test/{{sanitized}}/test_helper.clj" (render "test_helper.clj")]
             ["Procfile" (render "Procfile")]
             ["README.md" (render "README.md")]
             [".gitignore" (render "gitignore")]
             ["project.clj" (render "project.clj")])))
