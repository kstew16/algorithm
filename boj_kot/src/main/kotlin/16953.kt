import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (start,target) = br.readLine().split(" ").map { it.toInt() }
    val queue = LinkedList<IntArray>().apply { add(intArrayOf(target,1)) }
    var found = -1
    while(queue.isNotEmpty()){
        val (current,depth) = queue.pollFirst()
        if(current==start) {
            found = depth
            break
        }
        else{
            if(current%2==0) queue.add(intArrayOf(current/2,depth+1))
            if(current%10==1 &&current>9) queue.add(intArrayOf(current/10,depth+1))
        }
    }
    print(found)
}