import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.PriorityQueue
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt(); val k = st.getInt()
    data class Jewel(val mass:Int,val value:Int)
    val jewels = Array(n){
        st = StringTokenizer(readLine())
        Jewel(st.getInt(),st.getInt())
    }
    jewels.sortBy { it.mass }
    var ans = 0L
    val bags = IntArray(k){StringTokenizer(readLine()).getInt()}.sorted()

    val pq = PriorityQueue<Int>{a,b -> b-a}

    var jewelIndex = 0
    for(i in 0 until k){
        val currentSmallestBag = bags[i]
        while(jewelIndex<n && jewels[jewelIndex].mass<=currentSmallestBag){
            pq.add(jewels[jewelIndex++].value)
        }
        if(pq.isNotEmpty()) ans += pq.poll().toLong()
    }
    print(ans)
}