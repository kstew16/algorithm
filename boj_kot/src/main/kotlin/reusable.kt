import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.sqrt

fun main(){
    infix fun Int.on(i:Int) = this or (1 shl i)
    infix fun Int.off(i:Int) = this and (1 shl i).inv()
    infix fun Int.chk(i:Int) = (this shr i) and 1 == 1
    //infix fun Int.chk(i:Int) = this and (1 shl i)>=1
    
    // s 부터 e 까지의 1을 제외한 소수
    fun eratosthenes(s:Int,e:Int):MutableList<Int>{
        // 에라토스테네스의 체를 이용해 s부터 e 사이의 합성수들을 false 처리
        val isPrime = BooleanArray(e + 1){true}
        val output = mutableListOf<Int>()

        // e 의 제곱근 이하의
        val limit = sqrt(e.toDouble()).toInt()
        for (i in 2..limit){
            // 1을 제외한 소수에 대해서
            if(isPrime[i]){
                // 소수의 배수들을 체에서 제거
                var j = 2
                while (i*j <= e) {
                    if (isPrime[i * j]) isPrime[i * j] = false
                    j++
                }
            }
        }

        for (i in s..e){
            if (i == 0 || i == 1) continue
            if(isPrime[i]) output.add(i)
        }
        return output
    }

    val n = BufferedReader(InputStreamReader(System.`in`)).readLine().toInt()
    val parent = IntArray(n){it}
    fun find(x:Int):Int{
        if(parent[x]==x) return x
        parent[x] = find(parent[x])
        return  parent[x]
    }

    fun union(x:Int,y:Int){
        val px = find(x)
        val py = find(y)
        if(px==py) return
        else if(py<px) parent[px] = py
        else parent[py] = px
    }
    // 마지막에 find(i) 다 해줘야 뿌리들 다시 찾아감

    fun matrixMultiply(a:Array<IntArray>,b:Array<IntArray>):Array<IntArray>{
        val y1 = a.size
        val x1y2 = b.size
        val x2 = b[0].size
        val ans = Array(y1){y->
            IntArray(x2){x->
                var sum = 0
                for(i in 0 until x1y2){
                    sum = sum + b[i][x]*a[y][i]
                }
                sum
            }
        }
        return ans
    }
}