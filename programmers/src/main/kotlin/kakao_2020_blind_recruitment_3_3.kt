import java.util.LinkedList
import java.util.PriorityQueue

fun main(){

    data class Edge(val to:Int,val cost:Int,var disabled:Boolean)
    data class Node(var previous:Edge?,var next:Edge?)
    class Solution{
        fun solution(n:Int,weak:IntArray,dist:IntArray):Int{
            // Edge 가 weak + 1, 즉 최대 16개인 그래프에서 a 개의 간선을 제거하면, a 개의 분리된 그래프가 생성됨.
            // a <= dist.size 인 선에서 capacity 를 체크하면 Brute Force 가 가능함
            // 해봐야 14만개 이내인 조합들에 대해서 DFS(최대 16개) 를 수행하고, 최대 8 개인 capacity 테스트를 거치면 천만 레벨에서 끝남
            val nodes = weak.size
            // 순환을 이루는 양방향그래프 생성
            val map = Array(nodes){Node(null,null)}
            for(cur in weak.indices){
                val nextPoint = if(cur+1==weak.size) 0 else cur+1
                val distanceToNext = with(weak[nextPoint]-weak[cur]){
                    if(this>0){
                        kotlin.math.min(this,n-this)
                    }else{
                        kotlin.math.min(this+n,-this)
                    }
                }
                map[cur].next = Edge(nextPoint,distanceToNext,false)
                map[nextPoint].previous = Edge(cur,distanceToNext,false)
            }
            val capacities = dist.sortedArrayDescending()

            var minAttempts =Int.MAX_VALUE
            // dfs 기반으로 콤비네이션을 생성해서 disconnect 표시된 애들을 disable 하고 BFS 를 통해서
            val disconnect = BooleanArray(nodes){false}
            for(choose in 1..dist.size){
                fun dfsCombination(depth:Int,depthLimit:Int,visiting:Int){
                    if(minAttempts!=Int.MAX_VALUE) return
                    disconnect[visiting] = true
                    if(depth==depthLimit){
                        // disconnected 된 애들 가지고 BFS 굴리기
                        for(i in 0 until nodes){
                            if(disconnect[i]){
                                val nextNode = if(i+1==nodes) 0 else i+1
                                map[i].next!!.disabled=true
                                map[nextNode].previous!!.disabled=true
                            }
                        }
                        val visited = BooleanArray(nodes){false}

                        val capacityQueue = PriorityQueue<Int>{a,b-> if(a>b) -1 else 1}
                        var curCapacity = 0
                        fun getConnected(visiting:Int){
                            visited[visiting] = true
                            map[visiting].next?.let {
                                if(!it.disabled && !visited[it.to]){
                                    curCapacity+= it.cost
                                    getConnected(it.to)
                                }
                            }
                            map[visiting].previous?.let {
                                if(!it.disabled && !visited[it.to]){
                                    curCapacity+= it.cost
                                    getConnected(it.to)
                                }
                            }
                        }

                        for(j in 0 until nodes){
                            curCapacity = 0

                            if(!visited[j]){
                                visited[j] = true
                                getConnected(j)
                                capacityQueue.add(curCapacity)
                            }
                        }
                        var c = 0
                        while(capacityQueue.isNotEmpty()){
                            if(capacityQueue.poll()>capacities[c]) break
                            c++
                            if(capacityQueue.isEmpty()) minAttempts = depthLimit
                        }


                        for(i in 0 until nodes){
                            if(disconnect[i]){
                                val nextNode = if(i+1==nodes) 0 else i+1
                                map[i].next!!.disabled=false
                                map[nextNode].previous!!.disabled=false
                            }
                        }
                    }else {
                        for(i in visiting+1 until nodes){
                            dfsCombination(depth+1,depthLimit,i)
                        }
                    }
                    disconnect[visiting]=false
                }

                for(i in 0 until nodes){
                    dfsCombination(1,choose,i)
                }
            }
            return if(minAttempts== Int.MAX_VALUE) -1 else minAttempts
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