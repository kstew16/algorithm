import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.sqrt

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
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
    val n = readLine().toInt()
    val primeArr = eratosthenes(1,n).toIntArray()
    var localSum = 0L
    val primeAccSum = LongArray(primeArr.size){
        localSum += primeArr[it]
        localSum
    }
    var count = 0
    val limit = primeArr.size
    for(i in 0 until limit){
        if(primeAccSum[i] == n.toLong()) count+=1
        for(j in i+1 until limit){
            val primeSumFromItoJ = primeAccSum[j]-primeAccSum[i]
            if(primeSumFromItoJ>n) break
            else if(primeSumFromItoJ== n.toLong()) count+=1
        }
    }
    print(count)

}


