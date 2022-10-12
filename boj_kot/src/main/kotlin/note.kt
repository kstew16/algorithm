import java.io.BufferedReader
import java.io.InputStreamReader

fun main( ) = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val mask = UByteArray(4){UByte.MAX_VALUE}
    val add = UByteArray(4){UByte.MAX_VALUE}
    repeat(n){ addNum ->
        val address = readLine().split(".").map{it.toUByte()}
        if(addNum==1) for(i in 0 until 4) mask[i] = address[i]
        for(i in 0 until 4){
            mask[i] = (mask[i] xor address[i]).inv()
        }
        if(addNum == n-1) {
            for(i in 0 until 4){add[i] = (mask[i] and address[i])}
            println(add.joinToString("."))
        }
    }

    print(mask.joinToString("."))
}