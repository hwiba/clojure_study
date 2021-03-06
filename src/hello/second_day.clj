(ns hello.second_day)

;; clojure에서는 리스트를 입력하면 리스트의 첫 번째 인자를 함수로 간주하여 해석을 시작한다.
;; 때문에 (1 2 3)과 같은 리스트를 실제로 사용하려면 해석을 막기 위해 ' 기호를 사용해서 '(1 2 3) 과 같은 형태를 써야 한다.
'(1 2 3)

;; list의 첫 번째 항목을 펑션과 두 번째, 마지막, 첫번째를 제외한 나머지(rest)
(first '(1 2 3))
(second '(1 2 3))
(last '(1 2 3))
;;재귀호출을 구현할 때 종종 사용하게 된다. 언어적으로 반복문이 존재하지 않음.
(rest '(1 2 3))
(count '(1 2 3))
;;n번째 파라미터를 가져오기 n index는 0부터 시작한다.
(nth '(0 1 2 3) 3)

;; 자료구조 vector. 일반적인 Lisp에서는 벡터가 별도로 존재하지 않고 리스트를 사용하지만 clojure는 별도의 문법으로 벡터를 제공한다.
;; 벡터는 (= [1 2 3] '(1 2 3)). 이는 ' 기호를 쓰지 않고 리스트를 선언한다고 생각하면 좋다.
;; 리스트의 펑션들을 거의 모두(아닌게 있나?) 쓸 수 있으므로 클로져에서는 리스트를 쓰기보다 벡터를 쓰곤 함
(= [1 2 3] '(1 2 3))
[1 2 3]


;; map은 {} 괄호를 사용하며 {키 값, 키 값}
;; 하나의 맵 안의 키와 밸류의 타입이 제한 없이 섞일 수 있다.
;; , 구분은 생략이 가능하며, 순서를 보장하지 않는다.
{"id" 1, "key" "value"}
{"id" 2 "key" "value2"}

;;값 가져오기, 없다면 nil이 온다
(get {"name" "testName", "level" 40} "name")

;; assoc 함수로 맵에 값을 넣거나 바꿀 수 있다
(assoc {"name" "eunmin" "level" 40} "server" "asia")

;; Keyword : 주로 맵의 키로 사용되는 데이터 형식으로 :name으로 표현한다.
;; 키워드를 꼭 키로 써야한다는 제약은 없으며 이를 키로 쓸 때는 맵에서 ,를 생략하는 편이다.
:id

;; 키워드는 동적으로 생성하던 어떻던 항상 같은 인스턴스를 유지한다 다음은 true
(identical? (keyword "id") (keyword (str "i" "d")))

;;키워드는 함수로 사용할 수 있다. 키워드를 함수로 사용하면 맵에서 자신을 키로 가지는 값을 가져온다.
;; 이는 get 대용으로 거의 대부분의 경우 이렇게 쓰인다.
(:id {:id 2232 :name "eunmin"})

;; Set. 중복이 없는 자료구조. 잘은 안씀.
;; map의 keyword 문법이나 get 문법 등이 마찬가지로 적용된다.
#{1 2 3}

;; 존재하는지
(contains? #{1 2 3} 1)

;; Symbol type. 한글을 지원한다
;; 연산 시  연결된 어떤 값을 지칭하는 용도로 사용된다
println
;; 평가하지 않으려면 'println
'println

;; 함수

;; Clojure에서 함수는 값이다.
;; 완전히 순수한 함수형 언어는 아니다 하기에 따라 객체지향처럼 쓸 수 있다 그러나 비교적 함수형에 가깝다

;; 함수 만들기. fn 함수를 사용하여 함수를 만드는데 즉 리턴이 함수다.
;; []는 인자 그 다음 인자가 바디가 된다. 마지막 값이 리턴된다. 즉 중간에 리턴할 수는 없다.
;; 인자는 가변으로 받는다.
(fn [x y] (+ x y))

;; (fn [] nil)은 바로 nil을 리턴하는 함수가 된다.

;;함수를 간단히 만들 수 있는 #() 구문이 있다.
;;#() 구문은 fn과 파라미터 벡터를 생략하고 함수 본문을 바로 쓸 수 있다. 파라미터는 하나일 때 %, 두 개 이상일 때 첫번째 파라미터는 %1, 두번째 파라미터는 %2와 같이 쓴다.
#(+ %1 %2)
(#(println %) "Hello World")

;; 함수는 값이기 때문에 함수에 파라미터로 전달 할 수 있다.
((fn [f] (f 1 2)) (fn [x y] (+ x y)))

;; 함수를 리턴으로 한다면..
(((fn [] (fn [x] (+ x 1)))) 1)

;; Closure
;; 함수가 종료되더라도 다른 영역에서 쓰이게 되는 클로저 값들은 스택이 아닌 다른 영역에 할당한다.
(= 3 (((fn [y] (fn [x] (+ x y))) 2) 1))

;; 가변인자
;; 가변 인자를 받는 함수를 만들 때는 마지막 인자 앞에 &을 붙여서 만든다.
;; 아래 예제는 y가 가변인자라는 것을 명시한 것이다.
((fn [x & y] y) 1 2 3)

;; 오버로딩
;; 가변인자 함수는 인자 개수가 명시적이지 않기 때문에 정해진 갯수의 인자를 제공하는 함수의 경우에는 함수 오버로딩을 사용하는 것이 좋다
((fn ([x] x) ([x y] (+ x y))) 1)
((fn ([x] x) ([x y] (+ x y))) 1 2)
;; [x y] 로 오버로딩한, 즉 2개이기 때문에 3개 인자가 들어가면 에러가 난다.
;; ((fn ([x] x) ([x y] (+ x y))) 1 2 3)


;; 타입 체크를 할 수 있는 라이브러리로 coreTyped나 schema 같은 라이브러리가 있다.

