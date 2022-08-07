import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (n, m, d) = readLine().split(" ").map { it.toInt() }
    var nEnemy = 0
    var maxCaught = 0
    val visited = IntArray(m){0}
    val map = Array(n) {
        val st = StringTokenizer(readLine())
        IntArray(m) {
            val enemy = st.nextToken().toInt()
            if(enemy == 1) nEnemy++
            enemy
        }
    }

    // 게임을 진행시킬 맵, 성까지 포함
    val simMap = Array(n+1){ y->
        if(y<n){
            IntArray(m){ x->
                map[y][x]
            }
        }
        else{
            IntArray(m){0}
        }
    }

    fun proceedGame(turn:Int):Int{
        var eliminated = 0
        for(i in 0 until m){
            if(simMap[n-turn][i]==1){
                eliminated+= 1
                simMap[n-turn][i]=0
            }
            // 궁수올리기 사실은 필요없음
            simMap[n-turn+1][i] = 0
        }
        return eliminated
    }
    fun hunt(turn:Int):Int{
        val dx = intArrayOf(-1,0,1)
        val dy = intArrayOf(0,-1,0)

        val huntList = mutableListOf<IntArray>()
        val queue = LinkedList<IntArray>()
        // n 행의 궁수들이 일제히 사격, 사격을 BFS 로 진행하고 HuntSet 에 추가된 적을 제외, count 증가
        for(i in 0 until m){
            if(visited[i]==1){
                queue.clear()
                val targetedMap = Array(n){
                    BooleanArray(m){false}
                }

                // 궁수의 바로 윗 줄을 확장의 시작으로 하여 큐잉
                if(n-(turn) in 0 until n) {
                    queue.add(intArrayOf(n-(turn),i,1))
                    targetedMap[n-(turn)][i] = true
                }

                while(queue.isNotEmpty()){
                    val (curY,curX,curD) = queue.pollFirst()
                    if(simMap[curY][curX]==1){
                        huntList.add(intArrayOf(curY,curX))
                        break
                    }
                    else{
                        // 확장 가능한 곳들을 다 큐잉
                        for(i in 0..2){
                            val nx = curX + dx[i]
                            val ny = curY + dy[i]
                            if(nx in 0 until m && ny >=0 && curD<d && !targetedMap[ny][nx]){
                                targetedMap[ny][nx] = true
                                queue.add(intArrayOf(ny,nx,curD+1))
                            }
                        }
                    }
                }
            }
        }
        var caught = 0
        huntList.forEach {
            if(simMap[it[0]][it[1]]==1){
                caught += 1
                simMap[it[0]][it[1]]=0
            }
        }
        return caught
    }
    fun resetGame(){
        for(y in 0 until n){
            for(x in 0 until m){
                simMap[y][x] = map[y][x]
            }
        }
    }
    fun playGame(){
            var enemyCaught = 0
            var enemyLeft = nEnemy
            var turn = 1

            while(enemyLeft>0 && turn!=n+1){
                // 조합에 따라 궁수 배치
                for(i in visited.indices) simMap[n-(turn-1)][i] = visited[i]
                // 배치된 궁수들 사냥
                val newCaught = hunt(turn)
                // 궁수 한 칸 올리고 아랫줄 세어서 게임에서 제외
                enemyLeft -= newCaught
                enemyLeft -= proceedGame(turn)
                enemyCaught +=  newCaught
                turn+=1
            }
        maxCaught = enemyCaught.coerceAtLeast(maxCaught)
    }
    fun placeWithDfs(visiting:Int,depth:Int){
        visited[visiting] = 1
        if(depth == 3){
            resetGame()
            playGame()
        }
        else{
            for(i in visiting+1 until m){
                if(visited[i] == 0){
                    placeWithDfs(i,depth+1)
                }
            }
        }
        visited[visiting] = 0
    }

    for(i in visited.indices) {
        placeWithDfs(i, 1)
    }

    print(maxCaught)
}