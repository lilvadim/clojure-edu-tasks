(ns cljtasks.task1)

; Given an alphabet in form of a list containing 1-character strings and a number N. Define a function that
; returns all the possible strings of length N based on this alphabet and containing no equal subsequent
; characters.
; Use map/reduce/filter/remove operations and basic operations for lists such as str, cons, .concat, etc.
; No recursion, generators or advanced functions such as flatten!
; Example: for the alphabet ("а" "b " "c") and N=2 the result bust be ("ab" "ac" "ba" "bc" "ca" "cb") up to
; permutation.

(defn flatten' [coll]
  (reverse
    (reduce
      (fn [acc inner-coll]
        (reduce conj acc inner-coll))
      '()
      coll
      )
    )
  )

(defn c-mult [x y]
  (flatten'
    (map
      (fn [a]
        (map
          (fn [b] (flatten' (conj '() a b)))
          y
          )
        )
      x
      )
    )
  )

(defn map-to-str [x] (map (fn [y] (reduce str y)) x))

(defn last-char [s] (subs s (- (count s) 1) (count s)))

(defn pre-last-char [s] (subs s (- (count s) 2) (- (count s) 1)))

(defn clean-dup [x]
  (remove
    (fn [y] (= (pre-last-char y) (last-char y)))
    x
    )
  )

(defn comb [a n] {:pre [(> n 0)]}
  (if (= n 1)
    a
    (reduce
      (fn [acc, _] (clean-dup (map-to-str (c-mult a acc))))
      a
      (range (- n 1))
      )
    )
  )


(defn -main [& _]
  (let [a (list "a" "b" "c"), n 4]
    (println (comb a n))
    )
  )
