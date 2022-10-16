import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val st = StringTokenizer(readLine())
    val h = st.nextToken().toInt()
    val w = st.nextToken().toInt()
    var count:Int = if(w>=h){ if(h%2==0) 4*(h/2-1)+2 else if(h==w) 2*(h-1) else if(w%2==0) 2*(h-1) else 2*(h-1) }
    else{
        if(w%2==0) 2*w-1
        else{ if(h%2==1) 2*(w-1)+1 else 2*(w-1)+1 }
    }
    print(count)
}