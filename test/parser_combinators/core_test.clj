(ns parser-combinators.core-test
  (:require [clojure.test :refer :all]
            [parser-combinators.core :refer :all]))


(my-run (token number?) 8)
