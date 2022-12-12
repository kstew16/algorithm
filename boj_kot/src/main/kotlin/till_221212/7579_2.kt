import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
// 1차원 배열로 풀려다가 실패... 아마 더 꼬이면 optimum이 꺠지는듯
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,m) = readLine().split(" ").map{it.toInt()}

    var st = StringTokenizer(readLine())
    val memoryArr = IntArray(n){st.nextToken().toInt()}
    st = StringTokenizer(readLine())
    val costArr = IntArray(n){st.nextToken().toInt()}

    // dp[i] p번째 프로그램까지 실행해 봤을 때, i 의 재실행 비용을 통해 확보할 수 있는 최대 메모리 -> p를 윈도우식으로 0101왔다갔다하게함
    val dp = Array(2){IntArray(10001){0}}.apply{this[0][costArr[0]] = memoryArr[0]}

    for(i in 1 until n){
        val curTurn = i%2
        val lastTurn = (curTurn+1)%2
        val cost = costArr[i]
        val memory = memoryArr[i]
        for(j in 0 until 10001){
            dp[curTurn][j] = if(j-cost !in 0 until 10001) dp[lastTurn][j]
            else kotlin.math.max(dp[lastTurn][j],dp[lastTurn][j-cost]+memory)
        }
    }
    for(j in 0 until 10001){
        if(dp[(n-1)%2][j]>=m){
            print(j)
            break
        }
    }
}