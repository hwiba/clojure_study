(ns hello.trd_day)

;; Var : 이름 시스템
;; (def symbol value)
;; 이건 일반적인 변수와는 개념이 약간 다른데, 다른 언어에서는 변수명은 런타임시에 존재하지 않으며 포인터로 변경되고는 하지만
;; clojure에서는 symbol과 value과 완전히 별개가 된다. 실사용 상 거의 변수.

;; 함수 만들기
(def add (fn [x y] (+ x y)))
(defn add' [x y] (+ x y))

;; Docstrings : 함수 또는 값에 대해서 문서를 작성하는 기능.

;; let

;; def, defn으로 생성된 var들은 모두 전역 스코프를 가진다. local scope를 가지기 위해서는 let 키워드를 사용한다.
;; 다음 구문에서 b, c는 let이며 a는 var다.
(def a 1)
(let [b 2 c 3] (+ b c))

;; (defn [x y] (+ x y)) 와 같은 형태가 될 때, [x y] 또한 symbol로서 바인딩 된 것이다. 이 역시 로컬.

;; Dynamic Vars : runtime에 동적으로 binding 한다.

;; 조건문

;; (if boolean 참일때실행값 거짓일때실행값)
;; (if boolean 참일때실행값)
(if true 1 2)
(if false 1)
;; 거짓값은 생략이 가능하며 생략될 경우 거짓이 되면 nil을 반환한다. 그러나 권장은 아님.
;; 명시적으로 false 값은 nil을 원한다면 (if ) 대신 when 구문을 쓴다.
(when bool (println 1) 2)
;; when에 따라오는 인자들은 모두 수행된다 bool이 참일 때.
;; if는 함수가 아니다. 매크로로 구현된 것이며 리스트의 인자들을 평가하지 않도록 할 수 있다.
;; nil은 false이며 나머지는 true가 된다.

;; if 문의 조합들.
(let [x (if false 1 2)] (+ x 1))
(+ (if true 1 2) 3)

;; 조건을 판단해주는 =, >, <, >=, <= 등이 있다.
;; 계속 커지는가 같은 구문이 가능하게 된다.
(if (< 1 2 3 4 5) 1 2)

;; 조건이 맞지 않는 것을 판단해주는 not, not=이 있다.
(if (not (= 1 1)) 1 2)
(if (not= 1 2 ) 1 2)

;; 조건의 조합을 위해 and와 or가 있다.
(if (and (= 1 1) (= 2 2)) "O" "X")
(if (or (= 1 1) (= 2 3)) "O" "X")
(and true (+ 1 2) (+ 3 4) (+ 5 6))


;; 예제에서 and는 nil이 나오기 전까지만 확인해보고 이후에 있는 값은 무시한다. 주의할 것.
;; 쓸 일은 드물다.
(and (+ 1 2) (+ 3 4) nil (+ 5 6))

;; or는 처음 나온는 참값을 나타낸다. 때에 따라서 값이 없을 때 기본값을 사용하기 좋다.
;; or에서 처음 참값이 나오면 그 뒤는 실행하지 않는다.
(or 1 2)
(or nil 2)



;; if-let : 이때는 하나만 바인딩 된다.
(if-let [name (:name user)]
  (println name)
  (println "not found"))


;; bool을 리턴하는 함수에 대해서 컨벤션으로 ?를 붙인다.
(defn isZero? [x] (= x 0))


;; 시퀀스
;; 복수의 항목을 가지는 자료구조들은 시퀀스 인터페이스의 구현이므로 시퀀스 함수라 불리는 공통 함수를 모두 적용할 수 있다.
(first [1 2 3])
(first '(1 2 3))

;; 다음 예제에서 나오는 결과는 [:name "eunmin"]이 되는데 순서가 없기 때문이며, 그래도 같은 데이터에는 일정하게 나온다
;; 중요한 점은 이 결과는 vector가 아니라 MapEntry라는 형식이며 key, val 함수로 키 또는 값을 가져올 수 있다는 점.
(first {:id 1 :name "eunmin"})
(key first-element)
(val first-element)
(first first-element)


;; 문자열 역시 시퀀스에 해당한다.
(first "Hello World")

;; second
(second [1 2 3])

;; nth : 지정한 인덱스
(nth [1 2 3] 2)
(nth "Hello World" 5)

;; rest : 첫번째 항목을 제외한 나머지 항목을 가져온다.ㄴ
(rest [1 2 3])

;; 마지막
(last [1 2 3])

;; apply : 함수와 시퀀스를 받아 시퀀스를 함수의 인자로 넘겨 실행한다.
(apply + [1 2 3])

