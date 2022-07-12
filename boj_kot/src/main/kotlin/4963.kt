import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.StringTokenizer

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    while(true){
        var st = StringTokenizer(br.readLine())
        fun getInt() = st.nextToken().toInt()

        val w = getInt()
        val h = getInt()

        if(w==0 && h ==0) break

        val graph = Array(h){
            st = StringTokenizer(br.readLine())
            IntArray(w){
                getInt()
            }
        }
        // 그래프 입력 완료

        val visited = Array(h){
            IntArray(w){0}
        }

        var cComps = 0

        fun dfs(y:Int, x:Int){
            visited[y][x] = cComps

            var childList = mutableListOf<IntArray>()
            if(x+1<w){
                childList.add(intArrayOf(y,x+1))
                if(y+1<h) childList.add(intArrayOf(y+1,x+1))
                if(y-1>=0) childList.add(intArrayOf(y-1,x+1))
            }
            if(x-1>=0){
                childList.add(intArrayOf(y,x-1))
                if(y+1<h) childList.add(intArrayOf(y+1,x-1))
                if(y-1>=0) childList.add(intArrayOf(y-1,x-1))
            }
            if(y+1<h) childList.add(intArrayOf(y+1,x))
            if(y-1>=0) childList.add(intArrayOf(y-1,x))

            childList.removeIf{
                visited[it[0]][it[1]] != 0
            }

            childList.forEach {
                val b = it[0]
                val a = it[1]
                if(graph[b][a] == 1) dfs(b, a)
            }
        }

        for(y in 0 until h){
            for(x in 0 until w){
                if(graph[y][x]==1 && visited[y][x] == 0){
                    cComps += 1
                    dfs(y,x)
                }
            }
        }

        bw.write("$cComps\n")



    }
    bw.flush()
    bw.close()
    br.close()
}