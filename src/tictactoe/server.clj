(ns tictactoe.server
  (:require [noir.server :as server])
  (:gen-class))

;;(server/load-views "src/tictactoe/views/")
;;the above is replaced with below, because packaging didn't work properly
(require 'tictactoe.views.tictactoe)

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'tictactoe})))


