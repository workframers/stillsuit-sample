(ns stillsuit-sample.main
  (:require [io.pedestal.http :as http]
            [stillsuit-sample.stillsuit :as sss]
            [stillsuit-sample.inkvine :as ssi]
            [stillsuit-sample.catchpocket :as ssc]
            [clojure.tools.logging :as log]
            [clojure.string :as string]))

(defn- validate [args]
  (try
    (let [cmd  (-> args first keyword)
          smap (case cmd
                 :inkvine     (ssi/service-map)
                 :stillsuit   (sss/service-map (second args))
                 :catchpocket (ssc/service-map (nth args 1) (nth args 2))
                 nil)]
      (when (nil? smap)
        (println (string/join ["Usage: lein run (inkvine|stillsuit|catchpocket) [args]"
                               ""
                               "  lein run inkvine"
                               "  lein run stillsuit datomic:dev://localhost:4334/mbrainz-1968-1973"
                               "  lein run catchpocket CONFIG-FILE SCHEMA-FILE"
                               ""]
                              "\n"))
        (System/exit 1))
      [cmd smap])
    (catch Exception e
      (log/error e)
      (System/exit 1))))

(defn -main
  [& args]
  (let [[cmd smap] (validate args)]
    (log/infof "Starting %s server..." (name cmd))
    (-> smap
        http/create-server
        http/start)
    (log/infof "Ready. Serving graphiql at http://localhost:8888/")))
