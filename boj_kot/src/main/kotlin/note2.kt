import java.io.BufferedReader
import java.io.InputStreamReader

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val arr1 = readLine().toCharArray()
    val arr2 = readLine().toCharArray()
    val sizeA = arr1.size
    val sizeB = arr2.size
    val dp = Array(sizeA+1){IntArray(sizeB+1){-1} }
    val lcs = Array(sizeA+1){Array(sizeB+1){StringBuilder("")} }
    fun getLCS(a:Int,b:Int):Pair<Int,StringBuilder>{
        var maxLen = 0
        var sb = StringBuilder("")
        if(a==sizeA || b==sizeB) return Pair(maxLen,sb)
        if(dp[a][b]!=-1) return Pair(dp[a][b],lcs[a][b])
        if(arr1[a]==arr2[b]){
            sb.append(arr1[a])
            with(getLCS(a+1,b+1)){
                maxLen = this.first+1
                sb.append(this.second)
            }
        }
        with(getLCS(a,b+1)){
            if(this.first>maxLen){
                maxLen = this.first
                sb = this.second
            }
        }
        with(getLCS(a+1,b)){
            if(this.first>maxLen){
                maxLen = this.first
                sb = this.second
            }
        }
        dp[a][b] = maxLen
        lcs[a][b] = sb
        return Pair(maxLen,sb)
    }
    with(getLCS(0,0)){
        println(this.first)
        if(this.first!=0){
            println(this.second)
        }
    }

}