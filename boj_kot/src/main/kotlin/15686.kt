import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main()= with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,m) = readLine().split(" ").map { it.toInt() }
    // 각 치킨집들만이 있을 때의 치킨 거리를 탐색,
    var nHouse = 0

    val houseX = IntArray(2*n){-1}
    val houseY = IntArray(2*n){-1}
    var nStore = 0
    val store = Array(13){
        intArrayOf(-1,-1)
    }
    val field = Array(n){
        y ->
        val st = StringTokenizer(readLine())
        IntArray(n){
            x ->
            val input = st.nextToken().toInt()
            if(input == 2) store[nStore++] = intArrayOf(y,x)
            else if (input == 1) {
                houseY[nHouse] = y
                houseX[nHouse++] = x
            }
            input
        }
    }
    val visited = BooleanArray(store.size){false}
    var minDistanceSum = Int.MAX_VALUE
    fun calculateDistance(){
        // 현 상태에서 visited 가 false 인 애들만 제외해서
        // 치킨집에서 BFS 때려서 집 별로 최소거리 구하기?
        val minDistances = IntArray(2*n){ Int.MAX_VALUE}
        for(i in 0 until nStore){
            if(visited[i]) {
                val (curY,curX) = store[i]
                for(j in 0 until nHouse){
                    val curDistance = kotlin.math.abs(curY - houseY[j]) + kotlin.math.abs(curX - houseX[j])
                    minDistances[j] = curDistance.coerceAtMost(minDistances[j])
                }
            }
        }
        minDistanceSum = (minDistances.withIndex().sumOf { if(it.index<nHouse)it.value else 0 }).coerceAtMost(minDistanceSum)
    }
    fun dfs(visiting:Int,  depth:Int){
        if(depth==m){
            calculateDistance()
            return
        }
        for(i in visiting+1 until nStore){
            if(!visited[i]){
                visited[i] = true
                dfs(i,depth+1)
                visited[i] = false
            }
        }

    }

    for(i in 0 until nStore) {
        visited[i] = true
        dfs(i,1)
        visited[i] = false
    }
    println(minDistanceSum)
}