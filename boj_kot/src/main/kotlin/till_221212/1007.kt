import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.StringTokenizer
import kotlin.math.hypot
import kotlin.math.pow

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    // 비트마스킹으로 해당 포인트를 음수벡터로 사용하는 상황과 양수벡터로 사용하는 상황을 구별하는 방식
    infix fun Int.on(i:Int) = this or (1 shl i)
    infix fun Int.chk(i:Int) = this and (1 shl i)>=1
    fun StringTokenizer.getInt() = this.nextToken().toInt()

    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    repeat(readLine().toInt()){
        val n = readLine().toInt()
        val depthLimit = n/2
        val points = Array(n){
            val st = StringTokenizer(readLine())
            IntArray(2){st.getInt()}
        }
        val visited = BooleanArray((2.toDouble().pow((n+1))-1).toInt())
        var ans = Double.MAX_VALUE
        fun getSizeForCase(case:Int):Double{
            // feedback 이렇게 매번 벡터의 합을 구한 게 효율화할 수 있는 구간임
            var sX = 0
            var sY = 0
            for(i in 0 until n){
                if(case chk i){
                    sX += points[i][0]
                    sY += points[i][1]
                }else{
                    sX -= points[i][0]
                    sY -= points[i][1]
                }
            }
            // 이 hypot 이 미친듯이 무거운 메소드였음.
            return hypot(sX.toDouble(),sY.toDouble())
        }
        // index : 몇 번째 비트에 표시할 차례인지
        // depth : 몇 개의 비트에 표시했는지
        // currentBit 넘기고 있는 비트 정보
        fun makeCaseBit(index:Int,depth:Int,currentBit:Int){
            // 정 반대의 조합은 결국 크기가 같은 벡터 매칭을 만들어냄
            visited[currentBit] = true
            visited[-currentBit.inv()] = true
            if(depth == depthLimit) {
                // 컴비네이션에 따라 벡터를 계산하는 함수 삽입
                ans = getSizeForCase(currentBit).coerceAtMost(ans)
            }else{ // if(depth<depthLimit)
                // 점의 인덱스는 0~n-1
                // depthLimit - depth 개의 점을 더 찍어야 하는데, n-1 ~ index 개의 점이 남았을 때, 즉 남은 점의 개수가 찍어야 하는 점의 개수보다 작을 때
                if(index>=n || (n-index)<(depthLimit-depth)) return
                // 이번 비트에 체크 하거나 안 하거나
                if(!visited[currentBit on index]) makeCaseBit(index+1,depth+1,currentBit on index)
                makeCaseBit(index+1,depth,currentBit)
            }
        }
        makeCaseBit(0,0,0)
        bw.write("$ans\n")
    }
    bw.flush()
    bw.close()
    close()
}