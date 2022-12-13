import java.util.StringTokenizer
import java.io.BufferedReader
import java.io.InputStreamReader
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt()
    val maxUsing = IntArray(3){0}
    val minUsing = IntArray(3){0}
    val tmpUsing = IntArray(3){0}
    repeat(n){
        st = StringTokenizer(readLine())
        val a = st.getInt(); val b = st.getInt(); val c = st.getInt()
        tmpUsing[0] = kotlin.math.max(maxUsing[0],maxUsing[1])
        tmpUsing[2] = kotlin.math.max(maxUsing[1],maxUsing[2])
        tmpUsing[1] = kotlin.math.max(tmpUsing[0],tmpUsing[2]) + b
        tmpUsing[0]+=a
        tmpUsing[2]+=c
        for(i in 0..2) maxUsing[i] = tmpUsing[i]
        tmpUsing[0] = kotlin.math.min(minUsing[0],minUsing[1])
        tmpUsing[2] = kotlin.math.min(minUsing[1],minUsing[2])
        tmpUsing[1] = kotlin.math.min(tmpUsing[0],tmpUsing[2]) + b
        tmpUsing[0]+=a
        tmpUsing[2]+=c
        for(i in 0..2) minUsing[i] = tmpUsing[i]
    }
    val sb = StringBuilder("${maxUsing.maxOf { it }}")
    sb.append(" ${minUsing.minOf { it }}")
    print(sb)
}