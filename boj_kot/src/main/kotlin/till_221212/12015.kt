import java.util.StringTokenizer
import java.io.BufferedReader
import java.io.InputStreamReader
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val st = StringTokenizer(readLine())
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    // minIsLastNum[i] 길이가 i 인 부분수열의 마지막 수의 최소값
    val minIsLastNum = IntArray(n+1){Int.MAX_VALUE}.apply{this[0] = -1}
    var maxLen = 0
    repeat(n){
        val cur = st.getInt()
        // minIsLastNum 중에서 cur 보다 작은 가장 큰 수를 찾아낸다
        var lo = 0
        var hi = maxLen
        var mid:Int
        while(lo+1<hi){
            mid = (lo+hi)/2
            if(minIsLastNum[mid]<cur) lo = mid
            else hi = mid
        }

        // cur 로 만들 수 있는 가장 긴 부분수열의 길이
        val curLength = if(minIsLastNum[hi]<cur) hi+1 else lo+1
        // 내가 내 길이의 최고 효율을 가지고 있으면 (Destination 이 가장 작으면) 기록
        minIsLastNum[curLength] = cur.coerceAtMost(minIsLastNum[curLength])
        maxLen = curLength.coerceAtLeast(maxLen)
    }
    print(maxLen)
}