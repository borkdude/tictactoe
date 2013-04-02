(ns tictactoe.model
  (:require [noir.session :as session]))

(def empty-board [[\- \- \-]
                  [\- \- \-]
                  [\- \- \-]])

(def init-state {:board empty-board :player \X})

(defn reset-game! []
  (session/put! :game-state init-state))

(defn get-board []
  (:board (session/get :game-state)))

(defn get-board-cell 
  ([row col]
    (get-board-cell (get-board) row col))
  ([board row col]
    (get-in board [row col])))

(defn get-player []
  (:player (session/get :game-state)))

(defn other-player 
  ([] (other-player (get-player)))
  ([player] (if (= player \X) \O \X))) 

(defn winner-in-rows? [board player]
  (boolean (some (fn [row] (every? (fn [c] (= c player)) row)) board)))

(defn transposed-board [board]
  (vec (apply map vector board)))

(defn winner-in-cols? [board player]
  (winner-in-rows? (transposed-board board) player))

(defn winner-in-diagonals? [board player]
  (let [diag-coords [[[0 0] [1 1] [2 2]]
                     [[0 2] [1 1] [2 0]]]]
    (boolean (some (fn [coords] 
                     (every? (fn [coord] 
                               (= player (apply get-board-cell board coord))) 
                             coords))
                   diag-coords))))

(defn winner?
  "checks if there is a winner. when called with no args, checks for player X and player O.
returns the character for the winning player, nil if there is no winner"
  ([] (winner? (get-board)))
  ([board]
    (boolean (or (winner? board \X)
                 (winner? board \O))))
  ([board player]
    (if (or (winner-in-rows? board player)
            (winner-in-cols? board player)
            (winner-in-diagonals? board player))
      player)))

(defn full-board?
  ([] (full-board? (get-board)))
  ([board] (let [all-cells (apply concat board)]
             (not-any? #(= % \-) all-cells))))

(defn new-state [row col old-state]
  (if (and (= (get-board-cell (:board old-state) row col) \-)
           (not (winner? (:board old-state))))
    {:board (assoc-in (:board old-state) [row col] (:player old-state))
     :player (other-player (:player old-state))}
    old-state))

(defn play! [row col]
  (session/swap! (fn [session-map]
                   (assoc session-map :game-state 
                          (new-state row col (:game-state session-map))))))