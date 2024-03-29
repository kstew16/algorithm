import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.sqrt

fun main(){
    // min 과 max 사이의 s 의 배수 개수를 구함
    fun getMultiplesBetween(min:Long,max:Long,s:Long):Long{
        var dividendUnderMax = (max-max%s)
        var dividendUpperMin = if(min%s == 0L)min else (min-min%s + s)
        // min 이상이고 max 이하인 s 의 배수가 없는 경우
        return if(dividendUnderMax<dividendUpperMin) 0
        else (dividendUnderMax-dividendUpperMin)/s + 1
    }

    // min 과 max 사이의 제곱수의 개수를 좀 똑똑하게 구함
    fun getTNumBetween(min:Long,max:Long):Long{
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
        val squareNum = eratosthenes(1,limit).map{it.toLong()*it.toLong()}

        val numBetween = (max-min) + 1
        var count = 0L

        for(i in squareNum.indices){
            val s = squareNum[i]
            // 제곱수들의 배수만큽 뺴고 그 중 2개를 고른 경우의 공배수(소수제곱들이라 그냥 곱) 의 개수만큼 더하고,,, 짝수개 고르면 플러스 홀수개는 마이너스
            val visited = BooleanArray(squareNum.size){false}
            fun combination(depth:Int,visiting:Int,lcm:Long){

                visited[visiting] = true
                if(depth%2 == 1)count += getMultiplesBetween(min,max,lcm)
                else count -= getMultiplesBetween(min,max,lcm)


                for(i in visiting until squareNum.size){
                    //lcm 에 곱하는건 오버플로우 안 날때만
                    if(!visited[i]){
                        val lNewLCM = lcm*squareNum[i]
                        if(lNewLCM<=max) {
                            combination(depth+1,i,lNewLCM)
                        }
                    }
                }

                visited[visiting] = false
            }
            combination(1,i,s)
        }
        return numBetween - count
    }
    val (min,max) = BufferedReader(InputStreamReader(System.`in`)).readLine().split(" ").map{it.toLong()}
    print(getTNumBetween(min,max))
}