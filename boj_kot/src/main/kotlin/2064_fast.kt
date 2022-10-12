import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main( ) = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    var mask = UInt.MAX_VALUE
    var changed = UInt.MAX_VALUE
    var add:UInt
    var lastAddress = UInt.MIN_VALUE
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    repeat(n){ addNum ->
        var address = UInt.MIN_VALUE
        readLine().split(".").map{it.toUInt()}.forEachIndexed { index, i ->
            address = (1 shl(24-8*index)).toUInt()*i + address
        }

        // 바뀌었던 적이 있는 지 없는지 추적
        if(addNum!=0) changed = changed and (lastAddress xor address).inv()
        lastAddress = address
    }

    if(n!=1 && changed!=UInt.MAX_VALUE) {
        mask = UInt.MIN_VALUE
        for (i in 31 downTo 0) {
            if (((1 shl i).toUInt() and changed) == UInt.MIN_VALUE) {
                for (j in 31 downTo i + 1) mask = mask or (1 shl j).toUInt()
                break
            }
        }
    }
    add = (mask and lastAddress)
    fun UInt.toIPAddress():String{
        return this.toString(2).chunked(8).map{it.toInt(2)}.joinToString(".")
    }
    bw.write(add.toIPAddress())
    bw.write("\n")
    bw.write(mask.toIPAddress())
    bw.flush()
    bw.close()
    close()
}