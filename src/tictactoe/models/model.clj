(ns tictactoe.models.model
  [require noir.session :as session])

(def empty-board [[\- \- \-]
                  [\- \- \-]
                  [\- \- \-]])

(def init-state {:board empty-board :player \X})

(def ^{:private true} game-state (atom init-state))

(defn reset-game! []
  (session/put! :game-state init-state)
  #_(reset! game-state init-state))

(defn get-board []
  #_(:board @game-state)
  (:board (session/get :game-state)))

(defn get-board-cell 
  ([row col]
    (get-board-cell (get-board) row col))
  ([board row col]
    (get-in board [row col])))

(defn get-player []
  #_(:player @game-state)
  (:player (session/get :game-state)))

(defn other-player []
    (if (= (get-player) \X) \O \X))

(defn play! [row col]
  (when (= (get-board-cell (get-board) row col) \-)
      (session/put! :game-state
        (assoc (session/get :game-state)
               :board (assoc-in (get-board) [row col] (get-player))
               :player (other-player)))
      #_(swap! game-state assoc 
             :board 
             (assoc-in (get-board) [row col] (get-player))
             :player (other-player))))
  
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
    (or (winner? board \X)
        (winner? board \O)))
  ([board player]
    (if (or (winner-in-rows? board player)
            (winner-in-cols? board player)
            (winner-in-diagonals? board player))
      player)))

#_(defn full-board?
  ([] (full-board? (get-board)))
  ([board] (every? (fn [row]
                     (every? (fn [elt]
                               (not (= elt \-)))
                             row))
                   board)))

(defn full-board?
  ([] (full-board? (get-board)))
  ([board] (let [all-cells (apply concat board)]
             (not-any? #(= % \-) all-cells))))

