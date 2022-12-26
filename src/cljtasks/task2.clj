(ns cljtasks.task2)

; Define the infinite sequence of prime numbers. Use Sieve of Eratosthenes algorithm with infinite cap.
; Cover code with unit tests.

(def primes
  (lazy-seq (filter
              (fn [x] (not-any?
                        (fn [y] (zero? (rem x y)))
                        (take-while (fn [n] (<= (* n n) x)) primes)
                        )
                )
              (drop 2 (range))
              )
            )
  )

(defn -main [& _] (println (take 100 primes)))