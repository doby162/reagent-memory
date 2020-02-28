(ns memory-game.prod
  (:require
    [memory-game.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
