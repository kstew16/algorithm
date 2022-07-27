import java.io.BufferedWriter
import java.io.OutputStreamWriter
import kotlin.math.pow

fun main(){
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    var arr = mutableListOf<Long>()
    for(i in 0..39){
        arr.add(4*3.toDouble().pow(i.toDouble()).toLong())
        arr.add(2*3.toDouble().pow(i.toDouble()).toLong())
        arr.add(3.toDouble().pow(i.toDouble()).toLong())
    }
    bw.write("${arr.size}\n")
    bw.write(arr.joinToString(" "))
    bw.flush()
    bw.close()
}