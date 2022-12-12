import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.StringTokenizer
// 176 ms 13MB
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    var st:StringTokenizer
    repeat(readLine().toInt()){
        val n = readLine().toInt()
        val depthLimit = n/2
        val points = Array(n){
            st = StringTokenizer(readLine())
            IntArray(2){st.getInt()}
        }
        val sumX = points.map { it[0] }.fold(0) {total,next -> total+next}
        val sumY = points.map { it[1] }.fold(0) {total,next -> total+next}
        var ans = Double.MAX_VALUE
        // index : 몇 번째 점을 음수 포인트로 사용할지
        // depth : 몇 개의 점을 음수 포인트로 사용했는지
        fun checkCase(index:Int,depth:Int,sX:Int,sY:Int){
            if(depth == depthLimit) ans = ans.coerceAtMost(Math.sqrt(1.0*sX*sX+1.0*sY*sY))
            else{
                // 남은 점의 개수가 찍어야 하는 점의 개수보다 작을 때
                if((n-index)<(depthLimit-depth)) return
                // 이번 인덱스를 음수로 쓰거나 안 쓰거나
                checkCase(index+1,depth+1,sX-2*points[index][0],sY-2*points[index][1])
                checkCase(index+1,depth,sX,sY)
            }
        }
        checkCase(0,0,sumX,sumY)
        bw.write("$ans\n")
    }
    bw.flush()
    bw.close()
    close()
}