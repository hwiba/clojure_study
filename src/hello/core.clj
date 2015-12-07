(ns hello.core)
;;namespace가 src 아래의 hello의 core.clj가 될 것이다

(defn -main [] (println "Hello World"))
;;java main. method로 컴파일된다.

;;REPL에서 (ns example) 등으로 namespace를 변경할 수 있다.

;;lein repl로 REPL을 실행할 수도 있다.

;;REPL에서 user=>의 의미는 namespace가 user라는 것이다.

;;(false? true)
;;(true? true)
;;(nil? nil)

;; clojure에서는 Ratio라고 하여 1/3과 같은 분수 타입을 가지고 있다. 이는 / 연산을 하지 않고 모두 데이터라고 생각한다는 것.

;;(+ 1 2 3 4 5 6)
;;이와 같이 중복이 가능하며, == 비교는 =로 한다.

;; 1 글자는 \a 와 같은 형태로 쓴다. user=> \a 이는 UTF-8이므로 한글을 지원한다.

;; clojure는 (= "test" "test") 와 같은 형태로 제대로 비교를 해준다.
;; clojure도 자바와 마찬가지로 string table로 관리되는데, 

;; 인스턴스가 같은지를 비교하고 싶다면 (identical? )을 사용한다.
;; (= "" "")은 값을 비교한다.

(println (= "aa" "aa" "aa"))

;;(str "" "" "")는 스트링을 붙이는 펑션

