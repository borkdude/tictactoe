(ns tictactoe.views.tictactoe
  (:require [tictactoe.views.common :as common]
            [tictactoe.models.model :as tictactoe]
            [noir.content.pages :as pages])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers)) 

(defpartial cell-html [rownum colnum cell with-submit?] 
  [:td 
   [:input {:name (str "b" rownum colnum) 
            :value (str cell)
            :type (if with-submit? 
                    "submit" 
                    "button")}]])
  
(defpartial row-html [rownum row with-submit?]
  [:tr (map-indexed (fn [colnum cell]
                      (cell-html rownum colnum cell with-submit?))
                    row)])
  
(defpartial board-html [board with-submit?]
  (form-to [:post "/"]
           [:table 
            (map-indexed (fn [rownum row]
                           (row-html rownum row with-submit?)) 
                         (tictactoe/get-board))]))
  
(defpartial play-screen []
  [:div 
   [:p "Player " (tictactoe/get-player) ", it is your turn!"]
   (board-html (tictactoe/get-board) true)])

(defpartial winner-screen [winner]
  [:div 
   [:p "The winner is: " winner]
   (board-html (tictactoe/get-board) false)
   (link-to "/" "Reset")])

(defpartial draw-screen []
  [:div
   [:p "It's a draw!"]
   (board-html (tictactoe/get-board) false)
   (link-to "/" "Reset")])

(defpage "/" []
  (tictactoe/reset-game!)
  (common/layout
    (play-screen)))

(defpage [:post "/"] {:as button-pressed}
  (let [button-id (name (first (keys button-pressed)))
        rownr (Integer/parseInt (str (second button-id)))
        colnr (Integer/parseInt (str (nth button-id 2)))]
    (tictactoe/play! rownr colnr)
    (common/layout
      (if-let [winner (tictactoe/winner?)]
        (winner-screen winner)
        (if (tictactoe/full-board?)
          (draw-screen)
          (play-screen))))))
