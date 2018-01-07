(defproject com.workframe/stillsuit-sample "0.1.0-SNAPSHOT"
  :description "sample graphiql FE with pedestal"
  :url "https://github.com/workframers/stillsuit-sample"
  :pedantic? :warn
  :license {:name "EPL"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [com.workframe/stillsuit "0.1.0-SNAPSHOT"]
                 [com.datomic/datomic-pro "0.9.5656"]
                 [org.clojure/tools.logging "0.4.0"]
                 [org.apache.logging.log4j/log4j-core "2.10.0"]
                 [org.apache.logging.log4j/log4j-slf4j-impl "2.10.0"]
                 [com.walmartlabs/lacinia-pedestal "0.6.0-rc-1"]]

  :min-lein-version "2.8.1"

  :main stillsuit-sample.main

  :source-paths ["src"]

  :profiles {:dev  {:plugins      [[lein-ancient "0.6.15"]
                                   [venantius/ultra "0.5.2" :exclusions [org.clojure/clojure]]
                                   [com.jakemccrary/lein-test-refresh "0.22.0"]]
                    :dependencies [[vvvvalvalval/datomock "0.2.0"]]}
             :test {:resource-paths ["test/resources"]}})
