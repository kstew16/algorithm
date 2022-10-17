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

    val visited = IntArray(n){-1}
    val party = Array(p){
        st = StringTokenizer(readLine())
        IntArray(st.getInt()){
            st.getInt()
        }
    }
    fun getConnected(visiting:Int,source:Int){
        visited[visiting] = source
        party[visiting].forEach {
            redPill[it] = true
        }
        l@for(i in 0 until p){
            if(visited[i]==-1){
                var valid = true
                for(j in party[i].indices){
                    valid = ((!redPill[party[i][j]])&&valid)
                    if(!valid){
                        continue@l
                    }
                }
            }
        }
    }

}