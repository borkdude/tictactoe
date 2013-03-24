(ns tictactoe.handler
  (:use tictactoe.controller
        compojure.core)
  (:require [noir.util.middleware :as noir-middleware]
            [compojure.route :as route]))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

;;append your application routes to the all-routes vector
(def all-routes [tictactoe-routes app-routes])

(def app (-> all-routes
             noir-middleware/app-handler
             ;;add your middlewares here
             ))

(def war-handler (noir-middleware/war-handler app))
