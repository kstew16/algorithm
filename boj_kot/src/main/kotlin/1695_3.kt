import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val st = StringTokenizer(readLine())
    val arr = IntArray(n){st.nextToken().toInt()}
    // dp[a][b] arr[a:b] 를 (닫힌범위) 펠린드롬으로 만드는데 추가해야하는 수의 개수
    val dp = Array(n){IntArray(n){-1}}
    for(i in 0 until n){ dp[i][i] = 0 }
    fun dpSolve(start:Int,end:Int):Int{
        return if(dp[start][end]!=-1) dp[start][end]
        else{
            if(arr[start]==arr[end]) {
                if(start+1==end){
                    dp[start][end] = 0
                    return 0
                }else{
                    val minVal = dpSolve(start+1,end-1)
                    dp[start][end] = minVal
                    minVal
                }
            }
            else {
                val minVal = kotlin.math.min(dpSolve(start+1,end),dpSolve(start,end-1)) +1
                dp[start][end] = minVal
                minVal
            }
        }
    }
    print(dpSolve(0,n-1))

}