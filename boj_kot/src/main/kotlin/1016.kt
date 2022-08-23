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

    // 두 수의 사이들이 ㄴㄴ제곱수인지 표시할 배열을 만들고
    val numBetween = (max-min).toInt() + 1
    val isTarget= BooleanArray(numBetween){true}

    // 제곱수 s 에 대해, min 이상의 s 의 배수부터 max 이하의 s 의 배수를 제곱수에서 체크 해제
    for(i in squareNum.indices){
        val divisor = squareNum[i]
        var dividendMaxLong = ((max-max%divisor) - min)
        var dividendMinLong = if(divisor<min)(divisor-min%divisor)else(min-min%divisor)
        if(dividendMaxLong<0  || dividendMinLong<0) {
            continue
        }
        var dividendMax = dividendMaxLong.toInt()
        var dividendMin = dividendMinLong.toInt()

        var j = dividendMax
        while(j>=dividendMin){

            isTarget[j] = false
            if(divisor>j.toLong()) break
            j -= divisor.toInt()
        }
    }
    print(isTarget.count{it})
}