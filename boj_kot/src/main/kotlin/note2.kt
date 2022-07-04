fun main(){
    val n = readln().toInt()
    var ans = mutableListOf<String>()
    var stack = mutableListOf<Int>()
    var visited = ((0 until n).map { false }).toMutableList()
    var depth = 0


    fun dfs(depth:Int) {
        when (depth) {
            n -> ans.add(stack.joinToString(separator = " "))
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
    ans.forEach {
        println(n)
        println(it) }
}