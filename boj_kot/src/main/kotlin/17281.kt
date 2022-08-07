import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

const val PLAYERS = 9
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var st = StringTokenizer(readLine())
    fun getInt() = st.nextToken().toInt()
    val n = getInt()
    val foretells = Array(n){
        st = StringTokenizer(readLine())
        IntArray(PLAYERS){getInt()}
    }
    // 낙하산으로 4번 타자는 1번 선수, 이건 점수 계산할 때 사용할 것
    val preLineUp = IntArray(PLAYERS-1)
    val finalLineUp = IntArray(PLAYERS).apply { this[3] = 1 }

    val visited = BooleanArray(PLAYERS-1){false}
    var maxScore = 0

    // 베이스 기록용 비트마스킹
    // infix fun Int.on(i:Int) = this or (1 shl i)
    infix fun Int.off(i:Int) = this and (1 shl i).inv()
    infix fun Int.chk(i:Int) = this and (1 shl i)>=1

    fun playBaseBall():Int{
        //println("아 경기 시작합니다.")
        var score = 0
        var batSequence = 0
        repeat(n){
            inning->
            //println("${inning+1}이닝 시작합니다!!!!!!!!!1")
            var outCount = 0
            var base = 0
            while(outCount<3){
                val batResult = foretells[inning][finalLineUp[batSequence]-1]
                if(batResult>0){
                    // 안타시 주루
                    base = ((base shl 1) + 1) shl (batResult-1)
                    for(i in 3..6){
                        // 득점주자 체크
                        if(base.chk(i)){
                            score += 1
                            base = base.off(i)
                        }
                    }
                }
                else{
                    outCount += 1
                }
                // 다음 타자 결정
                //println("${batSequence+1}번 타자 ${finalLineUp[batSequence]}번 선수 ${batResult}루타 \n현재스코어 ${score}")
                batSequence = (batSequence+1)%PLAYERS
            }
            //println("쓰리아웃 체인지!!")
        }
        //println("경기 종료되었습니다.\n최종 스코어 ${score}!!")
        return score
    }
    fun simulateGame(){
        fun makeLineUpFrom(visiting:Int,depth:Int){
            visited[visiting] = true
            preLineUp[depth-1] = (visiting+2)
            if(depth==PLAYERS-1){
                for(i in preLineUp.indices){
                    when{
                        (i<3)->{
                            finalLineUp[i] = preLineUp[i]
                        }
                        (i>=3)->{
                            finalLineUp[i+1] = preLineUp[i]
                        }
                    }
                }
                maxScore = playBaseBall().coerceAtLeast(maxScore)
            }else{
                for(i in 2..9){
                    if(!visited[i-2]){
                        makeLineUpFrom((i-2),depth+1)
                    }
                }
            }
            visited[visiting] = false
            //preLineUp[depth-1] = 0
        }
        for(i in 0..7){
            makeLineUpFrom(i,1)
        }
    }
    simulateGame()
    print(maxScore)
}
