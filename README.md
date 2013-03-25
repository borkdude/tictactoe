Tictactoe
=========
A simple server based game built with [Ring](https://github.com/ring-clojure), [Compojure](https://github.com/weavejester/compojure), [lib-noir](https://github.com/noir-clojure/lib-noir) and [Hiccup](https://github.com/weavejester/hiccup).

# Download and run: 

    $ git clone git@github.com:borkdude/tictactoe.git
    $ cd tictactoe
    $ lein ring server
    
A browser window will open and you'll be able to play.

# Run tests:    

    $ lein test

# Deploy to Heroku:

    $ heroku create --stack cedar
    $ git push heroku master    

More, see [Heroku](https://blog.heroku.com/archives/2011/7/5/clojure_on_heroku).

# TODO or student assignments

* Make a ClojureScript variation of this game. How much of the code can be
re-used?
* Add Kerodon tests
* Add AI so player can play against the computer

