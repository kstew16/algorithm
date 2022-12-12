import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Integer.max
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val st=StringTokenizer(readLine())
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    fun getInt():Int{return st.nextToken().toInt()}
    val yang = getInt()
    val hu = getInt()
    val ban  = getInt()
    val targetY = getInt()*2 // 한 마리씩 주문하는 반마리씩 나눠서 생각
    val targetH = getInt()*2
    var minPrice = Int.MAX_VALUE

    var b = 0
    while(b<=max(targetH,targetY)){
        val hLeft = targetH-b
        val yLeft = targetY-b
        val price = ban*b + hu*(if(hLeft>0)(hLeft/2)else 0) + yang*(if(yLeft>0)(yLeft/2)else 0)
        minPrice = price.coerceAtMost(minPrice)
        b+=2
    }


    bw.write("${minPrice}")
    bw.flush()
    bw.close()
    close()

}