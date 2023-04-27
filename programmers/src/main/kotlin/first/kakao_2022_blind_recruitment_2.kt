package first

import java.util.*
import kotlin.collections.ArrayList

fun main(){
    class Solution {
        fun solution(info: IntArray, edges: Array<IntArray>): Int {
            val map = Array(info.size){ArrayList<Int>()}
            edges.forEach { (s,e)->
                map[s].add(e)
            }

            val connected = arrayListOf<Int>()
            val visited = BooleanArray(info.size)

            var maxCaught = 0
            fun dfs(visiting:Int,sheep:Int,wolves:Int,connected:ArrayList<Int>){
                val curConnected = arrayListOf<Int>().apply { this.addAll(connected) }
                visited[visiting] = true
                var curSheep = sheep

                val expandQueue = LinkedList<Int>()
                // 갈 수 있는 모든 노드를 추가하고
                map[visiting].forEach { expandQueue.add(it) }
                // 획득할 수 있는 양을 모두 획득
                while(expandQueue.isNotEmpty()){
                    val cur = expandQueue.pollFirst()
                    if(info[cur] == 1){ // 늑대
                        curConnected.add(cur)
                    }else{// 양
                        visited[cur] = true
                        map[cur].forEach {
                            expandQueue.add(it)
                        }
                        curSheep++
                    }
                }
                maxCaught = maxCaught.coerceAtLeast(curSheep)
                // 늑대노드에 대해 차례대로 dfs
                curConnected.forEach {wolfNode->
                    if(!visited[wolfNode] && curSheep>wolves+1){
                        dfs(wolfNode,curSheep,wolves+1,curConnected)
                    }
                }
                visited[visiting] = false
            }

            dfs(0,1,0,connected)
            return maxCaught
        }
    }
    val info = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val edge = arrayOf(
        intArrayOf(0,1),
        intArrayOf(0,2),
        intArrayOf(1,3),
        intArrayOf(1,4),
        intArrayOf(2,5),
        intArrayOf(2,6),
        intArrayOf(3,7),
        intArrayOf(3,8),
        intArrayOf(4,9),
        intArrayOf(4,10),
        intArrayOf(5,11),
        intArrayOf(5,12),
        intArrayOf(6,13),
        intArrayOf(6,14),
        intArrayOf(7,15),
        intArrayOf(7,16)
    )
    println(Solution().solution(info,edge))
}