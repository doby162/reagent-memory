(ns memory-game.core
  (:require
   [reagent.core :as r]))

;; -------------------------
;; Views

; duplicate every item in the starting list and shuffle
(defonce card-numbers (shuffle (reduce concat (map (fn [%] [% %]) [1  2  3 4 5 6 7 8 9]))))
(defonce active-card (r/atom nil))
(defonce solved-numbers (r/atom #{}))
(defonce temp-visible (r/atom #{}))

(defn card [id number]
  [id
   (let [active (or
                 (= id (:id @active-card))
                 (contains? @solved-numbers number)
                 (contains? @temp-visible id))]
     [:div
      [:h3 {:style (when (not active) {:display "none"})} number " card"]
      [:img
       {:on-click
        (when (not active)
          (fn []
            (cond
              @active-card
              (do
                (when
                 (and
                  (= (:number @active-card) number)
                  (not (= (:id @active-card) id)))
                  (swap! solved-numbers #(conj % number)))
                (reset! temp-visible #{(:id @active-card) id})
                (swap! active-card (fn [%] nil)))
              :else
              (do
                (swap! active-card (fn [%] {:id id :number number}))
                (reset! temp-visible #{})))))
        :src "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.HCnNvuumrkYlmPFq7moi4wHaKP%26pid%3DApi&f=1"}]])])

(defn cards [card-numbers]
  [:div {:class "card-container"}
   (doall
    (for [item (map-indexed #(card %1 %2) card-numbers)]
      ^{:key (first item)} [:div {:class "card"} (second item)]))])

(defn home-page []
  [:div
   [cards card-numbers]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))

