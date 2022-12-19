import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    var st = StringTokenizer(readLine())
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    val n = st.getInt(); val m = st.getInt()
    st = StringTokenizer(readLine())
    val nHole = st.getInt()
    val isHole = Array(m+1){(BooleanArray(n+1))}
    val mod = 1000000007
    repeat(nHole){
        st = StringTokenizer(readLine())
        val y = st.getInt()
        val x = st.getInt()
        isHole[x][y] = true
    }
    val way = Array(m+1){IntArray(n+1)}.apply { this[1][1] = 1 }
    // x 가 홀수인 경우 (y,x) 에 (y,x-1),(y-1,x-1),(y-1,x) 가 접근 가능
    // x 가 짝수일 경우 (y,x) 에 (y+1,x-1) (y,x-1), (y-1,x) 가 접근 가능
    val dx = intArrayOf(-1,-1,0)
    val dy = arrayOf(
        intArrayOf(1,0,-1),
        intArrayOf(0,-1,-1)
    )
    for(x in 1..m){
        for(y in 1..n){
            if(x==1 && y==1) continue
            var sum = 0
            for(i in 0..2){
                val nx = x + dx[i]
                val ny = y + dy[x%2][i]
                if(nx !in 1..m || ny !in 1..n||isHole[nx][ny]) continue
                sum = (sum+way[nx][ny])%mod
            }
            way[x][y] = sum
        }
    }
    print(way[m][n])

}