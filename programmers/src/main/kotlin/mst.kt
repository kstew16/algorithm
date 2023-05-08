import java.util.PriorityQueue
fun main(){
    data class Edge(val a:Int,val b:Int,val cost:Int)
    class Solution {
        fun solution(n: Int, costs: Array<IntArray>): Int {
            var answer = 0
            val parent = IntArray(n+1){it}
            fun find(x:Int):Int{
                if(parent[x]==x) return x
                parent[x] = find(parent[x])
                return parent[x]
            }
            fun union(x:Int,y:Int){
                val px = find(x)
                val py = find(y)
                if(px<py) parent[py] = px
                else parent[px]  = py
            }
            val edges = PriorityQueue<Edge>(){a,b-> if(a.cost<b.cost)-1 else 1}
            costs.forEach{(a,b,cost)->edges.add(Edge(a,b,cost))}
            while(edges.isNotEmpty()){
                val (a,b,cost) = edges.poll()
                if(find(a)!=find(b)){
                    union(a,b)
                    answer += cost
                }
            }
            return answer
        }
    }

    data class Node(val no:Int,val distance:Int)
    class Solution2 {
        fun solution(n: Int, costs: Array<IntArray>): Int {
            var answer = 0
            val pq = PriorityQueue<Node>(){a,b->if(a.distance<b.distance)-1 else 1}.apply{this.add(Node(1,0))}
            val map = Array(n+1){ArrayList<Node>()}
            costs.forEach{(a,b,cost)->
                map[a].add(Node(b,cost))
                map[b].add(Node(a,cost))
            }
            val visited = BooleanArray(n+1){false}
            while(pq.isNotEmpty()){ // 가장 가까운 노드별로 순서대로 이으면서 방문
                val (no,cost) = pq.poll()
                if(visited[no]) continue
                visited[no] = true
                answer+=cost // 잇는 비용
                map[no].forEach{(next,dToNext)->
                    if(!visited[no]) pq.add(Node(next,dToNext))
                }
            }
            return answer
        }
    }
}