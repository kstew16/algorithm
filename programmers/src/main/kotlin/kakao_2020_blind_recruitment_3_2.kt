import java.util.PriorityQueue

fun main(){
    data class OneWayEdge(val destination:Int, val cost:Int)
    data class TwoWayEdge(val from:Int,val to:Int, val cost:Int)
    class Solution{
        fun solution(n:Int, weak:IntArray, dist:IntArray):Int{
            //var minAttempts = Int.MAX_VALUE
            // 헐 MST 구성한다음에 MST 에서 하나씩 끊어가면서 그 모든 것들이 가능한가
            val weakPoints = weak.size
            val map = Array(weakPoints){ArrayList<OneWayEdge>()}

            val currentMap = PriorityQueue<TwoWayEdge>{a,b->
                when{
                    a.cost>b.cost -> -1
                    else -> 1
                }
            }

            val minCapacityWithCuts = Array(9){ PriorityQueue<Int>(){a,b->
                if(a>b) -1 else 1
            } }
            val edgeClosed = Array(weak.size){BooleanArray(weak.size){false} }

            for(i in weak.indices){
                val nextPoint = if(i+1==weak.size) 0 else i+1
                val distanceToNext = with(weak[nextPoint]-weak[i]){
                    if(this>0){
                        kotlin.math.min(this,n-this)
                    }else{
                        kotlin.math.min(this+n,-this)
                    }
                }
                currentMap.add(TwoWayEdge(i,nextPoint,distanceToNext))
                map[i].add(OneWayEdge(nextPoint,distanceToNext))
                map[nextPoint].add(OneWayEdge(i,distanceToNext))
            }


            val capacity = dist.sortedDescending().toIntArray()
            var nExcluded = 0
            while(currentMap.isNotEmpty()){
                nExcluded++
                // 가장 방해가 되는 연결로 하나 제외
                val excluding = currentMap.poll()

                edgeClosed[excluding.to][excluding.from] = true
                edgeClosed[excluding.from][excluding.to] = true

                val visited = BooleanArray(weakPoints)
                for(i in visited.indices){
                    if(!visited[i]){
                        visited[i] = true
                        val queue = PriorityQueue<OneWayEdge>(){a,b->
                            if(a.cost<b.cost)-1 else 1
                        }
                        queue.add(OneWayEdge(i,0))
                        var currentWeight = 0
                        while(queue.isNotEmpty()){
                            val cur = queue.poll()
                            currentWeight += cur.cost
                            map[cur.destination].forEach {next->
                                if(!visited[next.destination] && !edgeClosed[cur.destination][next.destination]){
                                    visited[next.destination] = true
                                    queue.add(OneWayEdge(next.destination,next.cost))
                                }
                            }
                        }
                        minCapacityWithCuts[nExcluded].add(currentWeight)
                    }
                }

                fun isPossibleToCover(capacityRequired:PriorityQueue<Int>, capacity:IntArray):Boolean{
                    var i = 0
                    while(capacityRequired.isNotEmpty()){
                        if(capacity[i]<capacityRequired.poll()) return false
                        i++
                    }
                    return true
                }
                if(isPossibleToCover(minCapacityWithCuts[nExcluded],capacity)) return nExcluded
            }


            return -1
        }
    }
    val n = 200
    val weakSize = 15
    val weak = IntArray(weakSize){
        it*(n/weakSize)
    }
    val dist = IntArray(8){10}
    println(Solution().solution(n,weak,dist))

}