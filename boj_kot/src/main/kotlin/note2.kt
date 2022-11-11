import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
import kotlin.math.abs

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val st = StringTokenizer(readLine())
    val arr = IntArray(n){st.nextToken().toInt()}
    var a = 0
    var b = 0
    var minSum = Int.MAX_VALUE
    data class Candidate(val index:Int,val ph:Int)
    fun getCandidate(lo:Int,hi:Int){
        val mid = (lo+hi)/2
    }
    for(i in 0 until n-1){
        val cur = arr[i]
        var lo = i+1
        var hi = n-1
        var mid = (lo+hi)/2
        var sumHi = abs(cur+arr[hi])
        var sumLo = abs(cur+arr[lo])
        var sumMid = abs(cur+arr[mid])
        while(lo+1<hi){
            if(abs(cur+arr[hi])>abs(cur+arr[lo])) hi = mid
            else lo = mid
            mid = (lo+hi)/2
            sumHi = abs(cur+arr[hi])
            sumLo = abs(cur+arr[lo])
            sumMid = abs(cur+arr[mid])
        }
        val candidate:Int
        val candidateVal:Int
        if(sumHi>sumLo){
            candidate = sumLo
            candidateVal = arr[lo]
        }
        else{
            candidate = sumHi
            candidateVal = arr[hi]
        }
        if(candidate<minSum){
            minSum = candidate
            a = cur
            b = candidateVal
        }
    }
    print("$a $b")
}