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
        // !failed != teamed, 미방문일 수도 있음.
        val teamed = BooleanArray(n){false}
        val failed = BooleanArray(n){false}
        val choice = IntArray(n){st.nextToken().toInt()-1}
        // dfs 하면서 탐색하다가 cycle 이 발생하면 cycle 의 시작점까지 리턴하며 팀 결성에 성공한 것으로 취급, 시작점부터는 다 fail 취급
        fun findCycle(visiting:Int):Int{
            visited[visiting] = true
            val nextChoice = choice[visiting]
            if(!visited[nextChoice]){
                val crashingPoint = findCycle(nextChoice)
                return if(visiting!=crashingPoint){
                    if(crashingPoint==100000){
                        failed[visiting] = true
                        crashingPoint
                    }else{
                        teamed[visiting] = true
                        crashingPoint
                    }
                }
                // 여기까지가 사이클
                else{
                    teamed[visiting] = true
                    // 사이클 내에 없었던 애들은 다 실패처리
                    100000
                }
            }
            // 이미 팀 결성된 지점이나 실패지점을 갔다는 건 잘못된 사다리를 탔다는 것
            else if(teamed[nextChoice]||failed[nextChoice]) {
                failed[visiting] = true
                return 100000
            }
            // 사이클 확인이 안 된, 이미 방문한 지점을 다시 갔다는 건 새 사이클이 발생했다는 것, 이 때 사이클의 시작지는 nextChoice
            // 내가 사이클 시작인 경우까지 고려해야함
            else if(visiting == nextChoice){
                teamed[visiting] = true
                return 100000
            }
            else {
                // 내가 사이클 찾은 경우 나부터 nextChoice 까지 팀 표시
                teamed[visiting] = true
                return nextChoice
            }
        }
        for(i in 0 until n) if(!visited[i] && !teamed[i] && !failed[i]) findCycle(i)
        bw.write("${failed.count { it }}\n")
    }
    bw.flush()
    bw.close()
    close()
}