import java.util.*
fun main(){


    data class Root(val destination:Int, val cost:Int)
    class Solution1 {
        fun solution(n: Int, costs: Array<IntArray>): Int {
            var answer = 0

            val map = Array(n){mutableListOf<Root>()}
            for(i in costs.indices){
                val (a,b,cost) = costs[i]
                map[a].add(Root(b,cost))
                map[b].add(Root(a,cost))
            }


            val connected = BooleanArray(n){false}
            // 현재 집합에서 선택할 수 있는 엣지들
            val pq = PriorityQueue<Root>(){a,b-> if(a.cost<b.cost) -1 else 1}
            pq.add(Root(0,0))
            while(pq.isNotEmpty()){

                val (cur,cost) = pq.poll()
                if(connected[cur]) continue

                connected[cur] = true
                answer += cost

                map[cur].forEach{
                    if(!connected[it.destination]){
                        pq.add(Root(it.destination,it.cost))
                    }
                }
            }
            return answer
        }
    }
    data class Edge(val x:Int,val y:Int, val cost:Int)
    class Solution2 {
        fun solution(n: Int, costs: Array<IntArray>): Int {
            val root = IntArray(n) { it }
            fun find(x: Int): Int {
                if (root[x] == x) return x
                else root[x] = find(root[x])
                return root[x]
            }

            fun union(x: Int, y: Int) {
                val rx = find(x)
                val ry = find(y)
                if (rx == ry) return
                if (rx < ry) root[ry] = rx
                else root[rx] = ry
            }

            var answer = 0
            val pq = PriorityQueue<Edge>() { a, b ->
                if (a.cost < b.cost) -1 else 1
            }
            var count = 1
            costs.forEach {
                pq.add(Edge(it[0], it[1], it[2]))
            }
            while (count < n) {
                val (x, y, cost) = pq.poll()
                if (find(x) != find(y)) {
                    union(x, y)
                    count++
                    answer += cost
                }
            }

            return answer
        }
    }
    val n = 4
    val costs = arrayOf(
        intArrayOf(0,1,1),
        intArrayOf(0,2,2),
        intArrayOf(1,2,5),
        intArrayOf(1,3,1),
        intArrayOf(2,3,8)
    )
    println(Solution2().solution(n,costs))
}