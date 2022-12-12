import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() : Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt(); val conditions = st.getInt()
    val conditionCount = IntArray(n+1)
    val accept = Array(n+1){ mutableListOf<Int>() }
    repeat(conditions){
        st = StringTokenizer(readLine())
        val a = st.getInt(); val b = st.getInt()
        accept[a].add(b)
        conditionCount[b] +=1
    }
    val pq = PriorityQueue<Int>().apply{for(i in 1..n) if(conditionCount[i]==0) this.add(i)}
    val sb = java.lang.StringBuilder("")
    while(pq.isNotEmpty()){
        val cur = pq.poll()
        sb.append(" $cur")
        accept[cur].forEach {
            if(--conditionCount[it]==0) pq.add(it)
        }
    }
    print(sb.toString().trim())
}