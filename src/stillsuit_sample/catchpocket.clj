(ns stillsuit-sample.catchpocket
  (:require [clojure.tools.logging :as log]
            [stillsuit.core :as stillsuit]
            [com.walmartlabs.lacinia.pedestal :as lacinia]
            [clojure.tools.reader.edn :as edn]
            [clojure.java.io :as io])
  (:import (java.io IOException PushbackReader)))

(defn load-edn
  "Load edn from an io/reader input (filename or io/resource). If not found, die."
  [source]
  (try
    (with-open [r (io/reader source)]
      (edn/read {:readers *data-readers*} (PushbackReader. r)))
    (catch IOException e
      (throw (RuntimeException. (format "Couldn't open file '%s': %s" source (.getMessage e)))))
    ;; This is the undocumented exception clojure.edn throws if it gets an error parsing an edn file
    (catch RuntimeException e
      (throw (RuntimeException. (format "Error parsing edn file '%s': %s" source (.getMessage e)))))))

(defn service-map
  [config-path schema-path]
  (try
    (let [[config schema] (map load-edn [config-path schema-path])
          context    (stillsuit/app-context config)
          service-fn (fn [] (stillsuit/decorate schema config))]
      (lacinia/service-map service-fn {:graphiql    true
                                       :app-context context}))

    (catch Exception e
      (log/error e))))
