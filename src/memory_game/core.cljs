(ns memory-game.core
  (:require
   [reagent.core :as r]))

;; -------------------------
;; Views

; duplicate every item in the starting list and shuffle
(defonce card-numbers (shuffle (reduce concat (map (fn [%] [% %]) [1  2  3 4 5 6 7 8 9]))))
;(js/console.log card-numbers)
(defonce active-card (r/atom nil))

(defn card [id number]
  (let [num-id (str id "num") active (= num-id @active-card)]
    [:div
     [:h3 {:style (when (not active) {:display "none"})} number " card"]
     [:img
      {:on-click
       (fn []
         (cond
           @active-card
           (do

             (swap! active-card (fn [%] nil)))
           :else
           (swap! active-card (fn [%] num-id))))
       :src "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.HCnNvuumrkYlmPFq7moi4wHaKP%26pid%3DApi&f=1"}]]))

(defn cards [card-numbers]
  [:div {:style {:display "flex" :flex-wrap "wrap"}}
   (doall (map-indexed #(card %1 %2) card-numbers))])

(defn home-page []
  [:div
   [cards card-numbers]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))

