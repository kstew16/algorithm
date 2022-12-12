fun main(){
    val br = System.`in`.bufferedReader()
    val bw = System.`out`.bufferedWriter()
    val n = br.readLine().toInt()
    var stack = mutableListOf<Int>()
    var visited = ((0 until n).map { false }).toBooleanArray()
    var depth = 0


    fun dfs(depth:Int) {
        when (depth) {
            n -> bw.write(stack.joinToString(separator = " ") + "\n")
            else -> {
                for (i in 0 until n) {
                    if (!visited[i]) {
                        stack.add(i+1)
                        visited[i] = true
                        dfs(depth + 1)
                        stack.removeAt(stack.size-1)
                        visited[i] = false
                    }
                }
            }
        }
    }

    dfs(depth)
    bw.close()
}