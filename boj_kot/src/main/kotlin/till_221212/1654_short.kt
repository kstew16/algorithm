import java.io.BufferedReader
import java.io.InputStreamReader
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,target) = readLine().split(" ").map { it.toInt() }
    val cables = IntArray(n){ readLine().toInt() }
    var lengthHi = cables.maxOf{it}
    var lengthLo = 1
    fun getLines(length:Int):Int{
        var nLine = 0
        cables.forEach { nLine+= it/length }
        return nLine
    }
    var lengthMid = 0
    while(true){
        lengthMid = ((lengthHi.toLong()+lengthLo.toLong())/2L).toInt()
        if(lengthMid == lengthHi || lengthMid == lengthLo) break
        var nLineMid = getLines(lengthMid)
        if( nLineMid < target) lengthHi = lengthMid
        else lengthLo = lengthMid
    }
    if(getLines(lengthHi)>=target) print(lengthHi)
    else print(lengthLo)
}