import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Integer.min
import java.util.*


fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val n = Integer.parseInt(br.readLine())

    var stack = mutableListOf<Int>()
    var visited = ((0 until n).map { false }).toBooleanArray()

    val input = Array(n){
        with(StringTokenizer(br.readLine())) {
            IntArray(n) { Integer.parseInt(nextToken()) }
        }
    }

    var minVal = 5000000

    fun dfs(depth:Int) {
        when (depth) {
            n -> {
                var sum = 0
                for( i in 0 until n){
                    var distance =input[stack[i%n]][stack[(i+1)%n]]
                    distance = if(distance==0){
                        1000001
                    }
                    else{
                        distance
                    }
                    sum += distance
                }
                minVal = min(sum,minVal)
            }

            else -> {
                for (i in 0 until n) {
                    if (!visited[i]) {
                        stack.add(i)
                        visited[i] = true
                        dfs(depth + 1)
                        stack.removeAt(stack.size-1)
                        visited[i] = false
                    }
                }
            }
        }
    }

    dfs(0)
    println(minVal)
    br.close()
}