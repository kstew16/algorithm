package first

fun main(){
    class Solution {
        fun solution(lotteries: Array<IntArray>): Int {
            var maxChance = -1.0
            var maxIndex = -1
            var prized = 0

            for(i in lotteries.indices){
                val nChance = lotteries[i][0].toDouble()
                val buyer = lotteries[i][1].toDouble()
                val prize = lotteries[i][2]
                val curChance = if(nChance>buyer+1.0) 1.0 else nChance/(buyer+1.0)
                if(curChance>maxChance){
                    maxIndex = i
                    maxChance = curChance
                    prized = prize
                }else if(curChance==maxChance && prized<prize){
                    maxIndex = i
                    prized = prize
                }
            }
            return (maxIndex+1)
        }
    }

    val s1 = arrayOf(
        intArrayOf(1,0,500),
        intArrayOf(1,0,300),
        intArrayOf(2,0,700),
        intArrayOf(250,0,1000),
        intArrayOf(250,0,1000000),
    )
    println(Solution().solution(s1))

}