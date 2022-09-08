import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    println("50 50")
    val arr = Array(50){y->
        CharArray(50){x->
            if(y==0&&x==0) 'S'
            else if(y==49&&x==0) 'C'
            else if(x==49&&y==0) 'C'
            else '.'
        }
    }
    arr.forEach {
        println(it.joinToString(""))
    }
}