import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*
import kotlin.math.abs
import kotlin.math.min
// 플로이드 - 와샬 알고리즘, 음의 사이클이 없는 그래프에서, 모든 정점 간의 최단거리를 구할 떄 사용하며 O(V^3)의 시간 복잡도를 갖는다
// 대칭성이 있는 그래프라고 하더라도, 인접 행렬의 절반만을 처리하게 조건을 처리하는 과정이 오버헤드가 더 크기 때문에, V^3 번 다 처리해주도록 하자
// 참고로 덧셈 정도의 가벼운 연산은 10억번의 계산도 1초 내에 처리할 수 있다고 한다.

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n, shortcutDistance) = readLine().split(" ").map { it.toInt() }
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    val cities = Array(n){
        val st = StringTokenizer(readLine())
        intArrayOf(st.getInt(),st.getInt()-1,st.getInt()-1)
    }
    val distances = Array(n){
        from->
        // 더 큰 곳으로만 갈 수 있음, 쌍방향그래프라 중복 있거든
        IntArray(n){
            to ->
            if(from==to) 0
            else{
                val (fromSpecial,fromR,fromC) = cities[from]
                val (toSpecial, toR, toC) = cities[to]
                var distance = abs(fromR-toR) + abs(fromC-toC)
                // 순간이동 가능한 경우 더 짧은 거리를 선택
                if(fromSpecial == 1 && toSpecial==1) distance = min(shortcutDistance,distance)
                distance
            }
        }
    }

    for(mid in 0 until n){
        for(from in 0 until n){
            for(to in 0 until n){
                if(distances[from][to] > distances[from][mid] + distances[mid][to]){
                    distances[from][to] = distances[from][mid] + distances[mid][to]
                }
            }
        }
    }


    //  문제 다 풀고 출력하자
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    val m = readLine().toInt()
    repeat(m){
        val st = StringTokenizer(readLine())
        var from = st.getInt()-1
        var tmp = st.getInt()-1
        var to = 0
        if(tmp>from) to = tmp
        else{
            to = from
            from = tmp
        }
        bw.write("${distances[from][to]}\n")
    }
    bw.flush()
    bw.close()
    close()

}