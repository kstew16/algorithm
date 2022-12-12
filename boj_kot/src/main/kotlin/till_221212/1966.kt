import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val nTest = readLine().toInt()
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    repeat(nTest){
        val (n,k) = readLine().split(" ").map{it.toInt()}
        val st = StringTokenizer(readLine())
        val queue = LinkedList<IntArray>()
        val priorityArr = IntArray(n){0}
        var count = 0
        var bottom = 0
        for(i in 0 until n){
            val input = st.nextToken().toInt()
            queue.add(intArrayOf(i,input))
            priorityArr[i] = input
        }
        priorityArr.sortDescending()
        var found = -1
        while(found<0){
            val (index,num) = queue.pollFirst()
            if(num>=priorityArr[bottom]){
                if(index==k) found = (bottom+1)
                bottom++
            }else{
                queue.add(intArrayOf(index,num))
            }
            count += 1
        }
        bw.write("$found\n")
    }
    bw.flush()
    bw.close()
    close()
}