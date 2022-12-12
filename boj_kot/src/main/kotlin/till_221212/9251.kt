import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Integer.max

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val arr1 = readLine().toCharArray()
    val arr2 = readLine().toCharArray()
    val sizeA = arr1.size
    val sizeB = arr2.size
    val dp = Array(sizeA){IntArray(sizeB){-1} }
    fun getLCS(a:Int,b:Int):Int{
        var maxLen = 0
        if(a==sizeA || b==sizeB) return 0
        if(dp[a][b]!=-1) return dp[a][b]
        if(arr1[a]==arr2[b])maxLen = getLCS(a+1,b+1) + 1
        maxLen = getLCS(a, b + 1).coerceAtLeast(maxLen)
        maxLen = getLCS(a + 1, b).coerceAtLeast(maxLen)
        dp[a][b] = maxLen
        return maxLen
    }
    println(getLCS(0,0))
}