import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main( ) = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val mask = UByteArray(4){ UByte.MAX_VALUE}
    val add = UByteArray(4)
    val lastAddress = UByteArray(4)
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    repeat(n){ addNum ->
        val address = readLine().split(".").map{it.toUByte()}
        // 바뀌었던 적이 있는 지 없는지 추적
        if(addNum!=0) for(i in 0 until 4) mask[i] = mask[i] and (lastAddress[i] xor address[i]).inv()
        for(i in 0 until 4) lastAddress[i] = address[i]
    }

    if(n!=1 && !mask.all { it==UByte.MAX_VALUE }) {
        var changedInt = 0
        for (i in 0 until 4) changedInt += (mask[3 - i].toInt()) * (1 shl 8 * i)

        var maskInt = 0
        for (i in 31 downTo 0) {
            if (((1 shl i) and changedInt) == 0) {
                for (j in 31 downTo i + 1) maskInt = maskInt or (1 shl j)
                break
            }
        }
        for (i in 0 until 4) mask[i] = (maskInt ushr (3 - i) * 8).toUByte()
    }
    for(i in 0 until 4) add[i] = mask[i] and lastAddress[i]
    bw.write("${add.joinToString(".")}\n")
    bw.write(mask.joinToString("."))
    bw.flush()
    bw.close()
    close()
}