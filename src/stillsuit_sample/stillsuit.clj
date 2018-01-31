(ns stillsuit-sample.stillsuit
  (:require [stillsuit.core :as stillsuit]
            [clojure.tools.logging :as log]
            [com.walmartlabs.lacinia.pedestal :as lacinia]))

;; TODO: fix this

(defn service-map
  [schema connection]
  (let [opts      {;:stillsuit/datomic-uri db-uri
                   :stillsuit/compile?    true
                   :stillsuit/default?    true
                   :stillsuit/trace?      true
                   :stillsuit/scalars     {}}
        decorated (stillsuit/decorate opts)]
    (lacinia/service-map (:stillsuit/schema decorated)
                         {:graphiql    true
                          :app-context (:stillsuit/app-context decorated)})))
