import java.io.InputStreamReader
import java.io.BufferedReader
import java.util.StringTokenizer

fun main():Unit= with(BufferedReader(InputStreamReader(System.`in`))){
    val st = StringTokenizer(readLine())
    val w = st.nextToken().toInt(); val h = st.nextToken().toInt()
    // dp[j][i][0][0] 남쪽으로부터 j 번째 서쪽으로부터 i 번째에 있는 서쪽에서 온[0] 바로 전 턴에 안 꺾은[0]
    val dp = Array(h){Array(w){Array(2){IntArray(2){0} } } }.apply {
        for(i in 0 until w) this[0][i][0][0] = 1 // 가장 남쪽의 교차로는 동쪽으로 가는 방법 한 개뿐
        for(j in 1 until h) this[j][0][1][0] = 1 // 가장 서쪽의 교차로는 북쪽으로 가는 방법 한 개뿐
    }
    val GOING_WEST = 0
    val GOING_NORTH = 1
    val NOT_TURNED = 0
    val TURNED = 1
    val divider = 100000
    for(j in 1 until h){
        for(i in 1 until w){
            // 가던 방향으로 계속 가는 경우, 별다른 조건 X, 돌았던 애들도 안 돈 게 됨
            for(k in 0..1) {
                dp[j][i][GOING_NORTH][NOT_TURNED]=(dp[j-1][i][GOING_NORTH][k]+dp[j][i][GOING_NORTH][NOT_TURNED])%divider
                dp[j][i][GOING_WEST][NOT_TURNED]=(dp[j][i-1][GOING_WEST][k]+dp[j][i][GOING_WEST][NOT_TURNED])%divider
            }
            dp[j][i][GOING_NORTH][TURNED]=(dp[j-1][i][GOING_WEST][NOT_TURNED]+dp[j][i][GOING_NORTH][TURNED])%divider
            dp[j][i][GOING_WEST][TURNED]=(dp[j][i-1][GOING_NORTH][NOT_TURNED]+dp[j][i][GOING_WEST][TURNED])%divider
        }
    }
    print(dp[h-1][w-1].sumOf { it.sum() }%divider)
}