import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
// 골드 2인데 한 번에 풀었음, 패인 말고 승인은 여러 개의 예시 그래프를 넣어본 것, 루트를 포함하지 않는 경로에서 지름을 발견하는 법을 추가함
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val graph = Array<MutableList<IntArray>>(n){
        mutableListOf()
    }
    repeat(n){
        with(StringTokenizer(readLine())){
            val from = nextToken().toInt()-1
            do{
                val to = nextToken().toInt()-1
                if(to != -2)graph[from].add(intArrayOf(to,nextToken().toInt()))
                else break
            }while(to!=-2)
        }
    }

    val visited = BooleanArray(n){false}
    var longest = 0

    fun dfs(visiting:Int,distance:Int):Int{
        var longest1 = 0
        var longest2 = 0
        var deadEnd = true
        visited[visiting] = true
        graph[visiting].forEach {
            if(!visited[it[0]]) {
                val localDistance = dfs(it[0],distance+it[1])
                if(longest1<localDistance){
                    longest2 = longest1 // 와 미는거 까먹었네
                    longest1 = localDistance
                }
                else if(longest2<localDistance)longest2 = localDistance
                deadEnd = false
            }
        }
        return if(deadEnd) distance
        else{
            // 자신의 자식들 간의 경로 중 지름이 될 만한 것이 있는지 확인
            // distance 에는 루트로 삼은 노드로부터의 거리가 들어있기 때문에, 자신을 기준으로 거리를 측정하도록 해 줌
            val localLongest = (if(longest1!=0) longest1 -distance else 0) + (if(longest2!=0) longest2 - distance else 0)
            longest = localLongest.coerceAtLeast(longest)
            // 자식들 간의 경로 중에서 제일 긴 거 하나를 부모한테 전달(백트래킹)
            longest1
        }
    }
    // 지름은 어디서 봐도 똑같음.
    dfs(0,0)
    println(longest)
}