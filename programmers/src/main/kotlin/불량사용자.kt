import kotlin.collections.ArrayList
fun main(){
    // 이날 코드가 잘 안 짜지더라... 50분 2트
    class Solution {
        fun solution(userId: Array<String>, bannedId: Array<String>): Int {
            var answer = 0
            val n = userId.size; val m = bannedId.size
            val visited = BooleanArray(n){false}
            fun canBanned(uId:String,bId:String):Boolean{
                if(uId.length!=bId.length) return false
                for(i in uId.indices) if(uId[i]!=bId[i] && bId[i]!='*') return false
                return true
            }
            val orderVisited = BooleanArray(m){false}
            val stack = ArrayList<Int>()
            val banCase = ArrayList<String>()
            fun orderBanId(visiting:Int){
                if(visiting==m){
                    banCase.add(stack.joinToString(""))
                }else{
                    for(i in 0 until m){
                        if(!orderVisited[i]){
                            orderVisited[i] = true
                            stack.add(i)
                            orderBanId(visiting+1)
                            orderVisited[i] = false
                            stack.removeAt(stack.size-1)
                        }
                    }
                }
            }
            orderBanId(0)
            fun pickFrom(pickLeft:Int,from:Int){
                if(pickLeft==0){
                    val idCase = ArrayList<String>()
                    for(i in 0 until n) if(visited[i]) idCase.add(userId[i])

                    var found = false
                    for(j in banCase.indices){

                        var valid = true
                        // 현재 생성된 idCase 가 banCase 의 일부와 매칭되는지
                        for(i in 0 until m){
                            if(!valid) break
                            val banningId = bannedId[banCase[j][i].digitToInt()]
                            valid = valid and canBanned(idCase[i],banningId)
                        }
                        if(valid){
                            answer++
                            break
                        }
                    }
                }
                for(i in from until n){
                    visited[i] = true
                    pickFrom(pickLeft-1,i+1)
                    visited[i] = false
                }
            }
            pickFrom(m,0)
            return answer
        }
    }

}