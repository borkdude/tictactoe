(ns tictactoe.test.models.model
  (:use tictactoe.models.model)
  (:use clojure.test)
  (:require [tictactoe.test.models.testdata :as td]))

(deftest get-board-cell-test
  (let [testboard [[\X \- \-]
                   [\- \O \-]
                   [\- \- \X]]]
    (is (get-board-cell testboard 0 0) \X)
    (is (get-board-cell testboard 0 1) \-)
    (is (get-board-cell testboard 1 1) \O)
    (is (get-board-cell testboard 2 2) \X)))

(deftest transposed-board-test
  (doseq [io-pair td/transposed-test-data]
    (is (transposed-board (:input io-pair))
        (:expected-output io-pair))))

(defmacro defboardtest [name winfn positives negatives]
  `(deftest ~name
     (doseq [player# [\X \O]]     
       (doseq [board# (~positives player#)]
         (is (= (~winfn board# player#) true) 
             (str "Player " player# " should win with board " board#)))
       (doseq [board# (~negatives player#)]
         (is (= (~winfn board# player#) false)
             (str "Player " player# " should not win with board " board#))))))

(defboardtest winner-in-rows?-test
              winner-in-rows?
              td/row-win-combinations
              td/no-row-win-combinations)

(defboardtest winner-in-cols?-test
              winner-in-cols?
              td/col-win-combinations
              td/no-col-win-combinations)

(defboardtest winner-in-diagonals?-test
              winner-in-diagonals?
              td/diag-win-combinations
              td/no-diag-win-combinations)

(deftest full-board?-test
  (doseq [player [\X \O]]
    (doseq [board (td/full-boards player)]
      (is (= (full-board? board) true)
          (str "Board should be considered full, but isn't: " board)))
    (doseq [board (td/no-full-boards player)]
      (is (= (full-board? board) false)
          (str "Board should not be considered full, but is: " board)))))

;; this test should be rewritten according to switch to Noir sessions
#_(deftest scenario1-test
  "it should not be possible to choose a cell that is already taken"
  (reset-game!)
  (play! 0 0)
  (is (= (get-board-cell 0 0) \X))
  (play! 0 1)
  (is (= (get-board-cell 0 1) \O))
  (play! 0 2)
  (is (= (get-board-cell 0 2) \X))
  (is (= (get-player) \O))
  (play! 0 0)
  (is (= (get-board-cell 0 0) \X) "value of cell 0 0 should still be X")
  (is (= (get-player) \O) "player should still be O")
  (reset-game!))

;; exercise: add deftest for function winner?
;; exercise: macro for defining test scenarios which resets game automatically at beginning and end
;; exercise: refactor scenario1-test using the macro
;; exercise: more scenario's
;;       - player X wins
;;       - player O wins
;;       - it's a draw



  