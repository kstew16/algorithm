import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.sqrt

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (min, max) = readLine().split(" ").map{it.toLong()}
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
    val limit = sqrt(max.toDouble()).toInt()
    val squareNum = eratosthenes(1,limit).map{it.toLong()*it.toLong()}

    // prime 의 제곱으로 나눠 떨어지는 수들의 개수를 사이의 숫자에서 빼 줌
    val visited = BooleanArray(squareNum.size){false}
    val numBetween = (max-min).toInt() + 1
    val isTarget= BooleanArray(numBetween){true}
    for(i in numBetween-1 downTo 0){
        if(!isTarget[i]) continue
        // 최대 10^6 * 8* 10^4 800억...?
        val checking = min+i
        for(j in squareNum.indices){
            if(visited[j]) continue
            val remainder = (checking%squareNum[j]).toInt()
            // 아직도 쓸모없는 검사가 있음, 두 개 이상의 제곱수를 약수로 가지면 여러 번 검사함
            if(remainder==0){
                isTarget[i] = false
            }else{
                // 아 이것도 이미 false 인 거를 여러번 찾아내네
                if(visited[j]) continue
                var k = i
                var divisor = squareNum[j].toInt()
                while(k-remainder in 1 until isTarget.size){
                    visited[j] = true
                    isTarget[k-remainder] = false
                    k-= divisor
                }
            }
        }
    }

    print(isTarget.count { it })
}