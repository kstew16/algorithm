import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.StringTokenizer

private fun main(){
    println(100000)
    for(i in 0 until 100000){
        //val offset = if(i%2==1) 0 else {if((i/2)%2==0)1 else -1}
        val offset = if(i<100)1 else 0
        print("${i*5+offset} ")
    }
}