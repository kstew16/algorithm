import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
// Top down 으로 풀려고 몸비틀다가 실패해서 10번은 틀린듯 아니 내 예제에서는 400ms 못넘어가던데 더 좋은 예제가 뭘까
// 시간제한이 빡빡하면 그냥 BU를 쓰자...
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val arr1 = readLine().toCharArray()
    val arr2 = readLine().toCharArray()
    val sizeA = arr1.size
    val sizeB = arr2.size
    // 문자열 1 을 a 번째 문자 까지, 문자열 2 를 b 번째 문자 까지 사용했을 때 얻을 수 있는 LCS 의 길이

    val dp = Array(sizeA+1){IntArray(sizeB+1){0} }
    for(a in 1 .. sizeA){
        for(b in 1 .. sizeB){
            if(arr1[a-1]==arr2[b-1]){
                dp[a][b] = dp[a-1][b-1] + 1
            }else dp[a][b] = kotlin.math.max(dp[a-1][b], dp[a][b-1])
        }
    }

    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    var x = sizeA
    var y = sizeB
    val ans = dp[x][y]

    bw.write("$ans\n")
    var ansStr = ""
    while(dp[x][y]!=0){
        when (dp[x][y]) {
            dp[x-1][y] -> x--
            dp[x][y-1] -> y--
            else -> {
                ansStr = arr1[x-1] + ansStr
                x--
                y--
            }
        }
    }
    bw.write(ansStr)
    bw.flush()
    bw.close()
    close()
}