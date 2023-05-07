import kotlin.collections.ArrayList
import java.util.LinkedList
fun main(){
    class Solution {
        fun solution(n: Int, edge: Array<IntArray>): Int {
            var farthest = 0
            var count = 0
            val map =  Array(n+1){ArrayList<Int>()}
            for(i in edge.indices){
                val (a,b) = edge[i]
                map[a].add(b)
                map[b].add(a)
            }
            val distance = IntArray(n+1){-1}.apply{this[1] = 0}
            val queue = LinkedList<Int>().apply{this.add(1)}
            while(queue.isNotEmpty()){
                val cur = queue.pollFirst()
                val nextDistance = distance[cur] + 1
                for(i in map[cur].indices){
                    val next= map[cur][i]
                    if(distance[next]==-1){
                        distance[next] = nextDistance
                        queue.add(next)
                        if(nextDistance>farthest){
                            farthest = nextDistance
                            count = 1
                        }else if(nextDistance == farthest) count++
                    }
                }
            }
            return count
        }
    }
}