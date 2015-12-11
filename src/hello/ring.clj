;; ring-clojure/ring

;; luminus : clojure에서 가장 대표적으로 사용되는 웹 개발 템플릿
;; Leiningen의 template 형태로 사용한다.
;; $ lein new lunimus foo 의 형태로 사용한다.
;; luminus의 근간은 ring이라는 라이브러리로 만들어져있다.

;; lein deps : 의존성 다운받는다.
;; lein 관련 대개 명령어는 의존 누락이 있을 시 자체 해결.

;; project.clj에 dependencies 추가. 각자 서블릿 관련, 네트워크 서버 관련으로 다른 라이브러리로 바꿀 수 있다.
;; [ring/ring-core "1.4.0"]
;; [ring/ring-jetty-adapter "1.4.0"]]

;; 기본 프로젝트를 만든 다음 작성.
(ns hello-world.core (:require [ring.adapter.jetty :refer [run-jetty]]
								[ring.util.response :as response]))


;; 이 내용은 기초 이해를 위한 것이고 실제로는 여러 라이브러리를 사용하므로 모양이 꽤 다르다.
;; compojure 라이브러리를 사용해서 routing을 더 편하게 할 수 있다.
(defn index [request]
	{	:status 200
		:header {"Content-Type" "text/html"}
		:body (str request)
	})

(defn show [request]
	(response/response "<h1>show</h1>"))
;; response/response는 {	:status 200 :header {"Content-Type" "text/html"} :body (인자) 자동 생성.
	})

(defn handler [request]
	(case (:uri request)
		"/index" (index request)
		"/show" (show request)
		(response/response "<h1>not found</h1>")))

;; https://github.com/ring-clojure/ring/wiki/Getting-Started
(defn -main [& arg] (run-jetty handler {:port 3000}))


;;============================================================

;; compojure를 project.clj에 의존성 추가해서 새로 변경.


(ns hello-world.core
	(:require [ring.adapter.jetty :refer [run-jetty]]
			   [ring.util.response :as response]
			   [compojure.core :refer [defroutes]]
			   [compojure.route :refer [not-found]]))


(defn index []
	(response/response "<h1>index</h1>"))

(defn show []
	(response/response "<h1>show</h1>"))

(defroutes handler
	(GET "/index" [] (index))
	(GET "/show" [] (show))
	(not-found "<h1>not found!</h1>"))

(defn -main [& arg] 
	(run-jetty handler {:port 3000}))


;; ===========================================================

;; template engine
;; selmer(luminus에서 쓴다. html 형태를 유지)와 Hiccup(clojure 문법으로 html을 구현)

;; hiccup을 project.clj에 의존성 추가.

(ns hello-world.core
	(:require [ring.adapter.jetty :refer [run-jetty]]
			   [ring.util.response :as response]
			   [compojure.core :refer [defroutes]]
			   [compojure.route :refer [not-found]]
			   [hiccup.core :refer [html]]))


(defn index [name]
	(response/response
		(html [:h1 name])))

(defn show []
	(response/response
		(html [:h1 "show"])))

(defroutes handler
	(GET "/index/:name" [name] (index name))
	(GET "/show" [] (show))
	(not-found (html [:h1 "not found!!"])))

(defn -main [& arg] 
	(run-jetty handler {:port 3000}))


;; =========================================

;; template fragments

(ns hello-world.view.base)

(defn view [content-view]
	[:div
		[:h1 "content!!"]
		content-view])

;;==========


(ns hello-world.view.index)

(denf view [name]
	[:h1 name])



;;================================

(ns hello-world.core
	(:require [ring.adapter.jetty :refer [run-jetty]]
			   [ring.util.response :as response]
			   [compojure.core :refer [defroutes]]
			   [compojure.route :refer [not-found]]
			   [hiccup.core :refer [html]]
			   [hello-world.view.base :as base]
			   [hello-world.view.index :as index]))


(defn index [name]
	(response/response
		(html (base/view
				(index/view name)))))

(defn show []
	(response/response
		(html [:h1 "show"])))

(defroutes handler
	(GET "/index/:name" [name] (index name))
	(GET "/show" [] (show))
	(not-found (html [:h1 "not found!!"])))

(defn -main [& arg] 
	(run-jetty handler {:port 3000}))



;; www.clojure-toolbox.com에서 여러 라이브러리를 확인할 수 있다.
;; 라이브러리가 잘게 쪼개져있어 하나하나의 기능이 단순하므로 학습비용은 적다.

;; 카카오 게임에서는 ring 대신 pedestal을 쓴다.

;; json 관련 라이브러리는 clojure 공식의 data.json 라이브러리.
;; 오래된 클래식한 라이브러리로는 cheshire가 있다. 이는 내부적으로 Jackson을 씀.





