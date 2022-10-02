import java.io.BufferedReader
import java.io.InputStreamReader
// 한 수록곡이 늘어나면 그 노래를 처리할 방법은 7가지 뿐.
// 여행 갔다와서 푼 거

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val divisor = 1000000007
    val (all,a,b,c) = readLine().split(" ").map{it.toInt()}
    // dp[n][i][j][k] n 곡의 노래 중 a 가 i, b 가 j, c 가 k 개의 노래를 부를 수 있는 경우의 수
    val dp = Array(all+1){Array(a+1){Array(b+1){IntArray(c+1){-1} } } }.apply{
        this[0][0][0][0] = 1
        //this[1][1][0][0] = 1
        //this[1][0][1][0] = 1
        //this[1][0][0][1] = 1
    }
    fun get(n:Int,i:Int,j:Int,k:Int):Int{
        if(n<i || n<j || n<k) dp[n][i][j][k] = 0
        if(dp[n][i][j][k]!=-1) return dp[n][i][j][k]
        else{
            var sum = 0
            val iLeft = i>0
            val jLeft = j>0
            val kLeft = k>0
            if(n-1>=0) {
                if (iLeft) sum = (sum+get(n - 1, i - 1, j, k)) % divisor
                if (jLeft) sum = (sum+get(n - 1, i, j - 1, k)) % divisor
                if (kLeft) sum = (sum+get(n - 1, i, j, k - 1)) % divisor
                if(iLeft&&jLeft) sum = (sum+get(n-1,i-1,j-1,k)) % divisor
                if(iLeft&&kLeft) sum = (sum+get(n-1,i-1,j,k-1)) % divisor
                if(kLeft&&jLeft) sum = (sum+get(n-1,i,j-1,k-1)) % divisor
                if(iLeft && jLeft && kLeft) sum = (sum+get(n-1,i-1,j-1,k-1)) % divisor
            }
            dp[n][i][j][k] = sum
            return sum
        }
    }
    print(get(all,a,b,c))
}