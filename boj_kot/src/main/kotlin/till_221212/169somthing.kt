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
    val rowSum = IntArray(n){
        var sum = 0
        for(i in 0 until m) sum += if(i==0 || i == m-1) arrA[it][i] else arrA[it][i]*2
        sum
    }
    val colSum = IntArray(m){
        var sum = 0
        for(i in 0 until n) sum += if(i==0 || i == n-1) arrA[i][it] else arrA[i][it]*2
        sum
    }
    var sumArrB = 0
    for(j in 0 until n){
        for(i in 0 until m){
            var matchCount =0
            if(i == 0 || i == m-1) matchCount+=1
            if(j == 0 || j == n-1) matchCount+=1
            when(matchCount){
                0 -> sumArrB +=  arrA[j][i]*4
                1 -> sumArrB +=  arrA[j][i]*2
                2 -> sumArrB +=  arrA[j][i]*1
            }
        }
    }


    var maxPositiveDifference = 0
    fun combRepeat(colMode:Boolean){
        val limit = if(colMode) m else n
        for(j in 1 until limit-1){
            maxPositiveDifference = if(colMode) (colSum[0]-colSum[j]).coerceAtLeast(maxPositiveDifference)
            else (rowSum[0] - rowSum[j]).coerceAtLeast(maxPositiveDifference)
        }
        for(j in 1 until limit-1){
            maxPositiveDifference = if(colMode) (colSum[limit-1] - colSum[j]).coerceAtLeast(maxPositiveDifference)
            else (rowSum[limit-1] - rowSum[j]).coerceAtLeast(maxPositiveDifference)
        }
    }

    combRepeat(true)
    combRepeat(false)
    print(maxPositiveDifference+sumArrB)
}