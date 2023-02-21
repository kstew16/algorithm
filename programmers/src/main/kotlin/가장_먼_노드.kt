import java.util.*

fun main(){
    class Solution {
        fun solution(n: Int, edge: Array<IntArray>): Int {
            val map = Array(n+1){
                LinkedList<Int>()
            }
            edge.forEach{
                map[it[0]].add(it[1])
                map[it[1]].add(it[0])
            }
            data class Node(val index:Int,val cost:Int)
            var farthest = 0
            var farCount = 0
            val queue = LinkedList<Node>().apply{this.add(Node(1,0))}
            val visited = BooleanArray(n+1){false}.apply{this[1] = true}
            while(queue.isNotEmpty()){
                val cur = queue.pollFirst()
                val newCost = cur.cost+1
                map[cur.index].forEach{
                    if(!visited[it]){
                        visited[it] = true
                        queue.add(Node(it,newCost))
                        if(newCost>farthest){
                            farCount = 1
                            farthest = newCost
                        }else if(newCost==farthest){
                            farCount++
                        }
                    }

                }
            }
            return farCount
        }
    }
}