package first

import java.util.*
fun main(){
    data class LightHouse(val no:Int,val connected:LinkedList<Int>)
    class Solution {
        fun solution(n: Int, lighthouse: Array<IntArray>): Int {
            var answer = 0

            val lightHouses = Array(n+1){
                LightHouse(it,LinkedList<Int>())
            }
            lighthouse.forEach{(a,b)->
                lightHouses[a].connected.add(b)
                lightHouses[b].connected.add(a)
            }

            val brightened = BooleanArray(n+1){false}.apply{this[0] = true}
            val pq = PriorityQueue<LightHouse>(){a,b->
                if(a.connected.size>b.connected.size) -1 else 1
            }

            lightHouses.forEach{
                pq.add(it)
            }

            while(pq.isNotEmpty()){
                val candidate = pq.poll()

                if(brightened[candidate.no]) continue
                var validConnected = candidate.connected.size

                var i = 0
                while(candidate.connected.isNotEmpty() && i<candidate.connected.size){
                    i++
                    val cur = candidate.connected.poll()
                    if(brightened[cur]) validConnected --
                    else candidate.connected.add(cur)
                }
                if(validConnected == 0) continue
                val candidate2 = pq.peek()
                if(validConnected >= candidate2.connected.size){
                    answer ++
                    brightened[candidate.no] = true
                }else{
                    pq.add(candidate)
                }
            }

            return answer
        }
    }
}