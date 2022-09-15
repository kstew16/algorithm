import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,m) = readLine().split(" ").map{it.toInt()}
    var target = IntArray(2){0}
    var red = IntArray(2){0}
    var blue = IntArray(2){0}
    data class State(val ry:Int,val rx:Int,val by:Int,val bx:Int,val distance:Int)
    val possible = Array(n){y->
        val input = readLine().toCharArray()
        BooleanArray(m){x->
            val field = input[x]
            if(field=='#') false
            else{
                when(field){
                    'R' -> red = intArrayOf(y,x)
                    'B' -> blue = intArrayOf(y,x)
                    'O' -> target = intArrayOf(y,x)
                }
                true
            }
        }
    }
    val queue = LinkedList<State>().apply{add(State(red[0],red[1],blue[0],blue[1],0))}
    val dx = intArrayOf(1,0,-1,0)
    val dy = intArrayOf(0,1,0,-1)
    val rVisited = Array(n){BooleanArray(m){false} }
    val bVisited = Array(n){BooleanArray(m){false} }
    fun applyGravity(direction:Int){

    }
    var ans = -1
    while(queue.isNotEmpty()){

    }
}