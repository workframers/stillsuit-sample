(ns stillsuit-sample.catchpocket
  (:require [clojure.tools.logging :as log]
            [stillsuit.core :as stillsuit]
            [com.walmartlabs.lacinia.pedestal :as lacinia]))

(defn service-map
  [schema connection]
  (try
    (let [decorated  (stillsuit/decorate #:stillsuit{:schema     schema
                                                     :connection connection
                                                     :config     {}})
          context    (:stillsuit/app-context decorated)]
      (lacinia/service-map (:stillsuit/schema decorated)
                           {:graphiql    true
                            :app-context context}))

    (catch Exception e
      (log/error e))))
