import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.StringTokenizer
// 답은 안 봤지만 푸는거 매우 오래 걸렸음. 2시간 반 쯤, 예외사항을 발견하지 못했고 못할 만 한 예외였긴 함
// 제한시간에는 넉넉하게 맞지만... -> 다른 분은 착수 후 주변 상대 돌 탐색이 아니라 상대 돌 탐색 후 착수 지점 지정으로 시간 당김
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

        while(searchQueue.isNotEmpty()) {
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
                                valid = false
                                return
                            }
                            2 -> dfsConnected(intArrayOf(ny,nx),label)
                        }
                    }
                }
            }

            if(visited[ty][tx]==0){
                label+=1
                dfsConnected(intArrayOf(ty,tx),label)
                if(valid)componentArea.add(count)
            }
        }
        return componentArea.sum()
    }


    var maxScore = 0
    val placed = Array(2){intArrayOf(-1,-1)}
    fun place(depth:Int){
        if(depth == 2){
            placed.forEach {
                // 배치된 돌의 주변 점을 스타팅포인트로 설정, 스타팅포인트부터 탐색
                val (j,i) = it
                for(index in 0..3){
                    val ny = j + dy[index]
                    val nx = i + dx[index]
                    if(ny in 0 until n && nx in 0 until m && field[ny][nx]==2) searchQueue.add(intArrayOf(ny,nx))
                }
            }
            maxScore = score().coerceAtLeast(maxScore)
        }
        else{
            for(j in 0 until n){
                for(i in 0 until m){
                    if(field[j][i] == 0){ // 착수 가능한 지점에
                        var valid = false
                        for(index in 0..3){
                            val ny = j + dy[index]
                            val nx = i + dx[index]
                            if(ny in 0 until n && nx in 0 until m && field[ny][nx]==2){
                                valid = true
                                break
                            }
                        }
                        if(valid){
                            // 의미 있는 돌을 둔 자리를 기억하고 다음 돌 두기
                            field[j][i] = 1
                            placed[depth] = intArrayOf(j,i)
                            place(depth+1)
                            placed[depth] = intArrayOf(0,0)
                            field[j][i] = 0
                        }
                    }
                }
            }
            if(depth == 1 && placed[depth].contentEquals(intArrayOf(-1,-1))){
                // 두 번째 돌에서 조건문을 다 돌았는데도 의미있는 착수를 진행할 수 없는 경우, 첫 번째 돌만 두는 것으로 간주
                placed[0].withIndex().forEach {
                    placed[1][it.index] = placed[0][it.index]
                }
                place(depth+1)
            }
        }
    }
    place(0)
    print(maxScore)
}