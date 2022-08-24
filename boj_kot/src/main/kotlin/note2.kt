import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.sqrt

fun main(){
    // min 과 max 사이의 제곱수의 개수를 좀 똑똑하게 구함
    fun getTNumBetween(min:Int,max:Int):Int{
        fun eratosthenes(s:Int,e:Int):MutableList<Int>{
            val isPrime = BooleanArray(e + 1){true}
            val output = mutableListOf<Int>()
            val limit = sqrt(e.toDouble()).toInt()
            for (i in 2..limit){
                if(isPrime[i]){
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
        val squareNum = eratosthenes(1,limit).map{it*it}

        val numBetween = (max-min) + 1
        val isTarget= BooleanArray(numBetween){true}

        for(i in squareNum.indices){
            val s = squareNum[i]
            var dividendUnderMax = (max-max%s)
            var dividendUpperMin = if(min%s == 0)min else (min-min%s + s)
            // min 이상이고 max 이하인 s 의 배수가 없는 경우
            if(dividendUnderMax<dividendUpperMin) continue

            // min 이상의 s 의 배수가 min 으로부터 얼마나 떨어져 있는가
            var startOffset = (dividendUpperMin - min)

            var j = startOffset
            while(j in isTarget.indices){
                isTarget[j] = false
                // divisor 에 의한 오버플로우 주의
                j += s
            }
        }
        return (isTarget.count{it})
    }
    val k = BufferedReader(InputStreamReader(System.`in`)).readLine().toInt()
    var low:Int
    var high = 0
    var left = k
    var lastHigh:Int

    while(left>0){
        low = high + 1
        lastHigh = high
        high += (left*1.618).toInt()

        var tmp = getTNumBetween(low,high)
        if(left<tmp){
            low = lastHigh+1
            high = lastHigh+left
            tmp = getTNumBetween(low,high)
        }
        left -= tmp
    }
    if(getTNumBetween(1,high)!=k){
        print(k)
    }

    println(high)

}