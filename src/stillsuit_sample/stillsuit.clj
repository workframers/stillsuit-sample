(ns stillsuit-sample.stillsuit
  (:require [stillsuit.core :as stillsuit]
            [clojure.tools.logging :as log]
            [com.walmartlabs.lacinia.pedestal :as lacinia])
  (:import (java.util UUID)))

(def default-datomic-uri "datomic:dev://localhost:4334/mbrainz")

(def base-schema
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
  {:stillsuit/datomic-uri default-datomic-uri
   :stillsuit/compile?    true
   :stillsuit/default?    true
   :stillsuit/scalars     {}})

(defn service-map
  [override-db-uri]
  (let [db-uri  (or override-db-uri default-datomic-uri)
        opts    {:stillsuit/datomic-uri db-uri
                 :stillsuit/compile?    true
                 :stillsuit/default?    true
                 :stillsuit/scalars     {}}
        _       (log/infof "Connecting to datomic at %s..." db-uri)
        context (stillsuit/app-context opts)]
    (-> base-schema
        (stillsuit/decorate opts)
        (lacinia/service-map {:graphiql    true
                              :app-context context}))))
