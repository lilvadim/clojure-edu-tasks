(ns cljtasks.task3-test
  (:require [cljtasks.task3 :refer [parallel-filter]])
  (:require [clojure.test :as test]))

(defn predicate [x] (Thread/sleep 100) (odd? x))
(def rng (range 200))

(test/deftest parallel-filter-benchmark []
                                        (test/testing "parallel benchmark"
                                          (println "> Parallel:")
                                          (time (doall (parallel-filter predicate rng)))
                                          )
                                        )

(test/deftest std-filter-benchmark []
                                   (test/testing "std benchmark"
                                     (println "> Std:")
                                     (time (doall (filter predicate rng)))
                                     )
                                   )

(test/deftest parallel-filter-test []
                                   (test/testing "is result valid"
                                     (test/is (= (filter predicate rng) (parallel-filter predicate rng)) "valid result")
                                     )
                                   )
