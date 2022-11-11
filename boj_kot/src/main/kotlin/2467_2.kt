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
    for(i in 0 until n-1){
        val cur = arr[i]
        var lo = i+1
        var hi = n-1
        var mid :Int
        while(lo+1<hi){
            mid = (lo+hi)/2
            val upper = abs(arr[mid+1]+cur)
            val lower = abs(arr[mid]+cur)
            if(lower<upper) hi = mid
            else lo = mid+1
        }
        val upper = abs(arr[hi]+cur)
        val lower = abs(arr[lo]+cur)
        val candidatePh:Int
        val candidate:Int
        if(lower<upper) {
            candidate = arr[lo]
            candidatePh = lower
        }else{
            candidate = arr[hi]
            candidatePh = upper
        }
        if(candidatePh<minSum){
            a = cur
            b = candidate
            minSum = candidatePh
        }
    }
    print("$a $b")
}