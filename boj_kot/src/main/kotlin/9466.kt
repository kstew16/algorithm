import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val t = readLine().toInt()
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    repeat(t){
        val n = readLine().toInt()
        val st = StringTokenizer(readLine())
        val visited = BooleanArray(n){false}
        //val teamed = BooleanArray(n){false}
        val failed = BooleanArray(n){false}
        val choice = IntArray(n){st.nextToken().toInt()-1}
        // 출발지점으로 돌아와야만 성공, 한 번 실패한 루트를 다시 밟을 필요는 없음
        fun theGameOfDFS(start:Int,visiting:Int):Boolean{
            if(visiting==start && visited[start]) return true
            val nextChoice = choice[visiting]
            if(!visited[nextChoice] && !failed[nextChoice]){
                visited[nextChoice] = true
                if(theGameOfDFS(start,nextChoice)){
                    // 팀 결성에 성공한 경우 return 루트 전체에 보고
                    return true
                }
                // 팀 결성에 실패한 경우 재방문 가능하게
                else visited[nextChoice] = false
            }
            return false
        }
        for(i in 0 until n) if(!visited[i]){
            if(theGameOfDFS(i,i)) visited[i] = true
            else failed[i] = true
            //else
        }
        bw.write("${visited.count { !it }}\n")
    }
    bw.flush()
    bw.close()
    close()
}