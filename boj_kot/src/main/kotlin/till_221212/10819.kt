import java.lang.Integer.max
import kotlin.math.abs

fun main() {
    val br = System.`in`.bufferedReader()
    val bw = System.`out`.bufferedWriter()
    val n = br.readLine().toInt()
    var stack = mutableListOf<Int>()
    var visited = ((0 until n).map { false }).toBooleanArray()
    var maxVal = 0
    val input = (br.readLine().split(" ").map { it.toInt() } as ArrayList<Int>).sorted()

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
    bw.write(maxVal.toString())
    br.close()
    bw.close()
}