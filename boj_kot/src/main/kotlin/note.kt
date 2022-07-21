import java.io.BufferedWriter
import java.io.OutputStreamWriter

fun main(){
    val lim = 1000
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write("$lim ${lim+1}\n")
    for(i in 0 until lim){
        if(i%2==0) bw.write("0".repeat(lim)+"\n")
        else{
            if(((i-1)/2)%2==0) bw.write("1".repeat(lim-1)+"0\n")
            else bw.write("0"+"1".repeat(lim-1)+"\n")
        }
    }
    bw.write("0".repeat(lim)+"\n")
    bw.flush()
    bw.close()
}