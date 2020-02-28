(ns memory-game.core
  (:require
   [reagent.core :as r]))

;; -------------------------
;; Views

; duplicate every item in the starting list and shuffle
(defonce card-numbers (reduce concat (map (fn [%] [% %]) (range 1 13))))
(defonce suites ["clubs" "diamonds" "hearts" "spades"])
(defonce cards-number-suite
  (shuffle (map-indexed
            (fn [index number] [number (get suites (mod index 4))])
            card-numbers)))
(defonce active-card (r/atom nil))
(defonce solved-cards (r/atom #{}))
(defonce temp-visible (r/atom #{}))
(defonce guesses (r/atom 0))

(defn counter []
  [:h1 @guesses " guesses"])
(defn victory []
  [:h1 "You got a score of " @guesses])

(defn card [id number suite]
  [id
   (let [active (or
                 (= id (:id @active-card))
                 (contains? @temp-visible id))
         src (cond
               (contains? @solved-cards id) "images/back_overlay.png"
               active (str "images/" number "_of_" suite ".svg")
               :else "images/back.png")]
     [:div
      [:img
       {:on-click
        (when (not (or (= (:id @active-card) id) (contains? @solved-cards id)))
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
        :src src}]])])

(defn cards [card-data]
  [:div {:class "card-container"}
   (doall
    (for [item (map-indexed #(card %1 (first %2) (second %2)) card-data)]
      ^{:key (first item)} [:div {:class "card"} (second item)]))])

(defn home-page []
  [:div
   (cond
     (= (count card-numbers) (count @solved-cards))
     [victory]
     :else
     [counter])
   [cards cards-number-suite]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))

