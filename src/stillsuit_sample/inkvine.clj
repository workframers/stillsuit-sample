(ns stillsuit-sample.inkvine
  (:require [inkvine.core :as inkvine]
            [com.walmartlabs.lacinia.schema :as schema]
            [com.walmartlabs.lacinia.util :as util]
            [clojure.tools.logging :as log]
            [com.walmartlabs.lacinia.pedestal :as lacinia]))

(defn setup-schema []
  (-> {}
      inkvine/decorate-schema
      (util/attach-resolvers (inkvine/decorate-resolver-map {}))
      (schema/compile)))

(defn service-map
  []
  (fn []
    (-> (setup-schema)
        (lacinia/service-map {:graphiql true}))))
