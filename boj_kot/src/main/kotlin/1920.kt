import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*
// hashSet보다 mutableSet 이 빠르고 절약되더라

fun main()= with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val set = mutableSetOf<Int>().apply {
        val st = StringTokenizer(readLine())
        repeat(n){
            add(st.nextToken().toInt())
        }
    }
    val m = readLine().toInt()
    val st = StringTokenizer(readLine())
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    repeat(m){
        val input = st.nextToken().toInt()
        if(set.contains(input)) bw.write("1\n") else bw.write("0\n")
    }
    bw.flush()
    bw.close()
    close()

}