(ns rubiks)

(def initial (zipmap [:u :d :l :r :b :f]
                     (map #(vec (repeat 4 %)) (range 6))))

(defn print-cube [c]
  (let [g get-in]
    (println)
    (println "    " (g c [:u 0] ) (g c [:u 1]))
    (println "    " (g c [:u 3] ) (g c [:u 2]))
    (println)
    (println (g c [:l 0] ) (g c [:l 1]) "" (g c [:f 0] ) (g c [:f 1]) "" (g c [:r 0] ) (g c [:r 1]) "" (g c [:b 0] ) (g c [:b 1]))
    (println (g c [:l 3] ) (g c [:l 2]) "" (g c [:f 3] ) (g c [:f 2]) "" (g c [:r 3] ) (g c [:r 2]) "" (g c [:b 3] ) (g c [:b 2]))
    (println)
    (println "    " (g c [:d 0] ) (g c [:d 1]))
    (println "    " (g c [:d 3] ) (g c [:d 2]))
    c))

(def face-cycles {:F #{[[:f 0] [:f 1] [:f 2] [:f 3]]
                       [[:u 3] [:r 0] [:d 1] [:l 2]]
                       [[:u 2] [:r 3] [:d 0] [:l 1]]}

                  :R #{[[:r 0] [:r 1] [:r 2] [:r 3]]
                       [[:f 2] [:u 2] [:b 0] [:d 0]]
                       [[:f 1] [:u 1] [:b 3] [:d 1]]}

                  :L #{[[:l 0] [:l 1] [:l 2] [:l 3]]
                       [[:f 0] [:d 0] [:b 2] [:u 0]]
                       [[:f 3] [:d 3] [:b 1] [:u 3]]}

                  :B #{[[:b 0] [:b 1] [:b 2] [:b 3]]
                       [[:l 0] [:d 3] [:r 2] [:u 1]]
                       [[:l 3] [:d 2] [:r 1] [:u 0]]}

                  :D #{[[:d 0] [:d 1] [:d 2] [:d 3]]
                       [[:l 2] [:f 2] [:r 2] [:u 0]]
                       [[:l 3] [:f 3] [:r 3] [:u 1]]}

                  :U #{[[:u 0] [:u 1] [:u 2] [:u 3]]
                       [[:l 0] [:d 2] [:r 0] [:f 0]]
                       [[:l 1] [:d 3] [:r 3] [:f 1]]}})

(defn rotate-1 [xs]
  (concat [(last xs)] (butlast xs)))

(defn single-cycle [cube ps]
  (let [originals (map (partial get-in cube) ps)
        os-cycled (rotate-1 originals)]
    (reduce (fn [c [p o]] (assoc-in c p o)) cube (zipmap ps os-cycled))))

(defn move-face [cube face]
  (reduce single-cycle cube (face-cycles face)))

(defn apply-moves [cube fs]
  (reduce move-face cube fs))

(defn generate-undo-seq [fs]
  (mapcat (partial repeat 3) (reverse fs)))

(do
  (println "===================")
  (let [moves [:F :U :D :R :L]]
    (print-cube (apply-moves (apply-moves initial moves) (generate-undo-seq moves))))
  )
