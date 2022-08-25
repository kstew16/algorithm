import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
import kotlin.math.abs
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (xs,ys) = readLine().split(" ").map { it.toInt() }
    val (xe,ye) = readLine().split(" ").map { it.toInt() }
    fun StringTokenizer.getInt() = nextToken().toInt()
    val choice = mutableListOf<IntArray>()


    fun getDistance(sx:Int,sy:Int,ex:Int,ey:Int):Int{
        return (abs(ex-sx) + abs(ey-sy))
    }

    // 의미 있는 텔레포트만 텔레포트로 침
    repeat(3){
        val st = StringTokenizer(readLine())
        val tsx = st.getInt()
        val tsy = st.getInt()
        val tex = st.getInt()
        val tey = st.getInt()
        choice.add(intArrayOf(tsx,tsy,tex,tey))
        choice.add(intArrayOf(tex,tey,tsx,tsy))
        //if(getDistance(tsx,tsy,tex,tey)>=10) choice.add(intArrayOf(tsx,tsy,tex,tey))
    }

    val isChoiceUsed = BooleanArray(choice.size){false}



    var minDistance = getDistance(xs,ys,xe,ye)
    // 할 수 있는 일
    // 도착지로 직선거리로 간다
    // 텔레포트 지점으로 가서 텔레포트한다
    fun takeChoice(curX:Int,curY:Int,distance:Int){
        if(curX == xe && curY == ye) minDistance = distance.coerceAtMost(minDistance)
        else{
            // 도착지로 직선거리로 간다
            takeChoice(xe,ye,distance + getDistance(curX,curY,xe,ye))

            // 사용 안 해 본 텔포가 있으면 가서 써 봄
            choice.withIndex().forEach {
                if(!isChoiceUsed[it.index]){
                    isChoiceUsed[it.index] = true
                    val (stx,sty,etx,ety) = it.value
                    takeChoice(etx,ety,distance + getDistance(curX,curY,stx,sty) + 10)
                    takeChoice(stx,sty,distance + getDistance(curX,curY,etx,ety) + 10)
                    // 그래 뭐 씨 안 써도 된다 쳐..
                    takeChoice(stx,sty,distance + getDistance(curX,curY,stx,sty))
                    takeChoice(etx,ety,distance + getDistance(curX,curY,etx,ety))
                    isChoiceUsed[it.index] = false
                }
            }
        }
    }
    takeChoice(xs,ys,0)
    print(minDistance)


}