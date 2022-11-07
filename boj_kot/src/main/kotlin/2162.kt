import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() : Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    data class Point(val x:Int, val y:Int)
    class Line(val s:Point,val e:Point){
        fun isConnected(l1:Line,l2:Line):Boolean{

        }
    }

}