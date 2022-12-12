import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,m) = readLine().split(" ").map { it.toInt() }
    val maze = Array(n){readLine().toCharArray()}

    val queue = LinkedList<IntArray>()
    val visited = Array(n){BooleanArray(m){false}}

    // 탈출 가능한 경계선 상의 블럭을 모두 큐에다가 집어넣음
    maze[0].withIndex().forEach {
        if(it.value=='U') queue.add(intArrayOf(0,it.index))
    }
    maze[n-1].withIndex().forEach {
        if(it.value=='D') queue.add(intArrayOf(n-1,it.index))
    }
    for(i in 0 until n){
        if(maze[i][0]=='L') queue.add(intArrayOf(i,0))
        if(maze[i][m-1]=='R') queue.add(intArrayOf(i,m-1))
    }

    val direction = charArrayOf('L','U','R','D')
    val dx = intArrayOf(1,0,-1,0)
    val dy = intArrayOf(0,1,0,-1)

    queue.forEach { visited[it[0]][it[1]]=true }

    // 탈출 가능한 블럭으로 갈 수 있는 길에 방문 표시를 하며 미로를 거슬러 오름
    while(queue.isNotEmpty()){
        val (vy,vx) = queue.pollFirst()
        for(i in 0 until 4){
            val ny = vy + dy[i]
            val nx = vx + dx[i]
            if(ny in 0 until n && nx in 0 until m && !visited[ny][nx]){
                if(maze[ny][nx]==direction[i]) {
                    visited[ny][nx] = true
                    queue.add(intArrayOf(ny,nx))
                }
            }
        }
    }

    // 방문된 칸은 모두 탈출 가능한 칸
    var count = 0
    for(j in 0 until n){
        for(i in 0 until m){
            if(visited[j][i])count+=1
        }
    }
    print(count)
}