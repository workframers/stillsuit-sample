(ns stillsuit-sample.main
  (:require [io.pedestal.http :as http]
            [stillsuit-sample.stillsuit :as sss]
            [stillsuit-sample.inkvine :as ssi]
            [clojure.tools.logging :as log])
  (:import (java.util UUID)))

(defn -main
  [& args]
  (let [cmd  (-> args first keyword)
        smap (case cmd
               :inkvine   (ssi/service-map)
               :stillsuit (sss/service-map (second args))
               nil)]
    (when (nil? smap)
      (println "Usage: lein run (inkvine|stillsuit) [stillsuit-db-uri]")
      (System/exit 1))

    (log/infof "Starting %s server..." (name cmd))
    (-> smap
        http/create-server
        http/start)
    (log/infof "Ready. Serving graphiql at http://localhost:8888/")))
