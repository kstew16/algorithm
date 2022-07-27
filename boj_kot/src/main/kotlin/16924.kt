import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.StringTokenizer

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    val st = StringTokenizer(br.readLine())
    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()
    val field = Array(n){BooleanArray(m){false}}
    val mutableField = Array(n){BooleanArray(m){false}}
    for(i in 0 until n){
        br.readLine().chunked(1).withIndex().forEach{
            input ->
            field[i][input.index] = input.value != "."
            mutableField[i][input.index] = input.value != "."
        }
    }
    field.forEach {
        println(it.joinToString(" "))
    }
    // field 에 갖다박을 수 있고, mutableField 에 영향을 줄 수 있으면 지름 작은 십자가부터 넣어서 넣을 수 있는 최대의 십자가를 넣기
    // mutableList 에다가 intArray 타입으로 넣은 다음에 각 배열을 joinToString \n 해서 출력. 단, ans 가 비면 -1
}