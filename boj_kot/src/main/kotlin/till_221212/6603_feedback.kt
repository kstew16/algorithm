import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.StringTokenizer


fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    var stack = mutableListOf<Int>()

    fun dfs(depth:Int, target: Int, array:IntArray, visited:BooleanArray, source:Int) {
        when (depth) {
            6 -> {
                bw.write(stack.joinToString(" "))
                bw.write("\n")
            }

            else -> {
                for (i in 0 until target) {
                    var current = array[i]
                    if (!visited[i] && (source<current)) {
                        stack.add(current)
                        visited[i] = true
                        dfs(depth + 1, target, array, visited, current)
                        stack.removeAt(stack.size-1)
                        visited[i] = false
                    }
                }
            }
        }
    }

    while(true){
        val tk = StringTokenizer(br.readLine())
        val n = tk.nextToken().toInt()
        if(n==0) break
        val input = Array(n){ tk.nextToken().toInt()}
        val visited = ((0 until n).map { false }).toBooleanArray()
        dfs(0,n,input.toIntArray(),visited,0)
        bw.write("\n")
    }

    bw.flush()
    bw.close()
    br.close()
}