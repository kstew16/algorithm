import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val blocks = readLine().toCharArray()
    fun Char.nextType() = when(this){
        'B' -> 'O'
        'O' -> 'J'
        'J' -> 'B'
        else -> 'F'
    }

    // 지연된 queue 를 사용할 수 있음
    // 2 dp, 자신 오른쪽에 있는 다음 턴 블럭을 (자신 턴 블럭을 거치지 않고) 가는 가장 가까운 방법은 d^2
    val distance = IntArray(n){Int.MAX_VALUE}.apply { this[0]=0 }
    // 방문 가능한 곳일 경우
    for(i in 0 until n-1){
        if(distance[i]!=Int.MAX_VALUE){
            val curType = blocks[i]
            val nextType1 = curType.nextType()
            val nextType2 = nextType1.nextType()
            var curDist = distance[i]
            var j = 1
            var possibility = 0
            while(i+j<n){
                if(possibility==2 && blocks[i+j] == curType) break
                if(blocks[i+j] == nextType1){
                    possibility = (1).coerceAtLeast(possibility)
                    distance[i+j] = (curDist + j*j).coerceAtMost(distance[i+j])
                }else if(possibility == 1 && blocks[i+j]==nextType2) possibility = 2
                j+=1
            }
        }
    }
    print(if(distance[n-1]==Int.MAX_VALUE)-1 else distance[n-1])
}
