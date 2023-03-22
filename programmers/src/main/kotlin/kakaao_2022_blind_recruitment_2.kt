fun main(){
    class Solution {
        fun solution(info: IntArray, edges: Array<IntArray>): Int {
            val map = Array(info.size){ArrayList<Int>()}
            edges.forEach { (s,e)->
                map[s].add(e)
            }
            // 해당 노드에서 벌려놓은 격차별로 모을 수 있는 양들의 최댓값
            val stateMemo = Array(info.size){mutableMapOf<Int,Int>()}

            // 양과 늑대의 차이에 따라 스테이트의 전개가 달라질 수 있음
            // 양과 늑대의 차가 같으면 양이 많은 게 좋은 스테이트
            // 스테이트를 여러 개 만들어서 최종반환되는 스테이트들중에 최대 양 수를 찾는 건 어떨까
            fun searchNode(visiting:Int){
                val possibleState = mutableMapOf<Int,Int>()
                val optimumCostOf = mutableMapOf<Int,Int>()
                var curReward = if(info[visiting]==0) 1 else 0
                var curCost = if(info[visiting]==1) 1 else 0

                // 더 이상 들어가지 않고 얻을 수 있는 비용/보상 기록
                //stateMemo[visiting][curCost] = curReward
                possibleState[curCost] = curReward
                optimumCostOf[curReward] = curCost

                // 자식 하나만 방문해서 얻을 수 있는 비용/보상 기록
                for(i in map[visiting].indices){
                    val child = map[visiting][i]
                    // 내 자식의 결과에 나를 더해볼 수 있음
                    if(stateMemo[child].isEmpty()) searchNode(child)
                    stateMemo[child].forEach { (childCost, childReward) ->
                        val newCost = curCost + childCost
                        val newReward = curReward + childReward
                        if(optimumCostOf[newReward]==null){
                            optimumCostOf[newReward] = newCost
                            possibleState[newCost] = newReward
                        }else if(optimumCostOf[newReward]!!>newCost){
                            optimumCostOf[newReward] = newCost
                            possibleState[newCost] = newReward
                        }
                    }
                }

                // 자식을 둘 다 방문해서 얻을 수 있는 비용/보상 기록 (돌려막기)
                if(map[visiting].size==2){
                    val l = map[visiting][0]
                    val r = map[visiting][1]
                    stateMemo[l].forEach { (lCost, lReward) ->
                        stateMemo[r].forEach{(rCost,rReward)->
                            // 좌측을 방문할 수 있고, 좌측보상으로 우측을 방문할 수 있는 경우
                            // 사실상 우측 노드를 방문하는 비용은 우측비용+현재비용 - 좌측보상
                            if(curReward>lCost && lReward+curReward>rCost){
                                val newCost = curCost + rCost - lReward
                                val newReward = curReward + lReward + rReward
                                if(optimumCostOf[newReward]==null){
                                    optimumCostOf[newReward] = newCost
                                    possibleState[newCost] = newReward
                                }else if(optimumCostOf[newReward]!!>newCost){
                                    optimumCostOf[newReward] = newCost
                                    possibleState[newCost] = newReward
                                }
                            }
                            if(curReward>rCost && rReward+curReward>lCost){
                                val newCost = curCost + lCost - rReward
                                val newReward = curReward + lReward + rReward
                                if(optimumCostOf[newReward]==null){
                                    optimumCostOf[newReward] = newCost
                                    possibleState[newCost] = newReward
                                }else if(optimumCostOf[newReward]!!>newCost){
                                    optimumCostOf[newReward] = newCost
                                    possibleState[newCost] = newReward
                                }
                            }
                        }
                    }
                }
                possibleState.forEach { (cost, reward) ->
                    stateMemo[visiting][cost] = reward
                }
            }
            searchNode(0)
            return stateMemo[0][0]!!
        }
    }
    val info = intArrayOf(0,0,1,1,1,0,1,0,1,0,1,1)
    val edge = arrayOf(
        intArrayOf(0,1),
        intArrayOf(1,2),
        intArrayOf(1,4),
        intArrayOf(0,8),
        intArrayOf(8,7),
        intArrayOf(9,10),
        intArrayOf(9,11),
        intArrayOf(4,3),
        intArrayOf(6,5),
        intArrayOf(4,6),
        intArrayOf(8,9)
    )
    println(Solution().solution(info,edge))
}