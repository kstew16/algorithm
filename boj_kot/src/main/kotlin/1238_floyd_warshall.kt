import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
// 플로이드 와샬 알고리즘 베이스, n^3 이라 10억까지 갈 수 있음 쓸데없는 연산 많지만 5분만에 짤 수 있는 코드
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt(); val m = st.getInt(); val x = st.getInt()
    val d = Array(n+1){IntArray(n+1){Int.MAX_VALUE}}
    repeat(m){
        st = StringTokenizer(readLine())
        d[st.getInt()][st.getInt()] = st.getInt()
    }
    for(k in 1..n)for(i in 1..n)for(j in 1..n)if(d[i][k]!=Int.MAX_VALUE && d[k][j]!=Int.MAX_VALUE && (d[i][k]+d[k][j] < d[i][j]))  d[i][j] =  d[i][k] + d[k][j]
    var ans = 0
    for(i in 1..n){
        ans = (d[i][x] + d[x][i]).coerceAtLeast(ans)
    }
    print(ans)
}