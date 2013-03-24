(defproject tictactoe "0.1.1-SNAPSHOT"
  :description "Tictactoe using ring, compojure, lib-noir and hiccup"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [lib-noir "0.4.9"]
                 [compojure "1.1.5"]
                 [ring-server "0.2.7"]
                 [hiccup "1.0.2"]]
  :ring {:handler tictactoe/handler.war-handler,
         :init tictactoe.handler/init,
         :destroy tictactoe.handler/destroy}
  :profiles {:production
             {:ring
              {:open-browser? false, :stacktraces? false, :auto-reload? false}},
             :dev
             {:dependencies [[ring-mock "0.1.3"] [ring/ring-devel "1.1.8"]]}}
  :url "http://example.com/FIXME"
  :plugins [[lein-ring "0.8.3"]]
  :min-lein-version "2.0.0")

