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
    var minPh = Int.MAX_VALUE
    data class Candidate(val index:Int,val ph:Int)
    fun getCandidate(lo:Int,hi:Int,source:Int):Candidate{

        if(lo==hi) {
            val loSum = abs(arr[lo]+source)
            return Candidate(lo,loSum)
        }
        else if(lo+1==hi) {
            val loSum = abs(arr[lo]+source)
            val hiSum = abs(arr[hi]+source)
            return if(loSum<hiSum) Candidate(lo,loSum) else Candidate(hi,hiSum)
        }
        val mid = (lo+hi)/2
        val loCandidate = getCandidate(lo,mid,source)
        val hiCandidate = getCandidate(mid,hi,source)
        return if(loCandidate.ph<hiCandidate.ph) loCandidate else hiCandidate
    }
    for(i in 0 until n-2){
        // 최소로 하는 답은 [i+1,n-1] 에 있기 때문에 그 사이에서 최소값을 데리고 올라오는 재귀함수 프로그램을 작성
        val curCandidate = getCandidate(i+1,n-1,arr[i])
        if(minPh>curCandidate.ph){
            minPh = curCandidate.ph
            a = i
            b = curCandidate.index
        }
    }
    print("${arr[a]} ${arr[b]}")
}