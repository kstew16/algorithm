import java.io.BufferedReader
import java.io.InputStreamReader

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val arr1 = readLine().toCharArray()
    val arr2 = readLine().toCharArray()
    val sizeA = arr1.size
    val sizeB = arr2.size
    // a 에서 문자열 1 끝까지, b 에서 문자열 2 끝까지 사용했을 때 얻을 수 있는 LCS 의 길이
    val dp = Array(sizeA+1){IntArray(sizeB+1){-1} }
    val next = Array(sizeA+1){Array(sizeB+1){Pair(-1,-1)} }
    fun getLCS(a:Int,b:Int):Int{
        var maxLen = 0
        if(a==sizeA || b==sizeB) return maxLen
        if(dp[a][b]!=-1) return dp[a][b]
        if(arr1[a]==arr2[b]){
            with(getLCS(a+1,b+1)){
                maxLen = this+1
                next[a][b] = Pair(a+1,b+1)
            }
        }
        with(getLCS(a,b+1)){
            if(this>maxLen){
                maxLen = this
                next[a][b] = Pair(a,b+1)
            }
        }
        with(getLCS(a+1,b)){
            if(this>maxLen){
                maxLen = this
                next[a][b] = Pair(a+1,b)
            }
        }
        dp[a][b] = maxLen
        return maxLen
    }
    with(getLCS(0,0)){
        println(this)
        if(this!=0){
            var last = Pair(0,0)
            var i = next[0][0]
            var sb = StringBuilder("")
            while(i.first!=-1 && i.second!=-1){
                if(last.first+1==i.first && last.second+1==i.second) sb.append(arr1[last.first])
                last = i
                i = next[i.first][i.second]
            }
            println(sb)
        }
    }
}