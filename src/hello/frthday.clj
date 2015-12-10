(ns hello.frthday)

;; 시퀀스 함 : 벡터 리스트 맵과 같은 특정한 인덱스를 가진 자료구조에 대해서 수행 가능한 함수 세트

;; apply : 함수와 시퀀스를 받아 시퀀스를 함수로 넘겨 실행한다.
;; 종종 쓰이는 함수라는데 왜 이렇게 쓰게 되는가? 나중에 찾아볼 것.
(apply + [1 2 3])

;; take : 시퀀스 앞에서부터 지정한 개수만큼 리턴
(take 2 [1 2 3 4])
;; -> (1 2)

;; sort (sort [2 1 2])

;; range
(range 1 4)
;; (1 2 3)
;; 3번째 인자는 간격을 의미한다.
(range 1 10 2)
;;-> (1 3 5 7 9)

;; 여기서부터 매우 범용적

;; map : 시퀀스에 모든 항목에 지정한 함수를 적용한 결과를 담은 시퀀스를 리턴한다.
(map (fn [e] (+ e 1)) [1 2 3 4 5])
(map inc [1 2 3 4 5])

;; reduce : 시퀀스의 모든 항목에 지정한 함수를 적용한 결과값을 리턴한다. (reduce 함수 시퀀스) 형태로 사용한다.
(reduce (fn [r e] (+ r e)) [1 2 3 4 5])
(reduce + [1 2 3 4 5])
;; reduce는 인자를 2개 받게 되며 이전 결과에 대한 리턴 값이 계속 r 인자가 된다.
;; 그 외에 가변인자를 가진 함수를 허용한다.

;; reduce에 두 번 째 인자로 초기값을 넣어줄 수 있다.
(reduce + 100 [1 2 3 4 5])

;; filter
(filter #(> % 0) [1 -1 2 -9 3 4 0 5])
;; #(> % 0) : fn 문법을 축약한 것. 아래와 동일.
(filter pos? [1 -1 2 -9 3 4 0 5])

;; empty? : 빈 시퀀스인지 체크.

;; every? : 시퀀스의 모든 항목이 조건을 만족하는지
(every? pos? [1 2 3 4])

;; 추가 내용 : http://clojure.org/sequences



;; 함수형 프로그래밍.

;; 추천 서적 : [착한 수학] - 튜링 머신, 람다 대수 튜링 완전성과 같은 후반부 내용이
;; 함수형 프로그래밍 이해에 도움이 된다.
;; sicp 도 도움이 되는 책.

;; 불변 데이터.
(def user {:id 1 :name "eunmin" :level 10})
(update-in user [:level] inc)
;; 여전히 user가 바인딩한 값은 {:name "eunmin", :level 11, :id 1}

;; var에 새로운 값을 바인딩 한다면 바뀐 거 아닌가 하는 의문에 대해
;; 값은 그대로이고 심볼에 바인딩 된 값이 다른 것이 될 뿐이다.

;; recur 는 재귀문을 꼬리재귀로 바꿔 최적화하는 함수다.
;; inc-level 대신 recur를 통해 inc-level로 호출할 수 있다.
;; 발산형 재귀?, 최적화할 수 없는 최종 완결 시에 답을 얻을 수 있는 재귀에는 쓸 수 없다.
(defn inc-level [user data]
  (if (empty? data)
    user
    (recur (update-in user [:level] #(+ (first data) %)) (rest data))))
;; 가능한 map 등 시퀀스 함수들을 이용하는 것이 편리하므로 재귀의 사용 빈도는 아주 높지는 않다.


;; 컨벤션 : db insert나 println 등 사이드 이펙트가 있는 함수들은 경고를 위해 끝에 !를 붙인다


;; var는 name space에 속하게 된다.
;; name space는 file 단위가 아니며 file 내부에서 다른 name space를 선언하면
;; 그 이후의 var들은 새 name space에 속하게 된다.

;; import 문. 다른 네임 스페이스의 이름이 평가되는 것을 막기 위해 '를 사용한다.
(require 'user)

;; 현재 네임 스페이스를 선언하면서 require를 사용할 수 있다. 이때는 평가하지 않으므로 '를 쓰지 않는다.
(ns group (:require user))

;; name space의 계층 구분자는 .을 사용한다. 실제 디렉터리 구조도 이 네임 스페이스 패키지 구조를 따른다.
;; java와의 차이는 자바처럼 뒤집어서 쓰는 컨벤션이 없다는 점이다.

;; namespace에서 -를 쓸 수 있으나 이것이 실제 디렉터리/파일에서는 _로 바꿔야 한다.

;; namespace가 너무 길 경우 :as를 사용하여 alias 할 수 있다
(require '[namespace-sample.handler.user :as user-handler])
(ns group (:require [namespace-sample.handler.user :as user-handler]))

;; :refer를 통해 상속 받은 것처럼 사용할 수 있다. 모든 var를 현재 name space에 있는 것처럼 쓸 수 있게 된다.
;; 또한 여러개 네임 스페이스를 추가할 수 있다. 특히 :refer :all은 모든 것을
(require '[namespace-sample.handler.user :refer [header-name]])
(require '[clojure.test :refer [deftest is]])
;; 권장 스타일은 아니지만, 특정 DSL 형태를 띈 라이브러리들은 이렇게 추가하고는 한다.
(require '[clojure.test :refer :all])

;; ^:private 키워드를 통해 private으로 정의할 수 있으며 외부 네임스페이스에서 사용시 컴파일 에러가 나게 된다
(defn ^:private valid-name? [name] (string? name))

;; defn ^:private 대신 defn- 을 사용할 수 있다 def에는 없다.
(defn- valid-name? [name] (string? name))

;; :require 를 통해 상호 참조를 할 수는 없다.

;; macro, meta data는 이번 세미나에서는 다루지 않겠다. but macro는 다른 언어와의 큰 차이점이므로 공부할 것.


;; 기타 record, protocol, component(라이브러리) 등의 개념이 있다.