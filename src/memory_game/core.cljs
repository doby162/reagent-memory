(ns memory-game.core
  (:require
   [reagent.core :as r]))

;; -------------------------
;; Views

; duplicate every item in the starting list and shuffle
(defonce card-numbers (shuffle (reduce concat (map (fn [%] [% % % %]) (range 1 14)))))
(defonce active-card (r/atom nil))
(defonce solved-cards (r/atom #{}))
(defonce temp-visible (r/atom #{}))
(defonce guesses (r/atom 0))

(defn counter []
  [:h1 @guesses " guesses"])
(defn victory []
  [:h1 "You got a score of " @guesses])

(defn card [id number]
  [id
   (let [active (or
                 (= id (:id @active-card))
                 (contains? @solved-cards id)
                 (contains? @temp-visible id))]
     [:div
      [:h2 {:style (when (not active) {:display "none"})} number]
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
                  (swap! solved-cards #(conj % id (:id @active-card))))
                (swap! guesses inc)
                (reset! temp-visible #{(:id @active-card) id})
                (reset! active-card nil))
              :else
              (do
                (reset! active-card {:id id :number number})
                (reset! temp-visible #{})))))
        :src "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.HCnNvuumrkYlmPFq7moi4wHaKP%26pid%3DApi&f=1"}]])])

(defn cards [card-numbers]
  [:div {:class "card-container"}
   (doall
    (for [item (map-indexed #(card %1 %2) card-numbers)]
      ^{:key (first item)} [:div {:class "card"} (second item)]))])

(defn home-page []
  [:div
   (cond
     (= (count card-numbers) (count @solved-cards))
     [victory]
     :else
     [counter])
   [cards card-numbers]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))

