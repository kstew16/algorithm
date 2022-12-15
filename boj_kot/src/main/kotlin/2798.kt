import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    var st = StringTokenizer(readLine())
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    val n = st.getInt();val m = st.getInt()
    st = StringTokenizer(readLine())
    val visited = BooleanArray(100001)
    repeat(n){visited[st.getInt()]=true}
    val arr = IntArray(n){Int.MAX_VALUE}
    var count = 0
    for(i in 1..100000){if(visited[i])arr[count++]=i}
    var ans = 0
    for(i in 0 until n-2)
        for(j in i+1 until n-1)
            for(k in j+1 until n){
               val tmp = arr[i] + arr[j] + arr[k]
               if(tmp<=m) ans = ans.coerceAtLeast(tmp)
            }
    print(ans)
}