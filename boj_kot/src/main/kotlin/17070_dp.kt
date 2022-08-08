import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.contracts.contract
// Also 17069
private const val HORIZONTAL = 0
private const val VERTICAL = 1
private const val DIAGONAL = 2
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val field = Array(n){
        val st = StringTokenizer(readLine())
        IntArray(n){st.nextToken().toInt()}
    }

    val ways = Array(n){Array(n){LongArray(3){0}}}.apply{this[0][1][HORIZONTAL] = 1}
    (0 until n).forEach { j->
        (0 until n).forEach { i->
            if(field[j][i]==0){
                // Horizontal
                if(i-1 in 0 until n){
                    // 좌 Horizontal
                    ways[j][i][HORIZONTAL] += ways[j][i-1][HORIZONTAL]
                    // 좌 Diagonal
                    if(j-1 in 0 until n)
                        ways[j][i][HORIZONTAL] += ways[j][i-1][DIAGONAL]
                }
                // Vertical
                if(j-1 in 0 until n){
                    // 상 Vertical
                    ways[j][i][VERTICAL] += ways[j-1][i][VERTICAL]
                    // 상 Diagonal
                    if(i-1 in 0 until n)
                        ways[j][i][VERTICAL] += ways[j-1][i][DIAGONAL]
                }
                // Diagonal
                if(j-1 in 0 until n && i-1 in 0 until n){
                    // 사 Diagonal
                    if(field[j-1][i]+field[j][i-1]==0)
                        ways[j][i][DIAGONAL] += ways[j-1][i-1][DIAGONAL] + ways[j-1][i-1][VERTICAL] + ways[j-1][i-1][HORIZONTAL]
                }
            }
        }
    }

    print(ways[n-1][n-1].sum())
}