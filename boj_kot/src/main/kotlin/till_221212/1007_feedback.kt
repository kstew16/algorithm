import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.StringTokenizer
import kotlin.math.pow
// 208ms 44MB
// 거의 2배나 빠른 방식이 있길래 확인해봤는데
// 1. 배열의 합을 여러번 구하는 대신, 미리 구해놓고 변하는 부분만 반영하여 가속
// 2. hypot 이라는 메소드를 사용했었는데, 두 원소의 제곱의 합의 제곱근을 구하는 메소드임
// 문제는 이게 굉장히 무거웠다는 거지, 나는 오버플로우가 있을 걸 예상했는데, 두 원소의 범위를 최악을 고려한 나머지 실제로 발생하지 않는 오버플로우를 넣었음
// 근데 Double 형은 1.79 *10^308까지 표현할 수 있더라 ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ 아오 지수방식...

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    // 비트마스킹으로 해당 포인트와, 정반대 케이스를 방문했는지 확인하는 케이스
    infix fun Int.on(i:Int) = this or (1 shl i)
    fun StringTokenizer.getInt() = this.nextToken().toInt()

    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    repeat(readLine().toInt()){
        val n = readLine().toInt()
        val depthLimit = n/2
        val points = Array(n){
            val st = StringTokenizer(readLine())
            IntArray(2){st.getInt()}
        }
        val sumX = points.map { it[0] }.fold(0) {total,next -> total+next}
        val sumY = points.map { it[1] }.fold(0) {total,next -> total+next}
        val visited = BooleanArray((2.toDouble().pow((n+1))-1).toInt())
        var ans = Double.MAX_VALUE
        // index : 몇 번째 점을 음수 포인트로 사용할지
        // depth : 몇 개의 점을 음수 포인트로 사용했는지
        // currentBit visited 표시를 위한 비트
        fun checkCase(index:Int,depth:Int,sX:Int,sY:Int,currentBit:Int){
            // 정 반대의 조합은 결국 크기가 같은 벡터 매칭을 만들어냄
            visited[currentBit] = true
            visited[-currentBit.inv()] = true
            if(depth == depthLimit) ans = ans.coerceAtMost(kotlin.math.sqrt(1.0*sX*sX+1.0*sY*sY))
            else{
                // 남은 점의 개수가 찍어야 하는 점의 개수보다 작을 때
                if((n-index)<(depthLimit-depth)) return
                // 이번 인덱스를 음수로 쓰거나 안 쓰거나
                if(!visited[currentBit on index])
                    checkCase(index+1,depth+1,sX-2*points[index][0],sY-2*points[index][1],currentBit on index)
                checkCase(index+1,depth,sX,sY,currentBit)
            }
        }
        checkCase(0,0,sumX,sumY,0)
        bw.write("$ans\n")
    }
    bw.flush()
    bw.close()
    close()
}