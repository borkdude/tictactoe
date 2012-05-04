(ns tictactoe.views.common
  (:use noir.core
        hiccup.core
        hiccup.page-helpers))

(defpartial layout [& content]
            (html5
              [:head
               [:title "clojureweb"]
               (include-css "/css/reset.css")
               (include-css "/css/tictactoe.css")]
              [:body
               [:div#wrapper
                content]]))
