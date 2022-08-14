import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.StringTokenizer

private fun main(){
    fun nextTurn(pos:Int,dice:Int): IntArray {
        var nextPos = pos+dice
        var result: IntArray
        when{
            (pos in 50..53) -> {
                result = if(nextPos>53) nextTurn(197,nextPos-53-1)
                else intArrayOf(3*(nextPos-50) + 10,nextPos)
            }
            (pos in 100..102) -> {
                result = if(nextPos>102) nextTurn(197,nextPos-102-1)
                else intArrayOf(2*(nextPos-100) + 20,nextPos)
            }
            (pos in 150..153) -> {
                //153 에서 26 152에서 27 151에서 28
                result = if(nextPos>153) nextTurn(197,nextPos-153-1)
                else intArrayOf((-1)*(nextPos-150) + 29,nextPos)
            }
            (pos in 197..199) -> {
                // 197->25 198->30 199->35 200->40
                result = if(nextPos>200) intArrayOf(0,300)
                else intArrayOf((200-nextPos)*(-5) + 40,nextPos)
            }
            else ->{
                nextPos = pos + 10*dice
                result = if(nextPos>200) intArrayOf(0,300)
                else intArrayOf(nextPos/5,nextPos)
            }
        }
        return result
    }
    for(i in 0 until 20){
        for(j in 1..5){
            println("$i : " + nextTurn(i*10,j).joinToString(" "))
        }
    }
    for(i in intArrayOf(50,51,52,53,100,101,102,150,151,152,153,197,198,199)){
        for(j in 1..5){
            println("$i : " + nextTurn(i,j).joinToString(" "))
        }
    }
}