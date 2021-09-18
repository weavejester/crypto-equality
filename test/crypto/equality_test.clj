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
          zs (int-array (concat (repeat 999 3) [0]))
          ts (loop [i 0, t0 0, t1 0]
               (if (< i 1000)
                 (recur (inc i)
                        (+ t0 (time-expr (eq? xs ys)))
                        (+ t1 (time-expr (eq? xs zs))))
                 [t0 t1]))]
      (is (approx-eq (ts 0) (ts 1) 0.2))))
  (testing "zero-length strings"
    (is (eq? "" ""))
    (is (not (eq? "foo" "")))
    (is (not (eq? "" "foo")))))
