import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,m) = readLine().split(" ").map{it.toInt()}

    // 착수 후보를 작성하는 것은 적군의 돌이 더 많을 때 성능에 악영향을 미칠 수 있으므로 착수 지점 선정은 for 문으로 지정하도록 하겠다
    val field = Array(n){
        val st = StringTokenizer(readLine())
        IntArray(m){
            st.nextToken().toInt()
        }
    }

    val searchQueue = LinkedList<IntArray>()


    val dx = intArrayOf(1,0,-1,0)
    val dy = intArrayOf(0,1,0,-1)

    fun score():Int{
        val visited = Array(n){
            IntArray(m){0}
        }
        val componentArea = mutableListOf<Int>()
        var label = 0

        while(searchQueue.isNotEmpty()){
            val (ty,tx) = searchQueue.pollFirst()
            var count = 0
            var valid = true

            fun dfsConnected(visiting:IntArray,label:Int){
                count += 1
                val (vy,vx) = visiting
                visited[vy][vx] = label
                for(i in 0..3){
                    val ny = vy + dy[i]
                    val nx = vx + dx[i]
                    if(ny in 0 until n && nx in 0 until m && visited[ny][nx] == 0){
                        when(field[ny][nx]){
                            0 -> {
                                // 빈 공간과 이웃한 경우 갇히지 못한 component 이다.  valid 로 기록을 막는다
                                valid = false
                            }
                            1 -> {}
                            2 -> {
                                dfsConnected(intArrayOf(ny,nx),label)
                            }
                        }
                    }
                }
            }

            if(visited[ty][tx]==0){
                label+=1
                dfsConnected(intArrayOf(ty,tx),label)
                if(valid){
                    componentArea.add(count)
                }
            }
        }
        return componentArea.sum()
    }


    var maxScore = 0
    fun place(depth:Int){
        if(depth == 2){
            maxScore = score().coerceAtLeast(maxScore)
        }
        else{
            for(j in 0 until n){
                for(i in 0 until m){
                    // 중복지정 이슈 있음
                    if(field[j][i] == 0){ // 빈 곳이며, 주변에 적의 돌이 있는 경우에만 착수 대상 지점으로 간주
                        // -> 이러면 의미 없는 돌 하나가 있는 경우 시작을 못 함
                        // 따라서 주변의 돌이 있는 경우 + 이미 돌을 둔 경우
                        var valid = false
                        for(index in 0..3){
                            val ny = j + dy[index]
                            val nx = i + dx[index]
                            // 착수한 돌 주변의 적의 돌을 BFS 의 출발지로 삼는다
                            //if(ny in 0 until n && nx in 0 until m && field[ny][nx]==2){
                            if(ny in 0 until n && nx in 0 until m && field[ny][nx]==2){
                                valid = true
                                searchQueue.add(intArrayOf(ny,nx))
                            }
                        }
                        if(!valid && depth==1){
                            // 1번 돌이 이미 의미있는 착수를 한 경우
                            // 의미있는 착수를 1개도 할 수 없는 경우는?
                            valid = true
                        }
                        if(valid){
                            field[j][i] = 1
                            place(depth+1)
                            field[j][i] = 0
                        }
                    }
                }
            }
        }
    }
    place(0)
    print(maxScore)
}