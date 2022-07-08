import java.io.BufferedReader
import java.io.InputStreamReader

fun main(){

    class SetManager {
        val set = mutableSetOf<Int>()
        fun add(x:Int){
            this.set.add(x)
        }

        fun remove(x:Int){
            this.set.remove(x)
        }

        fun check(x:Int)
        {
            if(x in this.set) println(1)
            else println(0)
        }

        fun toggle(x:Int){
            if(x in this.set) this.set.remove(x)
            else this.set.add(x)
        }

        fun all() = this.set.addAll(1..20)

        fun empty() = this.set.clear()
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