import java.io.BufferedReader
import java.io.InputStreamReader

fun main(){

    class SetManager {
        var set = 0L
        fun add(x:Int){
            this.set = (set or (1L shl (x-1)))
        }

        fun remove(x:Int){
            this.set = (set and (1L shl (x-1)).inv())
        }

        fun check(x:Int)
        {
            if((this.set or (1L shl (x-1)))>this.set) println(0)
            else println(1)
        }

        fun toggle(x:Int){
            this.set = (set xor (1L shl (x-1)))
        }

        fun all() {
            this.set = (this.set or 0L.inv())
        }

        fun empty(){
            this.set = 0L
        }
    }

    val setManager = SetManager()
    val br = BufferedReader(InputStreamReader(System.`in`))
    val n = br.readLine().toInt()
    var input: MutableList<String>
    for( i in 0 until n){
        input = br.readLine().split(" ").toMutableList()
        when(input[0]){
            "add" -> setManager.add(input[1].toInt())
            "remove" ->setManager.remove(input[1].toInt())
            "check" -> setManager.check(input[1].toInt())
            "toggle" -> setManager.toggle(input[1].toInt())
            "all" -> setManager.all()
            "empty" -> setManager.empty()
        }
    }
    br.close()
}