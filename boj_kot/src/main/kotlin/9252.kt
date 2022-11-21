import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val arr1 = readLine().toCharArray()
    val arr2 = readLine().toCharArray()
    val sizeA = arr1.size
    val sizeB = arr2.size
    // a 에서 문자열 1 끝까지, b 에서 문자열 2 끝까지 사용했을 때 얻을 수 있는 LCS 의 길이
    val dp = Array(sizeA+1){IntArray(sizeB+1){0} }
    fun getLCS(a:Int,b:Int):Int{
        var maxLen = 0
        if(a==sizeA || b==sizeB) return 0
        if(dp[a][b]!=0) return dp[a][b]
        if(arr1[a]==arr2[b]) maxLen = getLCS(a+1,b+1)+1
        maxLen = getLCS(a, b + 1).coerceAtLeast(maxLen)
        maxLen = getLCS(a + 1, b).coerceAtLeast(maxLen)
        dp[a][b] = maxLen
        return maxLen
    }
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    val ans = getLCS(0,0)
    bw.write("$ans\n")
    var count = 0
    var x = 0
    var y = 0
    val ansArr = CharArray(ans)
    while(count<ans){
        when(dp[x][y]){
            dp[x+1][y] -> x+=1
            dp[x][y+1] -> y+=1
            else ->{
                ansArr[count++] = arr1[x]
                x+=1
                y+=1
            }
        }
    }
    bw.write(ansArr)
    bw.flush()
    bw.close()
    close()
}