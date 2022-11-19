import java.io.BufferedReader
import java.io.InputStreamReader

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val arr1 = readLine().toCharArray()
    val arr2 = readLine().toCharArray()
    val sizeA = arr1.size
    val sizeB = arr2.size
    // a 에서 문자열 1 끝까지, b 에서 문자열 2 끝까지 사용했을 때 얻을 수 있는 LCS 의 길이
    val dp = Array(sizeA+1){IntArray(sizeB+1){-1} }
    // 그 LCS 를 만드는 다음 문자가 dp 어디에서 온 건지
    val next = Array(sizeA+1){Array(sizeB+1){Pair(-1,-1)} }
    fun getLCS(a:Int,b:Int):Pair<Int, Pair<Int,Int>>{
        var maxLen = 0
        var usingCharIndex = Pair(-1,-1)
        if(a==sizeA || b==sizeB) return Pair(maxLen,usingCharIndex)
        if(dp[a][b]!=-1) return Pair(dp[a][b],next[a][b])
        if(arr1[a]==arr2[b]){
            with(getLCS(a+1,b+1)){
                maxLen = this.first+1
                next[a][b] = this.second
            }
        }
        with(getLCS(a,b+1)){
            if(this.first>maxLen){
                maxLen = this.first
                next[a][b] = this.second
            }
        }
        with(getLCS(a+1,b)){
            if(this.first>maxLen){
                maxLen = this.first
                next[a][b] = this.second
            }
        }
        dp[a][b] = maxLen
        //next[a][b] = usingCharIndex
        return Pair(maxLen,next[a][b])
    }
    with(getLCS(0,0)){
        println(this.first)
        if(this.first!=0){
            var i = this.second
            var sb = StringBuilder("")
            while(i.first!=-1 && i.second!=-1){
                if(arr1[i.first]==arr2[i.second]) sb.append(arr1[i.first])
                i = next[i.first][i.second]
            }
            print(sb)
        }
    }
}