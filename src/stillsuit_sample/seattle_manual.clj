(ns stillsuit-sample.seattle-manual
  (:require [com.walmartlabs.lacinia :as lacinia]
            [stillsuit.core :as stillsuit]
            [datomic.api :as d]))

;; Sample code used in manual

;; https://github.com/Datomic/datomic-java-examples/blob/master/src/resources/datomic-java-examples/seattle-schema.edn

(def my-schema {:objects {:Community {:fields {:name {:type 'String}}}}
                :queries {:all_communities {:type    :Community
                                            :resolve :sample/all-communities}}})

(defn- all-communities-query [context args value]
  nil)

(def my-db-uri "datomic:dev://localhost:4334/seattle")
(def my-resolvers {:sample/all-communities all-communities-query})
(def my-query "{ all_communities { name } }")

(defn -main [_]
  (let [options   #:stillsuit{:schema     my-schema
                              :config     {}
                              :connection (d/connect my-db-uri)
                              :resolvers  my-resolvers}
        stillsuit (stillsuit/decorate options)
        compiled  (:stillsuit/schema stillsuit)
        context   (:stillsuit/app-context stillsuit)
        result    (lacinia/execute compiled my-query nil context)]
    (println result)))
