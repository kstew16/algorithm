fun main(){
    class Solution {
        fun solution(n: Int, lighthouse: Array<IntArray>): Int {
            var answer = 0
            val map = Array(n+1){
                ArrayList<Int>()
            }
            lighthouse.forEach { (a,b)->
                map[a].add(b)
                map[b].add(a)
            }
            val brightened = BooleanArray(n+1){false}

            // 하위 노드가 없거나, 하위 노드가 다 켜있으면 사실상 리프노드나 다름없음 -> 리프노드의 부모는 켜 줘야 함
            fun isLeaf(source:Int, visiting:Int):Boolean{
                var hasValidChild = false
                var hasLeafChild = false
                for(i in map[visiting].indices){
                    val connected = map[visiting][i]
                    if(connected==source) continue
                    else{ // only child
                        if(isLeaf(visiting,connected)){
                            hasLeafChild = true
                            if(!brightened[connected]) hasValidChild = true
                        }
                    }
                }
                if(hasLeafChild){
                    answer++
                    brightened[visiting] = true
                }
                // 자식이 없거나 켜졌으면 true 반환
                return !hasValidChild
            }
            isLeaf(0,1)

            return answer
        }
    }
}