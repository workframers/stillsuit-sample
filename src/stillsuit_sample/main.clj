(ns stillsuit-sample.main
  (:require [io.pedestal.http :as http]
            [com.walmartlabs.lacinia.pedestal :as lacinia]
            [clojure.java.browse :refer [browse-url]]
            [stillsuit.core :as stillsuit]))

(def base
  {:queries {:hello
             ;; String is quoted here; in EDN the quotation is not required
             {:type    'String
              :resolve (constantly "world")}}})

(def opts
  {:stillsuit/compile? true
   :stillsuit/default? true})

(defn fire-it-up [base-schema stillsuit-options]
  (-> base-schema
      (stillsuit/decorate stillsuit-options)
      (lacinia/service-map {:graphiql true})
      http/create-server
      http/start))

(defn -main
  [& args]
  (println "\nCreating your server...")
  (fire-it-up base opts)
  (browse-url "http://localhost:8888/"))
