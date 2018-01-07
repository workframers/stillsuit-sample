(ns stillsuit-sample.main
  (:require [io.pedestal.http :as http]
            [com.walmartlabs.lacinia.pedestal :as lacinia]
            [clojure.java.browse :refer [browse-url]]
            [datomic.api :as d]
            [stillsuit.core :as stillsuit]
            [clojure.tools.logging :as log])
  (:import (java.util UUID)))

(def datomic-uri "datomic:dev://localhost:4334/mbrainz")

(def base
  {:queries {:hello
             ;; String is quoted here; in EDN the quotation is not required
             {:type    'String
              :resolve (constantly "world")}
             :id
             {:type    :JavaUUID
              :resolve (fn [a c v] (UUID/randomUUID))}
             :now
             {:type    :JavaDate
              :resolve (fn [a c v] (java.util.Date.))}}})

(def opts
  {:stillsuit/datomic-uri datomic-uri
   :stillsuit/compile?    true
   :stillsuit/default?    true
   :stillsuit/scalars     {}})

(defn fire-it-up [base-schema stillsuit-options]
  (-> base-schema
      (stillsuit/decorate stillsuit-options)
      log/spy
      (lacinia/service-map {:graphiql    true
                            :app-context (stillsuit/app-context stillsuit-options)})
      http/create-server
      http/start)
  (log/info "Ready."))

(defn -main
  [& args]
  (log/infof "Connecting to %s..." datomic-uri)
  (log/infof "Creating your server...")
  (fire-it-up base opts))
;(browse-url "http://localhost:8888/"))
