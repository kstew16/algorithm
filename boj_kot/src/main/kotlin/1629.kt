import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    var st = StringTokenizer(readLine())
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    val n = st.getInt()
    val t = st.getInt()
    val table = Array(n){
        st = StringTokenizer(readLine())
        IntArray(n){
            st.getInt()
        }
    }
    val accHorizontal = Array(n){
        IntArray(n){

        }
    }


    val command = Array(t){
        st = StringTokenizer(readLine())
        IntArray(4){st.getInt()}
    }


}