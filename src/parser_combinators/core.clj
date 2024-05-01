(ns parser-combinators.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defrecord State [input pos])

(defrecord Ok [consumed])
(defrecord Err [error-msg])

;consumed
(defn cok [consumed state] (Ok. consumed))
;ok
(defn eok [item state] (Ok. item))
;empty
(defn cerr [error-msg] (Err. error-msg))
;error
(defn eerr [error-msg] (Err. error-msg))

;consumes nothing but is always ok and produces a value
(defn always [x]
  ;escape hatches- signal parser's current state
  (fn  [state cok cerr eok eerr]
    (eok x state)))

;no arguments and immediately blows up
(defn never []
  ;escape hatches- signal parser's current state
  (fn  [state cok cerr eok eerr]
    (eerr (UnknownError. (:pos state)))))


(defn token [consume?] (fn [{:keys [input pos] :as state} cok cerr eok eerr]
                         (let [tok (first input)]
                           (if (consume? tok)
                             (cok tok (State. (rest input)  (inc pos)))
                             (eerr (str "token '" tok "'" "pos '" pos "'")) )
                             )
                         )
  )


(defn run-parser [p state] (p state cok cerr eok eerr))

(defn my-run [p input] (run-parser p (State. input 1)))