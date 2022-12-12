import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
import kotlin.math.max


fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n =readLine().toInt()
    fun StringTokenizer.getNum():Short = this.nextToken().toShort()
    val st = StringTokenizer(readLine())
    val k = readLine().toInt()

    val arr =  ShortArray(n){
        st.getNum()
    }

    // n 번 열차부터 k 개를 끌 때 얻을 수 있는 양을 미리 계산
    var sum = 0
    for(i in 0 until k) sum += arr[i]
    val benefit = IntArray(n){0}.apply { this[0] = sum }
    for(i in 1..n-k){
        sum = sum - arr[i-1] + arr[i+k-1]
        benefit[i] = sum
    }
    // dp[t][i] i 이상의 인덱스에서 t 개의 열차를 선택했을 때 얻을 수 있는 최대 이득
    val dp = Array(4){IntArray(n){0} }.apply {
        for(i in n-k downTo 0) this[1][i] = max(this[1][i+1],benefit[i])
    }
    for(i in 2 .. 3){
        for(j in n-k*(i-1)-1 downTo    0){
            // 나 다음 열차부터 다시 3개 고르기 vs 나 고르고 아래에서 고르기
            dp[i][j] =  max(dp[i][j+1],dp[i-1][j+k]+benefit[j])
        }
    }
    print(dp[3][0])

}