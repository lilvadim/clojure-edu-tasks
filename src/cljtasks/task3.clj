(ns cljtasks.task3)

; Implement a parallel variant of filter using futures. Each future must process a block of elements, not just a
; single element. The input sequence could be either finite or infinite. Thus, the implemented filter must
; possess both laziness and performance improvement by utilizing parallelism.
; Cover code with unit tests.
; Demonstrate the efficiency using time.

(defn parallel-filter [pred coll]
  (let [cores (.availableProcessors (Runtime/getRuntime)),
        small-chunk-size (/ cores 2),
        big-chunk-size (* small-chunk-size cores),
        filter-chunks (fn [c] (reduce (fn [acc x] (conj acc (future (doall (filter pred x))))) '() (partition-all small-chunk-size c))),
        concat-blocking (fn [c] (reduce (fn [acc x] (concat @x acc)) '() c)),
        action (fn action [c]
                 (lazy-seq
                   (if (empty? c)
                     '()
                     (concat
                       (concat-blocking (filter-chunks (take big-chunk-size c)))
                       (action (drop big-chunk-size c))
                       )
                     )
                   )
                 )
        ]
    (action coll)
    )
  )

(defn predicate [x] (Thread/sleep 100) (even? x))

(defn -main [& _] (println (parallel-filter predicate (range 100))))