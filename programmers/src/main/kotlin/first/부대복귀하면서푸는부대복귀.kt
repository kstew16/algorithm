package first

import java.util.PriorityQueue

fun main(){
    data class Node(val nodeNum:Int, val distance:Int)
    class Solution {
        fun solution(n: Int, roads: Array<IntArray>, sources: IntArray, destination: Int): IntArray {
            var distance = IntArray(n+1){Int.MAX_VALUE}.apply { this[destination] = 0 }
            val pq = PriorityQueue<Node>{a,b -> if(a.distance<b.distance) -1 else 1 }.apply {
                this.add(Node(destination,0))
            }

            val map = Array(n+1){ArrayList<Int>()}
            roads.forEach {(a,b)->
                map[a].add(b)
                map[b].add(a)
            }

            while(pq.isNotEmpty()){
                val cur = pq.poll()
                if(distance[cur.nodeNum]<cur.distance)continue
                map[cur.nodeNum].forEach {nextNodeNum->
                    val newDistance = cur.distance + 1
                    if(newDistance<distance[nextNodeNum]){
                        distance[nextNodeNum] = newDistance
                        pq.add(Node(nextNodeNum,newDistance))
                    }
                }
            }
            val ans = IntArray(sources.size)
            sources.forEach {
                ans[it] = if(distance[it]==Int.MAX_VALUE) -1 else distance[it]
            }
            return ans
        }
    }


}