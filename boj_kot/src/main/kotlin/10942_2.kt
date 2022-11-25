import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.LinkedList
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    var st = StringTokenizer(readLine())
    fun getInt():Int = st.nextToken().toInt()
    val arr = IntArray(n){getInt()}
    var maxCase = 0
    val t = readLine().toInt()
    val tc = Array(t){
        st = StringTokenizer(readLine())
        val a = getInt()-1
        val b = getInt()-1
        maxCase = b.coerceAtLeast(maxCase)
        Pair(a,b)
    }
    // dp[i][j] i 이상 j 이하의 문자열은 팰린드롬인가
    val dp = Array(maxCase+1){i->
        BooleanArray(maxCase+1){j->
            i==j
        }
    }


    for(from in maxCase downTo 0){
        for(to in from+1 until maxCase+1){
            // 일단 서로가 같아야 팰린드롬 형성 시도라도 해볼 수 있음
            if(arr[to] == arr[from]){
                // 사이에 아무 것도 없거나 하나만 있는 경우 팰린드롬
                //  뭐가 있는데 걔들도 팰린드롬인경우 팰린드롬
                dp[from][to] = ((to-1)-(from+1)<=0)||(dp[from+1][to-1])
            }
        }
    }

    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    tc.forEach {
        bw.write(
            "${if(dp[it.first][it.second]) 1 else 0}\n"
        )
    }
    bw.flush()
    bw.close()
    close()
}