# crypto-equality

A very small Clojure library for protecting against
[timing attacks][1] when comparing strings or sequences of bytes.

[1]: https://en.wikipedia.org/wiki/Timing_attack

## Installation

Add the following dependency to your `project.clj` file:

    [crypto-equality "0.1.0-SNAPSHOT"]

## Usage

Require the `crypto.equality` namespace:

```clojure
(require '[crypto.equality :as crypto])
```

Then use the `eq?` function to compare strings, byte arrays, or any
ordered sequence of integers.

```clojure
(crypto/eq? "foo" "foo") ;; => true
(crypto/eq? "foo" "bar") ;; => false
```

## License

Copyright © 2013 James Reeves

Distributed under the Eclipse Public License, the same as Clojure.
