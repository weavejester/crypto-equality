(ns crypto.equality-test
  (:use clojure.test
        crypto.equality))

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
    (is (not (eq? [1 2 3] [3 2 1])))))
