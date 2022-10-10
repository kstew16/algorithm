import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val st = StringTokenizer(readLine())
    val h = st.nextToken().toInt()
    val w = st.nextToken().toInt()
    var count:Int

    if(w>=h){
        if(h%2==0){
            // 짝짝정, 짝짝가로, 짝홀가로
            count = 4*(h/2-1)+2
        }else if(h==w){
            // 홀홀정
            count = 2*(h-1)
        }else if(w%2==0){
            // 홀짝가로
            count = 2*(h-1)
        }else{
            //홀홀가로
            count = 2*(h-1)
        }
    }
    else{
        // h<w
        if(w%2==0){
            if(h%2==0){
                count = 2*w - 1
            }else{
                //홀짝세로
                count = 2*h/2-1
            }
        }else{
            if(h%2==1) {
                //홀홀세로
                count = 2*(w-1)+1
            }else{
                //짝홀세로
                count = 2*(w-1)+1
            }
        }

    }
    print(count)
}