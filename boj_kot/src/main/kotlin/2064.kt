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
    }
    bw.write("${add.joinToString(".")}\n")
    var addInt = 0
    for(i in 0 until 4) addInt += (add[3-i].toInt())*(1 shl 8*i)
    var maskInt = 0
    var found = false
    for(i in 0 until 32){
        if((1 and (addInt shr i)) == 1 || found){
            found = true
            maskInt = maskInt or (1 shl i)
        }
    }
    for(i in 0 until 4) mask[i] = (maskInt shr (3-i)*8).toUByte()
    bw.write(mask.joinToString("."))
    bw.flush()
    bw.close()
    /*
    3
127.40.64.0
127.40.65.124
127.40.127.33
     */
    close()
}