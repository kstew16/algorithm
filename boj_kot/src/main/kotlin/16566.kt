import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.Buffer
import java.util.PriorityQueue
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    var st = StringTokenizer(readLine())
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    val n = st.getInt(); val m = st.getInt(); val k  = st.getInt()
    //st = StringTokenizer(readLine())
    val cardList = ArrayList<Int>()
    //repeat(m){cardList.add(st.getInt())}
    repeat(m){cardList.add(3999999-it)}
    cardList.sort()
    //st = StringTokenizer(readLine())
    //val condition = IntArray(k){st.getInt()}
    val condition = IntArray(k){it}
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    for(i in 0 until k){
        // 타겟보다 크고 가장 작은 카드를 찾아야 함
        val target = condition[i]
        var lo = 0
        var hi = cardList.size-1
        var mid:Int
        while(lo+1<hi){
            mid = (lo+hi)/2
            if(target>=cardList[mid]) lo = mid else hi = mid
        }
        val pick = if(cardList[lo]>target) lo else hi
        bw.write("${cardList[pick]}\n")
        cardList.removeAt(pick)
    }
    bw.flush()
    bw.close()
    close()
}