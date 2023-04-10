import java.util.*
fun main(){
    data class Node(var tries:Int, var score:Int,var bonus:Int)
    class Solution {
        fun solution(target: Int): IntArray {
            val dp = Array(100001){intArrayOf(Int.MAX_VALUE,0)}
            var foundTry = Int.MAX_VALUE
            var searchingTry = 1
            val queue = LinkedList<Node>().apply{this.add(Node(0,0,0))}
            val choiceList = ArrayList<Int>()
            for(i in 1..20){
                choiceList.add(i)
                if(i in 11..20) choiceList.add(i*2)
                if(i in 7..20 && (i%2!=0||3*i>40)) choiceList.add(3*i)
            }
            choiceList.add(50)
            var choiceArr = choiceList.sortedDescending().toIntArray()
            while(queue.isNotEmpty() && searchingTry<foundTry){
                val cur = queue.pollFirst()
                searchingTry = cur.tries
                choiceArr.forEach{choice->
                    val newScore = choice + cur.score
                    if(newScore<=100000){
                        val newTry = cur.tries + 1
                        val newBonus = cur.bonus + if(choice in 1..20 || choice==50) 1 else 0
                        if(dp[newScore][0]>newTry || (dp[newScore][0]==newTry && dp[newScore][1]<newBonus)){
                            dp[newScore][0] = newTry
                            dp[newScore][1] = newBonus
                            queue.add(Node(newTry,newScore,newBonus))
                            if(newScore==target){
                                foundTry = newTry.coerceAtMost(foundTry)
                            }
                        }
                    }

                }
            }

            return dp[target]
        }
    }
}