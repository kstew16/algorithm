import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList

fun main() = BufferedReader(InputStreamReader(System.`in`)).run{
    val limit = 100001
    val (s,t) = readLine().split(" ").map{it.toInt()}
    val started = Array(limit){
        BooleanArray(3){false}
    }
    var distance = Int.MAX_VALUE
    var count = 0
    val queue = LinkedList<IntArray>().apply{ add(intArrayOf(s,0)) }
    while(queue.isNotEmpty()){
        val (pos,curDist) = queue.pollFirst()
        if(curDist>distance) continue
        if(pos == t){
            if(curDist < distance){
                distance = curDist
                count = 1
            }else if(curDist == distance){
                count += 1
            }
            continue
        }
        fun visit(source:Int, target:Int,dist:Int,type:Int){
            if(target in 0 until limit && !started[source][type]){
                started[source][type] = true
                queue.add(intArrayOf(target,dist+1))
            }
        }
        visit(pos,pos*2,curDist,0)
        visit(pos,pos-1,curDist,1)
        visit(pos,pos+1,curDist,2)
    }
    println(distance)
    println(count)
}