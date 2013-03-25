(defproject tictactoe "0.1.1"
  :description "Tictactoe using ring, compojure, lib-noir and hiccup"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [lib-noir "0.4.9"]
                 [compojure "1.1.5"]
                 [ring-server "0.2.7"]
                 [hiccup "1.0.2"]]
  :ring {:handler tictactoe.handler/war-handler}
  :profiles {:production
             {:ring
              {:open-browser? false, :stacktraces? false, :auto-reload? false}}}
  :plugins [[lein-ring "0.8.3"]]
  :min-lein-version "2.0.0")

