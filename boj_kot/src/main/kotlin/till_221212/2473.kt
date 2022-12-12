import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
import kotlin.math.abs

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val st = StringTokenizer(readLine())
    val arr = LongArray(n){st.nextToken().toLong()}.sorted().toLongArray()
    data class Candidate(val distance:Long, val second:Long, val third:Long)
    var distanceToZero = Long.MAX_VALUE
    fun findMinPH(original:Long, from:Int, to:Int):Candidate{
        var distanceToOrigin = Long.MAX_VALUE
        var second = 0L
        var third = 0L
        for(i in from until to){
            val cur = arr[i]
            val curPH = cur + original
            // [lo,hi] 사이에 original + cur + arr[i] 가 0 에 가장 가까운 수가 있음
            var lo = i+1
            var hi = to
            var mid:Int
            while(lo+1<hi){
                mid = (lo+hi)/2
                // mid 아래쪽 구간에 존재
                if(abs(curPH+arr[mid])<abs(curPH+arr[mid+1])) hi = mid
                else lo = mid+1
            }
            // lo+1==hi
            val distanceFromLo = abs(curPH+arr[lo])
            val distanceFromHi = abs(curPH+arr[hi])
            val candidateDistance:Long
            val candidatePH:Long
            if(distanceFromLo<distanceFromHi){
                candidateDistance = distanceFromLo
                candidatePH = arr[lo]
            }else{
                candidateDistance = distanceFromHi
                candidatePH = arr[hi]
            }
            if(candidateDistance<distanceToOrigin){
                distanceToOrigin = candidateDistance
                second = cur
                third = candidatePH
            }
        }
        return Candidate(distanceToOrigin,second,third)
    }
    var f = 0L
    var s = 0L
    var t = 0L
    for(i in 0 until n-2){
        val cur = arr[i]
        val candidate = findMinPH(cur,i+1,n-1)
        if(candidate.distance<distanceToZero){
            distanceToZero = candidate.distance
            f = cur
            s = candidate.second
            t = candidate.third
        }
    }
    print("$f $s $t")
}