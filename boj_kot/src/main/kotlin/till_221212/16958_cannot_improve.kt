import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*
import kotlin.math.abs
import kotlin.math.min
// 왜 플로이드-와샬 알고리즘에서 대칭성을 처리하면 안 되는가에 대한 답이다
// 대칭성을 검사하고 이를 단방향으로 유지하는데 3개의 조건문이 사용되었다, 이를 통해서 V^3/2 번의 (조건문+덧셈) 연산이 줄어들었다.
// 조건문과 덧셈, 대입이 같은 수준의 오버헤드를 가진다고 가정하였을 때, V^3 * (1+1)의 연산은 V^3/2 * (3+1+1+3) 로, 연산 횟수는 결국 2배
// 조건문은 beq, bne 등으로 대치되고, 덧셈은 add, 대입은 mov 로 대치되므로 오버헤드가 비슷하다는 가정은 설득력이 있을 것이다
// 실제로 메모리 대칭성을 해소하지 않은 상황에서 단순한 소요 시간이 2.5배 이상으로 증가하는것을 확인하였다. 이상

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
            if(from>=to) 0
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
                // 연산 1/2배로 만드는 조건문
                if(from<to){
                    // 처리하는데 4배의 오버헤드가 생긴 것을 볼 수 있음
                    var distanceFT = distances[from][to]
                    var distanceFM = if(from<=mid) distances[from][mid] else distances[mid][from]
                    var distanceMT = if(mid<=to) distances[mid][to] else distances[to][mid]
                    if(distanceFT > distanceFM + distanceMT){
                        distances[from][to] = distanceFM+distanceMT
                    }
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