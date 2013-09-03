(ns crypto.equality-test
  (:use clojure.test
        crypto.equality))

(defmacro time-expr [& form]
  `(let [t# (System/nanoTime)]
     ~@form
     (- (System/nanoTime) t#)))

(defn approx-eq [x y tolerance]
  (<= (/ (Math/abs (- x y)) x)
      tolerance))

(deftest eq?-test
  (testing "equality"
    (is (eq? "foo" "foo"))
    (is (eq? [1 2 3] [1 2 3]))
    (let [xs (take 1000 (repeatedly #(rand-int 1000000)))]
      (is (eq? (vec xs) (vec xs)))))
  (testing "inequality"
    (is (not (eq? "foo" "bar")))
    (is (not (eq? "foo" "foob")))
    (is (not (eq? "foob" "foo")))
    (is (not (eq? [1 2 3] [3 2 1]))))
  (testing "timing"
    (let [xs (int-array (repeat 1000 3))
          ys (int-array (repeat 1000 0))
          zs (int-array (concat (repeat 999 3) [0]))]
      (is (approx-eq (time-expr (dotimes [_ 1000] (eq? xs ys)))
                     (time-expr (dotimes [_ 1000] (eq? xs zs)))
                     0.2)))))
