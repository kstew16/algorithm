// 틀린 이유 -> println 오버헤드가 bw 방식보다 적어서

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main(){

    var set = 0

    fun add(x:Int) = set or (1 shl x-1)
    fun remove(x:Int) = set and (1 shl x-1).inv()
    fun check(x:Int) = if(set or (1 shl x-1)>set) 0 else 1
    fun toggle(x:Int) = set xor (1 shl x-1)
    fun all() = set or 0.inv()
    fun empty() = 0

    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    val n = br.readLine().toInt()
    var input: MutableList<String>

    for( i in 0 until n){
        input = br.readLine().split(" ").toMutableList()
        when(input[0]){
            "add" -> set = add(input[1].toInt())
            "remove" -> set = remove(input[1].toInt())
            "check" -> bw.write((check(input[1].toInt())).toString()+"\n")
            "toggle" -> set = toggle(input[1].toInt())
            "all" -> set = all()
            "empty" -> set = empty()
        }
    }
    br.close()
    bw.flush()
    bw.close()
}