import java.io.BufferedReader
import java.io.InputStreamReader


fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,lengthLimit) = readLine().split(" ").map{it.toInt()}
    val nameLength = IntArray(n){readLine().toInt()}
    var ans = Int.MAX_VALUE

    fun searchNextBoundary(curWaste:Int,lastBoundary:Int){
        var lengthSum  = nameLength[lastBoundary+1]
        var i = 1
        while(lengthSum<lengthLimit){
            if(lastBoundary+i+1 in 0 until n){
                var left = lengthLimit-lengthSum
                var nextWaste = left*left + curWaste
                if(nextWaste<ans) searchNextBoundary(nextWaste,lastBoundary+i)
                i+=1
                lengthSum += nameLength[lastBoundary+i]+1
            }
            if(lastBoundary+i+1>=n){
                ans = curWaste.coerceAtMost(ans)
                return
            }
        }
    }
    searchNextBoundary(0,-1)
    print(ans)

}