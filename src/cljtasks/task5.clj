(ns cljtasks.task5)

(def transaction-count (atom 0))

(defn eat [ph-number left-fork right-fork dur]
  (dosync
    (swap! transaction-count inc)
    (println (str "Philosopher#" ph-number " wants to eat"))
    (alter left-fork inc)
    (println (str "Philosopher#" ph-number " grabbed left fork"))
    (alter right-fork inc)
    (println (str "Philosopher#" ph-number " grabbed right fork"))
    (println (str "Philosopher#" ph-number " is eating"))
    (Thread/sleep dur)
    (println (str "Philosopher#" ph-number " finished dining"))
    (swap! transaction-count dec)
    )
  )

(defn think [ph-number dur]
  (println (str "Philosopher#" ph-number " is thinking"))
  (Thread/sleep dur)
  )

(defn philosopher [ph-number left-fork right-fork think-dur eat-dur periods-count]
  (new Thread
       (fn []
         (reduce
           (fn [_ _] (think ph-number think-dur) (eat ph-number left-fork right-fork eat-dur))
           '()
           (range periods-count)
           )
         )
       )
  )

(def forks (atom '()))

(defn philosophers-problem [ph-count think-dur eat-dur periods-count]
  (swap! forks (fn [_] (map (fn [_] (ref 0)) (range ph-count))))
  (let [
        philosophers (map
                       (fn [x] (philosopher x (nth @forks x) (nth @forks (mod (+ x 1) ph-count)) think-dur eat-dur periods-count))
                       (range ph-count)
                       )
        ]
    (doall (map (fn [philosopher] (.start philosopher)) philosophers))
    (doall (map (fn [philosopher] (.join philosopher)) philosophers)))
  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn -main []
  (time (philosophers-problem 7 1000 500 11))
  (println "Transaction Restarts:" @transaction-count)
  (doall (map #(println "Fork#" %1 "was used" @%2 "times") (iterate inc 0) @forks))
  )