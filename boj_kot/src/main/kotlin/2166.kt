import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.NumberFormat
import java.util.StringTokenizer
import kotlin.math.abs

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getLong() = this.nextToken().toLong()
    var st : StringTokenizer
    val n = readLine().toInt()
    val xCoords = LongArray(n+1)
    val yCoords = LongArray(n+1)
    repeat(n){
        st = StringTokenizer(readLine())
        xCoords[it] = st.getLong()
        yCoords[it] = st.getLong()
    }
    xCoords[n] = xCoords[0]
    yCoords[n] = yCoords[0]
    var term = 0L
    for(i in 0 until n) term += (xCoords[i]*yCoords[i+1] - yCoords[i]*xCoords[i+1])
    print((NumberFormat.getInstance().apply { this.isGroupingUsed=false;this.minimumFractionDigits=1}).format((abs(term)).toDouble()/2.0))
}