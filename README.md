# Hello, welcome to this clojurescript / reagent project!

I wanted to learn some React, so here we are!

## Running the project
This is pretty much not a production app, there's no backend and no dedicated production server.
To run, navigate to the root of the project and start the Figwheel compiler with `lein figwheel`. 
If you don't have Lein installed, you can follow the installation directions here: https://leiningen.org/.
If you don't want it globally on your machine, feel free to place it in the project root instead of on your path. Just run with `./lein figwheel`.

Running Figwheel not only opens the project in your browser, it also opens a snazzy REPL that you can use to interact with gamestate live. There's a trick though, it plops you down in the wrong namespace and you need to run `(in-ns 'memory-game.core)` in order to access any of the defined functions and vars.

### Why aren't there any kings? 
This app was built to a spec that specified 2 cards per "value" and 24 total cards, thus kings are intentionally ommited and every given card value only represents two suites. There ought to be some kind of configuration, but this is all handled right at the top of the file in the declaration of card-numbers. 

Figwheel will automatically push cljs changes to the browser.
Once Figwheel starts up, you should be able to open the `public/index.html` page in the browser.

### REPL

The project is setup to start nREPL on port `7002` once Figwheel starts.
Once you connect to the nREPL, run `(cljs)` to switch to the ClojureScript REPL.
