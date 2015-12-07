(ns hello.example)


(defn -main [] (+ 1 1)
  (println (str (= "t" \t)
                (str \t \c)
                (= "tc" (str \t \c))))
  )


