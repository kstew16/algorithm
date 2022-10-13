import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val address = Array(8){""}
    val shortAddress = readLine()
    if(shortAddress.contains("::")){
        val (add1Str,add2Str) = shortAddress.split("::")
        var size1=0
        var size2=0
        with(add1Str.split(":")){
            if(add1Str!=""){
                size1 = this.size
                this.forEachIndexed { index, s ->
                    address[index] = s }
            }
        }
        with(add2Str.split(":")){
            if(add2Str!=""){
                size2 = this.size
                this.forEachIndexed { index, s ->
                    address[8-size2+index] = s }
            }
        }
        val droppedLength = 8-(size1+size2)
        for(i in 0 until droppedLength)address[size1+i] = "0000"
    }else shortAddress.split(":").forEachIndexed { index, s -> address[index]=s }
    for(i in 0 until 8){
        val len = address[i].length
        if(len<4){
            var sb = StringBuilder("")
            for(j in 0 until 4-len) sb.append("0")
            sb.append(address[i])
            address[i] = sb.toString()
        }
    }
    println(address.joinToString(":"))
}