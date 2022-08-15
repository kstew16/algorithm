import java.io.BufferedReader

import java.io.InputStreamReader
import java.io.Serializable

import java.util.StringTokenizer



fun main()=with(BufferedReader(InputStreamReader(System.`in`))) {
    val st = StringTokenizer(readLine())
    val dices = IntArray(10) {
        st.nextToken().toInt()
    }
    val turnSequence = IntArray(10) { -1 }
    fun calcGame():Int{
        var players = IntArray(4) { 0 }
        var score = 0
        turnSequence.withIndex().forEach {
            //  현재 턴과 주사위의 눈을 받아 얻은 점수와 다음 위치를 반환
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
            // 도착지에 간 말을 움직일 수 없음
            if(players[it.value]==300) return 0
            val nextTurn = nextTurn(players[it.value],dices[it.index])
            if(nextTurn[1] !in players || nextTurn[1]==300){
                // 도착지 이외의 칸에서는 중복될 수 없음
                score += nextTurn[0]
                players[it.value] = nextTurn[1]
            }
            else return 0
        }
        return score
    }
    var maxScore = 0
    fun dfsPermutation(depth: Int) {
        if (depth == 10) {
            //if(calcGame()==190){println(turnSequence.joinToString(" "))}
            maxScore = calcGame().coerceAtLeast(maxScore)
        } else {
            for (i in 0..3) {
                turnSequence[depth] = i
                dfsPermutation(depth + 1)
            }
        }
    }
    dfsPermutation(0)
    print(maxScore)
}
