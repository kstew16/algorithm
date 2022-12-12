import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val case = readLine().toInt()
    val target = IntArray(case){
        readLine().toInt()
    }
    val ans = Array(case){""}

    data class Node(val remainder:Int, val sNum:StringBuilder)
    for(i in target.indices){
        if(ans[i]!="") continue
        val t = target[i]
        val remainderVisited = BooleanArray(t){false}
        val queue = LinkedList<Node>().apply { add(Node(1%t, StringBuilder("1"))) }
        while (queue.isNotEmpty()){
            val cur = queue.pollFirst()
            if(cur.remainder==0){
                remainderVisited[0] = true
                for(i in target.indices)if (target[i]==t) ans[i] = cur.sNum.toString()
                break
            }
            if(remainderVisited[cur.remainder]) continue
            remainderVisited[cur.remainder]=true
            queue.add(Node(remainder = (cur.remainder*10)%t, sNum = StringBuilder(cur.sNum).append("0")))
            queue.add(Node(remainder = (cur.remainder*10+1)%t, sNum = StringBuilder(cur.sNum).append("1")))
        }
        if(!remainderVisited[0]) ans[i] = "BRAK"
        queue.clear()
    }
    ans.forEach {
        println(it)
    }
}