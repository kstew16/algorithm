package first

import kotlin.math.pow

fun main(){
    class Solution {
        infix fun Int.on(i:Int) = this or (1 shl i)
        //infix fun Int.off(i:Int) = this and (1 shl i-1).inv()
        infix fun Int.chk(i:Int) = this and (1 shl i)>=1


        fun solution(user_id: Array<String>, banned_id: Array<String>): Int {
            var answer = 0
            val caseVisited = BooleanArray(2.0.pow(user_id.size).toInt())

            fun canBeBanned(userId:String,bannedId:String):Boolean{
                if(userId.length!=bannedId.length) return false
                for(i in userId.indices){
                    if(userId[i]!=bannedId[i] && bannedId[i]!='*') return false
                }
                return true
            }

            val map = Array(banned_id.size){ mutableListOf<Int>()}
            for(b in banned_id.indices){
                for(u in user_id.indices){
                    if(canBeBanned(user_id[u],banned_id[b])) map[b].add(u)
                }
            }

            fun markBanCase(depth:Int,bannedList:Int){
                if((depth >= banned_id.size)){ // 모든 밴아이디가 밴을 진행한 경우
                    if(!caseVisited[bannedList]) { // 그 배닝케이스가 없으면 마크
                        caseVisited[bannedList] = true
                        answer++
                    }
                }else {
                    map[depth].forEach { // DFS 식으로 bannedId 마다 갈 수 있는 곳을 방문
                        if(!bannedList.chk(it)){
                            markBanCase(depth + 1, bannedList.on(it))
                        }
                    }
                }
            }

            markBanCase(0,0)

            return answer
        }
    }
    val user_id = arrayOf("a","aa","aaa")
    val banned_id =  arrayOf("*", "**", "***")
    print(Solution().solution(user_id,banned_id))
}