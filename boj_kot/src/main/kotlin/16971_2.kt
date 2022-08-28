import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with( BufferedReader(InputStreamReader(System.`in`))) {
    val (n,m) = readLine().split(" ").map{it.toInt()}
    val arrA = Array(n){
        val st =StringTokenizer(readLine())
        IntArray(m){
            st.nextToken().toInt()
        }
    }
    val sumCount = Array(n){
        IntArray(m){0}
    }
    for(j in 0 until n-1){
        for(i in 0 until m-1){
            sumCount[j][i]+=1
            sumCount[j][i+1]+=1
            sumCount[j+1][i]+=1
            sumCount[j+1][i+1]+=1
        }
    }




    var maxPositiveDifference = 0
    fun combRepeat(colMode:Boolean){
        val limit = if(colMode) m else n
        for(j in 1 until limit){
            //maxPositiveDifference = if(colMode) (colSum[0]-colSum[j]).coerceAtLeast(maxPositiveDifference)
            //else (rowSum[0] - rowSum[j]).coerceAtLeast(maxPositiveDifference)
        }
        for(j in 1 until limit-1){
            //maxPositiveDifference = if(colMode) (colSum[limit-1] - colSum[j]).coerceAtLeast(maxPositiveDifference)
            //else (rowSum[limit-1] - rowSum[j]).coerceAtLeast(maxPositiveDifference)
        }
    }

    combRepeat(true)
    combRepeat(false)
    //print(maxPositiveDifference+sumArrB)
}