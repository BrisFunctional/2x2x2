(ns rubiks)

(def initial (zipmap [:u :d :l :r :b :f]
                     (map #(vec (repeat 4 %)) (range 6))))

(defn print-cube [c]
  (let [g get-in]
    (println "    " (g c [:u 0] ) (g c [:u 1]))
    (println "    " (g c [:u 3] ) (g c [:u 2]))
    (println)
    (println (g c [:l 0] ) (g c [:l 1]) "" (g c [:f 0] ) (g c [:f 1]) "" (g c [:r 0] ) (g c [:r 1]) "" (g c [:b 0] ) (g c [:b 1]))
    (println (g c [:l 3] ) (g c [:l 2]) "" (g c [:f 3] ) (g c [:f 2]) "" (g c [:r 3] ) (g c [:r 2]) "" (g c [:b 3] ) (g c [:b 2]))
    (println)
    (println "    " (g c [:d 0] ) (g c [:d 1]))
    (println "    " (g c [:d 3] ) (g c [:d 2]))))
