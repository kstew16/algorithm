import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.DecimalFormat
import kotlin.math.pow

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val wholeBlock = Array(5){
        readLine().map { it=='#' }.toBooleanArray()
    }
    fun getNumForPosition(pos:Int):IntArray{
        val ans = mutableListOf<Int>()
        val offset = pos*4
        when{
            (!wholeBlock[1][offset+1]&&!wholeBlock[3][offset+1]) -> {
                ans.add(8)
                if(!wholeBlock[2][offset+1]) {
                    ans.add(0)
                    if(!wholeBlock[1][offset]&&!wholeBlock[2][offset]&&!wholeBlock[3][offset]&&!wholeBlock[4][offset]&&!wholeBlock[4][offset+1]){
                        ans.add(7)
                        if(!wholeBlock[0][offset]&&!wholeBlock[0][offset+1]) ans.add(1)
                    }

                }
                if(!wholeBlock[1][offset]){
                    if(!wholeBlock[3][offset]) ans.add(3)
                    if(!wholeBlock[3][offset+2]) ans.add(2)
                }
                if(!wholeBlock[3][offset]) ans.add(9)
                if(!wholeBlock[1][offset+2]) {
                    ans.add(6)
                    if(!wholeBlock[3][offset]) ans.add(5)
                }
                if(!wholeBlock[0][offset+1]&&!wholeBlock[3][offset]&&!wholeBlock[4][offset]&&!wholeBlock[4][offset+1]) ans.add(4)
            }
        }
        return ans.toIntArray()
    }
    val possibleNum = Array(n){ getNumForPosition(it) }
    var nCase = 1 // 10^9 이하
    for(i in 0 until n) nCase *= possibleNum[i].size
    if(nCase == 0) print(-1)
    else {
        var average = 0.0
        possibleNum.forEachIndexed { index, pn ->
            val offset = 10.0.pow(n - 1 - index)
            average += (pn.sum().toDouble()/pn.size.toDouble()) * offset
        }
        var strAns = average.toString()
        if(strAns.contains("E")){
            val df = DecimalFormat()
            df.isGroupingUsed = false
            strAns = df.format(average)
        }
        print(strAns)
    }

}