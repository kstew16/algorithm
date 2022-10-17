import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt(); val p = st.getInt()
    val redPill = BooleanArray(n+1){false}
    st = StringTokenizer(readLine())
    repeat(st.getInt()){redPill[st.getInt()] = true}

    val visited = IntArray(p){-1}
    val subVisited = BooleanArray(p)
    val party = Array(p){
        st = StringTokenizer(readLine())
        IntArray(st.getInt()){
            st.getInt()
        }
    }
    fun getConnected(visiting:Int,source:Int){
        visited[visiting] = source
        subVisited[visiting] = true
        party[visiting].forEach {
            redPill[it] = true
        }
        l@for(i in 0 until p){
            if(!subVisited[i] && visited[i]==-1){
                subVisited[i] = true
                for(j in party[i].indices){
                    if(redPill[party[i][j]]) continue@l
                }
                getConnected(i,source)
            }
        }
    }
    var ans = 0
    for(i in 0 until p){
        if(visited[i]==-1) {
            getConnected(i,i)
            ans = (visited.count { it==i }).coerceAtLeast(ans)
        }
    }
    print(ans)
}