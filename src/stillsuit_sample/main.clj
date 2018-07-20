(ns stillsuit-sample.main
  (:require [io.pedestal.http :as http]
            [stillsuit.lib.util :as u]
            [stillsuit-sample.stillsuit :as sss]
            [stillsuit-sample.catchpocket :as ssc]
            [clojure.tools.logging :as log]
            [clojure.string :as string]
            [datomic.api :as d]))

(def db-uris
  {:mbrainz "datomic:dev://localhost:4334/mbrainz-1968-1973"
   :seattle "datomic:dev://localhost:4334/seattle"})

(def paths
  {:stillsuit   {:mbrainz "stillsuit/mbrainz.edn"
                 :seattle "stillsuit/seattle.edn"}
   :catchpocket {:seattle "target/seattle/stillsuit.edn"
                 :mbrainz "target/mbrainz/stillsuit.edn"}})

(defn- usage! [& args]
  (when args
    (println (apply format args)))
  (println (string/join "\n" [(format "Usage: clojure -m stillsuit-sample.main (%s) (%s)"
                                      (string/join "|" (->> paths keys (map name) sort))
                                      (string/join "|" (->> db-uris keys (map name) sort)))
                              "Example:"
                              ""
                              "  clojure -m stillsuit-sample.main stillsuit seattle"
                              ""]))

  (System/exit 1))

(defn- validate [args]
  (try
    (let [lib     (some-> args first keyword)
          db-name (some-> args second keyword)]
      (if (or (nil? lib) (nil? db-name) (not= (count args) 2))
        (usage!)
        (if-let [path (get-in paths [lib db-name])]
          (if-let [config (u/load-edn-file path)]
            (let [db-uri (get db-uris db-name)]
              (log/infof "Connecting to datomic at %s..." db-uri)
              (let [conn (d/connect (get db-uris db-name))
                    smap (-> (case lib
                               :stillsuit (sss/service-map config conn)
                               :catchpocket (ssc/service-map config conn)
                               nil)
                             (assoc ::http/resource-path "/public"))]
                [lib config smap]))
            ;; Else config not loaded
            (usage!))
          ;; Else path not found
          (usage! "Can't find path for arguments %s %s!" lib db-name))))
    (catch Exception e
      (log/error e)
      (System/exit 1))))

(defn -main
  [& args]
  (let [[lib config smap] (validate args)]
    (log/infof "Starting %s server..." (name lib))
    (-> smap
        http/create-server
        http/start)
    (log/infof "Serving graphiql at: http://localhost:8888/graphiql/index.html")
    (log/infof "GraphQL Voyager:     http://localhost:8888/voyager/index.html")
    (log/infof "Ready.")))
