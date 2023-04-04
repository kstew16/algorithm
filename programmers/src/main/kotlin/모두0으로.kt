import java.util.*
fun main(){
    data class Node(val nodeNum:Int,val depth:Int)
    class Solution {
        fun solution(w: IntArray, edges: Array<IntArray>): Long {
            var answer = 0L
            val weight = LongArray(w.size){w[it].toLong()}
            val map = Array(weight.size){ArrayList<Int>()}
            edges.forEach{ (a,b)->
                map[a].add(b)
                map[b].add(a)
            }
            val pq = PriorityQueue<Node>(){a,b->
                if(a.depth>b.depth) -1 else 1
            }
            val depth = IntArray(weight.size){-1}
            val depthQueue = LinkedList<Node>().apply{this.add(Node(0,0))}
            while(depthQueue.isNotEmpty()){
                val (nodeNum,d) = depthQueue.pollFirst()
                pq.add(Node(nodeNum,d))
                depth[nodeNum] = d
                map[nodeNum].forEach{
                    if(depth[it]==-1) depthQueue.add(Node(it,d+1))
                }
            }

            while(pq.isNotEmpty()){
                val cur = pq.poll()
                if(weight[cur.nodeNum]==0L) continue

                val connected = map[cur.nodeNum]
                for(i in connected.indices){
                    if(depth[connected[i]]<depth[cur.nodeNum]){
                        val moving = weight[cur.nodeNum].toLong()
                        answer += kotlin.math.abs(moving)
                        weight[connected[i]] += moving
                        weight[cur.nodeNum] = 0L
                        break
                    }
                }
            }
            return if(weight[0]==0L) answer else -1L
        }
    }
}