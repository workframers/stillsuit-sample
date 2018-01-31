(ns stillsuit-sample.load-seattle
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [clojure.tools.logging :as log]
            [datomic.api :as d]))

;; This is a little helper file to load up the sample Seattle dataset from the
;; datomic distribution to a local database. Run it via:
;; lein seattle /path/to/datomic/root

(def default-datomic-uri "datomic:dev://localhost:4334/seattle")

(defn- bail! [err-msg & args]
  (throw (ex-info (apply format (into [err-msg] args))
                  {::bail? true})))

(defn confirm! [datomic-uri]
  (log/warnf "About to delete the database %s. Press return to confirm, or control-c to bail out"
             datomic-uri)
  (flush)
  (print "=> ")
  (flush)
  (read-line))


(def create-database!
  "Try to create a database, then return a connection to it."
  (memoize
   (fn [datomic-uri]
     (when-not (d/create-database datomic-uri)
       ;; Database already existed, prompt to remove it
       (confirm! datomic-uri)
       (when-not (d/delete-database datomic-uri)
         (bail! "Unable to remove old database at %s" datomic-uri))
       (when-not (d/create-database datomic-uri)
         (bail! "Deleted old database but unable to create new one at %s" datomic-uri)))
     (log/warnf "Created database %s" datomic-uri)
     (d/connect datomic-uri))))

(defn parse-edn [file]
  (log/infof "Loading data from file %s..." file)
  (-> file
      slurp
      read-string))

(defn load-single-file!
  [file datomic-uri]
  (let [conn    (create-database! datomic-uri)
        tx-data (parse-edn file)
        result  (-> conn
                    (d/transact tx-data)
                    deref
                    :tx-data)]
    (log/infof "Loaded %d datoms." (count result))))

(defn load-all-data!
  [datomic-home datomic-uri]
  (doseq [filename ["samples/seattle/seattle-schema.edn"
                    "samples/seattle/seattle-data0.edn"
                    "samples/seattle/seattle-data1.edn"]
          :let [f (io/file datomic-home filename)]]
    (when-not (.exists f)
      (bail! "Unable to load file %s!" f))
    (load-single-file! f datomic-uri)))

(defn -main [& args]
  (let [datomic-home (first args)
        datomic-uri  (or (second args) default-datomic-uri)]
    (try
      (when-not datomic-home
        (bail! "Usage: lein seattle /path/to/datomic/root [uri-to-create]"))
      (let [dir (io/file datomic-home)]
        (when (or (nil? dir) (not (.isDirectory dir)))
          (bail! "Datomic home argument %s is not a directory!")))
      (load-all-data! datomic-home datomic-uri)
      (log/infof "Loaded seattle sample data to %s." datomic-uri)
      (System/exit 0)

      (catch Exception e
        (if (some-> e ex-data ::bail?)
          (log/error (.getMessage e))
          (log/error e))
        (System/exit 1)))))
