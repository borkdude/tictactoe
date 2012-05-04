(ns tictactoe.test.models.testdata
  (:use clojure.set))

(defn row-win-combinations [p]
  #{ [[p  p  p]
      [\- \- \-]
      [\- \- \-]], 
     [[\- \- \-]
      [p  p  p]
      [\- \- \-]],
      [[\- \- \-]
       [\- \- \-]
       [p  p  p]]
   })

(defn no-row-win-combinations [p]
  #{[[\- \X \X]
     [\- \X \-]
     [\X \X \-]]})

(def transposed-test-data
  #{ {:input [[\X \X \X]
              [\O \X \O]
              [\X \X \X]],
      :expected-output [[\X \O \X]
                        [\X \X \X]
                        [\X \O \X]]}})

(defn col-win-combinations [p]
  #{ [[p  \- \-]
      [p  \- \-]
      [p  \- \-]],
    [[\- p  \-]
     [\- p  \-]
     [\- p  \-]], 
    [[\- \- p ]
     [\- \- p ]
     [\- \- p ]] })

(defn no-col-win-combinations [p]
  #{[[p  p  p ]
     [\- p  \-]
     [p  \- \-]]})

(defn diag-win-combinations [p]
  #{ [[p  \- \-]
      [\- p  \-]
      [\- \- p]],
    [[\-  \- p ]
     [\-  p  \-]
     [p  \- \-]] })

(defn no-diag-win-combinations [p]
  #{ [[p  \- \-]
      [\- \-  \-]
      [\- \- p]] })

(defn full-boards [p]
  #{ [[p p p]
      [p p p]
      [p p p]] })

(defn no-full-boards [p]
  (union (no-row-win-combinations p)
         (no-col-win-combinations p)
         (no-diag-win-combinations p)))
