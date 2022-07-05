import java.lang.Integer.max
import kotlin.math.abs
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val n = Integer.parseInt(br.readLine())
    var input = with(StringTokenizer(br.readLine())) {
        IntArray(n) { Integer.parseInt(nextToken()) }
    }
    var stack = mutableListOf<Int>()
    var visited = ((0 until n).map { false }).toBooleanArray()
    var maxVal = 0
    // val input = (br.readLine().split(" ").map { it.toInt() } as ArrayList<Int>).sorted()

    fun dfs(depth:Int) {
        when (depth) {
            // n -> bw.write(stack.joinToString(separator = " ") + "\n")
            n-> {
                var sum = 0
                for(i in 0 until n-1) {
                    sum += abs(stack[i] - stack[i + 1])
                }
                maxVal = max(maxVal,sum)
            }
            else -> {
                var last = -200
                for (i in 0 until n) {
                    var current = input[i]
                    if (!visited[i] && current != last) {
                        last = current
                        stack.add(current)
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
    println(maxVal)
    br.close()
}