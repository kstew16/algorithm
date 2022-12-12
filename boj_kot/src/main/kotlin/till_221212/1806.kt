import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
// 이분탐색 구현이 익숙하지 않아서 많이 틀렸음
// 내가 어떤 과정을 통해서 뭐가 답이 되는지 그냥 돌려보면서 감으로 맞추지 말고 논리로 증명해서 짜...
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt(); val s = st.getInt()
    st = StringTokenizer(readLine())
    var sum = 0
    val accSum = IntArray(n){
        sum+=st.getInt()
        sum
    }
    var minLength = n+1
    var lastMid = 0
    for(i in 0 until n){
        if(accSum[i]>=s){
            var hi = i
            var lo = lastMid
            var mid = (hi+lo)/2
            while (lo != mid) {
                mid = (hi + lo) / 2
                // mid 초과 i 이하 j 중에 [j,i] 의 합이 s 이상인 경우가 있음
                if (accSum[i] - accSum[mid] >= s) lo = mid+1
                // lo 이상 mid 이하 j 중에 [j,i] 의 합이 s 이상인 경우가 있음
                else hi = mid
            }
            // while 문에 들어갔다 나온 경우 lo==mid 이고 lo 이상 mid 이하에 답이 있기때문에 mid 가 답
            // 들어갔다 나오지 않은 경우는 hi 와 lo 의 평균이 lo 였다는거니까 hi == lo == mid 즉 한 개 짜리 구간임 근데 이런 경우 없을 듯
            lastMid = mid
            minLength = (i - mid + 1).coerceAtMost(minLength)
        }
    }
    if(minLength == n+1) print(0)
    else print(minLength)
}