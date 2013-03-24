(ns tictactoe.controller
  (:use compojure.core)
  (:require [compojure.core :as compojure]
            [tictactoe.view :as view]
            [tictactoe.model :as model]))

(defn start-page []
  (model/reset-game!)
  (view/play-screen))

(defn turn-page [button-pressed]
  (let [button-id (name (first (keys button-pressed)))
        rownr (Integer/parseInt (str (second button-id)))
        colnr (Integer/parseInt (str (nth button-id 2)))]
    (model/play! rownr colnr)
    (if-let [winner (game/winner?)]
      (view/winner-screen winner)
      (if (game/full-board?)
        (view/draw-screen)
        (view/play-screen)))))

(defroutes tictactoe-routes
  (GET "/" [] (start-page))
  (POST "/" {button-pressed :params} (turn-page button-pressed)))
