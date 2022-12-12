import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.Buffer
import java.util.StringTokenizer

// 내가 예상한 것보다 최댓값이 커서 배열 백트래킹 바운드에러로 2번쯤 틀림
// 와 뭐임 큰 거에서 작은 걸로 가면 순간이동을 (2x) 못해서 한 칸씩만 가야되네 이걸 생각못했네
private const val MAX_VAL = 100001
private fun main(){

    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    val st = StringTokenizer(br.readLine())
    val n = st.nextToken().toInt()
    val k = st.nextToken().toInt()
    br.close()

    if(n==k){
        // 찾을 필요가 없는 경우 예외처리
        bw.write("0\n$n")
        bw.flush()
        bw.close()
        return
    }

    val visited = Array(MAX_VAL){
        intArrayOf(0,0)
    }
    fun dx(x:Int):IntArray = intArrayOf(x+1,x-1,x*2)


    val queue = IntArray(MAX_VAL*3){0}
    var bottom = 0
    var top = 0
    queue[top++] = n
    // 0초 후에 방문, 0에서 방문
    visited[n] = intArrayOf(0,0)

    while(top!=bottom){
        val x = queue[bottom++]
        dx(x).forEach {
            if(it in 0 until MAX_VAL){
                // 미방문지 발견시
                if(visited[it][0] == 0){
                    queue[top++] = it
                    visited[it][0] = visited[x][0] + 1
                    visited[it][1] = x
                    if(it==k){
                        // 최초 방문 시점을 기록하고
                        bw.write("${visited[k][0]}\n")
                        val path = IntArray(MAX_VAL){-1}
                        var pathTop = 0
                        var source = k
                        path[pathTop++] = k
                        while(source!=n){
                            source = visited[source][1]
                            path[pathTop++] = source
                        }
                        bw.write(path.filter { pathData -> pathData!=-1 }.reversed().joinToString(" "))
                        bw.flush()
                        bw.close()
                        return
                    }
                }
            }
        }
    }

}