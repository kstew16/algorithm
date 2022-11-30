import java.io.BufferedReader
import java.io.InputStreamReader

fun main(){
    val n = BufferedReader(InputStreamReader(System.`in`)).readLine().toInt()
    //0 정과 1 전산관 2 미래관 3 신앙관 4 진리관 5 한경직기념관 6 학생회관 7 형남공학관
    data class Road(val s:Int,val e:Int)
    val roads = arrayOf(
        Road(0,1),
        Road(0,2),
        Road(1,2),
        Road(1,3),
        Road(2,3),
        Road(2,5),
        Road(3,4),
        Road(3,5),
        Road(4,5),
        Road(4,6),
        Road(5,7),
        Road(6,7)
    )
    //val record = Array(8){ IntArray(n+2) }
    // dp[i][j] i%2 분 후에 j 번 건물에 머물고 있을 수 있는 경우의 수 어라? 규칙성 있나
    val dp = Array(2){ IntArray(8) }.apply { this[0][0] = 1 }
    var t = 0
    while(t<n){
        t++
        val tIndex = t%2
        val tLastIndex = if(tIndex==1) 0 else 1
        for(i in 0 until 8) dp[tIndex][i] = 0
        roads.forEach {
            dp[tIndex][it.e] = (dp[tIndex][it.e]+dp[tLastIndex][it.s])%1000000007
            dp[tIndex][it.s] = (dp[tIndex][it.s]+dp[tLastIndex][it.e])%1000000007
        }
    }
    print(dp[n%2][0])
}