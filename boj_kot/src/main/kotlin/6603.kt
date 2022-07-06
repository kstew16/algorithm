import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter


fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    var stack = mutableListOf<Int>()
    val input = mutableListOf<IntArray>()


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

    do{
        var str = br.readLine()
        input.add((str.split(" ").map { it.toInt() }).toIntArray())
    }
    while(!input.last().contentEquals(intArrayOf(0)))

    input.forEach {
        if(it[0] != 0){
            var visited = ((0 until it[0]).map { false }).toBooleanArray()
            var target = it[0]
            var arr = it.sliceArray(1 .. target).sorted().toIntArray()
            dfs(0, target ,arr, visited,0)
            bw.write("\n")
        }
    }
    bw.flush()
    bw.close()
    br.close()
}