(ns cljtasks.task2_test
  (:require [cljtasks.task2 :refer (primes)])
  (:require [clojure.test :as test]))



(test/deftest primes-test
  (test/testing "primes"
    (test/is (= (nth primes 0) 2))
    (test/is (= (nth primes 15) 53))
    (test/is (= (nth primes 255) 1619))
    (test/is (= (nth primes 4095) 38873))
    (test/is (= (nth primes 65535) 821641))))
