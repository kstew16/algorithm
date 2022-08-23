import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.sqrt
// 제틴 진우한테 에라토스테네스라는 힌트를 먼저 받았기도 하고, 시간도 오래걸렸음
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
        val s = squareNum[i]
        var dividendUnderMax = (max-max%s)
        var dividendUpperMin = if(min%s == 0L)min else (min-min%s + s)
        // min 이상이고 max 이하인 s 의 배수가 없는 경우
        if(dividendUnderMax<dividendUpperMin) continue

        // min 이상의 s 의 배수가 min 으로부터 얼마나 떨어져 있는가
        var startOffset = (dividendUpperMin - min).toInt()

        var j = startOffset
        while(j in isTarget.indices){
            isTarget[j] = false
            // divisor 에 의한 오버플로우 주의
            j += s.toInt()
        }
    }
    print(isTarget.count{it})
}