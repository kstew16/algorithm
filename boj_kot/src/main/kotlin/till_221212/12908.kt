import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
import kotlin.math.abs
// 은우야 보거라 숫자가 크면 음? 오버플로우? 생각만 하고 지나가지 말고 좀 따져봐 좀!!!
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (xs,ys) = readLine().split(" ").map { it.toLong() }
    val (xe,ye) = readLine().split(" ").map { it.toLong() }
    fun StringTokenizer.getLong() = nextToken().toLong()
    val choice = mutableListOf<LongArray>()


    fun getDistance(sx:Long,sy:Long,ex:Long,ey:Long):Long{
        return (abs(ex-sx) + abs(ey-sy))
    }

    // 의미 있는 텔레포트만 텔레포트로 침
    repeat(3){
        val st = StringTokenizer(readLine())
        val tsx = st.getLong()
        val tsy = st.getLong()
        val tex = st.getLong()
        val tey = st.getLong()
        if(getDistance(tsx,tsy,tex,tey)>=10L) choice.add(longArrayOf(tsx,tsy,tex,tey))
    }

    val isChoiceUsed = BooleanArray(choice.size){false}



    var minDistance = getDistance(xs,ys,xe,ye).toLong()
    // 할 수 있는 일
    // 도착지로 직선거리로 간다
    // 텔레포트 지점으로 가서 텔레포트한다
    fun takeChoice(curX:Long,curY:Long,distance:Long){
        if(curX == xe && curY == ye) minDistance = distance.coerceAtMost(minDistance)
        else{
            // 도착지로 직선거리로 간다
            takeChoice(xe,ye,distance + getDistance(curX,curY,xe,ye))

            // 사용 안 해 본 텔포가 있으면 가서 써 봄
            choice.withIndex().forEach {
                if(!isChoiceUsed[it.index]){
                    isChoiceUsed[it.index] = true
                    val (stx,sty,etx,ety) = it.value
                    takeChoice(etx,ety,distance + getDistance(curX,curY,stx,sty) + 10L)
                    takeChoice(stx,sty,distance + getDistance(curX,curY,etx,ety) + 10L)
                    isChoiceUsed[it.index] = false
                }
            }
        }
    }
    takeChoice(xs,ys,0)
    print(minDistance)

}