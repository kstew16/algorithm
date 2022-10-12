import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main( ) = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val mask = UByteArray(4){ UByte.MAX_VALUE}
    val add = UByteArray(4)
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    repeat(n){ addNum ->
        val address = readLine().split(".").map{it.toUByte()}
        if(addNum==0) for(i in 0 until 4) add[i] = address[i]
        else for(i in 0 until 4) add[i] = (add[i] and address[i])
        //for(i in 0 until 4) lastAddress[i] = address[i]
        //if(addNum!=0) for(i in 0 until 4) mask[i] = mask[i] and (lastAddress[i] xor add[i]).inv()
    }
    bw.write("${add.joinToString(".")}\n")
    var count0 = 0
    var count1 = 0
    for(i in 0 until 4){
        if(count0!=0) count0+=4
        else if(add[i]== UByte.MAX_VALUE) count1 +=4
        else for(j in 0 until 8){
            if(((1 shl j) and add[i].toInt())==1){
                count0 = j+1
                count1 += (7-j)
                break
            }
        }
    }

}