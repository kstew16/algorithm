import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    var st= StringTokenizer(readLine())
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    val n = st.getInt(); val t = st.getInt()
    var s = 0
    st = StringTokenizer(readLine())
    val accArr = IntArray(n+1){
        if(it==0) 0
        else{
            s+=st.getInt()
            s
        }
    }
    val sb = StringBuilder()
    repeat(t){
        st = StringTokenizer(readLine())
        sb.append("${-accArr[st.getInt()-1]+accArr[st.getInt()]}\n")
    }
    print(sb)
}