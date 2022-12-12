import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.sqrt
/*
1016 푼 다음날에 풀었고, 4시간 좀 넘게 걸렸음, 근데 왜 이거 다이아야? 나 기분 너무 좋음 ㅋㅋㅋㅋㅋㅋㅋ
어제는 min 과 max 사이의 s의 배수를 구하기 위해 s를 min 바로 위의 s의 배수부터 s씩 더해가며 1씩 카운트했는데
(min 바로위의 s의 배수 dividendUpperMin 과 Max 바로 아래의 s의 배수 dividendUnderMax)
오늘은 두 개를 빼서 s로 나누는 등차수열의 개수 정리를 사용했음. 대신에 공배수끼리 겹치기 떄문에
U -A -B -C -D +AB +AC +AD +BC +BD +CD -ABC -ABC -ACD - CD +ABCD 이런 집합의 원소 수 정리를 사용해서 개수를 구했는데
그 방법은 Combination 을 사용해서 집합들의 원소 중 1개부터 N 개까지를 각각 골라서 교집합을 구하는 것
소수의 제곱수들이라 그들의 교집합, 즉 최소공배수는 그냥 둘의 곱임 하여튼 이 방법으로 min 과 max 사이의 제곱ㄴㄴ수의 개수를 구했음
여기까지가 getTNumBetween. 근데 이거 1016 시간초과 떠. 왠지 한 번 봐줘

내가 원하는게 k 번째 제곱 ㄴㄴ수였는데, k 까지의 수에는 대략적으로 제곱 ㄴㄴ수가 0.6배로 유지되는 규칙이 있더라고?
해서 k 까지의 제곱ㄴㄴ수와 k 와의 관계가 1.618에 수렴하는 규칙까지 찾아냈어.
이후에는 헤라클레스의 달리기처럼 1부터 k*1.618 해 보고, high+1 을 low 로 하고 k 까지 남은 수에다가 1.618 더해서 다시 high 로 설정하고
이걸 반복해서 구하니까 틀렸었음. 틀린 이유는 high 를 출력하는데 high 가 제곱 ㄴㄴ수가 아니었기 때문임
마지막으로 high 가 제곱 ㄴㄴ수가 될 때 까지 낮춰서 제출, 맞았음. 개선의 여지 충분히 있어 보이니 조만간 피드백 받자
 */
const val GOLDEN_RATIO = 1.618
fun main(){
    // min 과 max 사이의 s 의 배수 개수를 구함
    fun getMultiplesBetween(min:Int,max:Int,s:Int):Int{
        var dividendUnderMax = (max-max%s)
        var dividendUpperMin = if(min%s == 0)min else (min-min%s + s)
        // min 이상이고 max 이하인 s 의 배수가 없는 경우
        return if(dividendUnderMax<dividendUpperMin) 0
        else (dividendUnderMax-dividendUpperMin)/s + 1
    }

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
        var count = 0

        for(i in squareNum.indices){
            val s = squareNum[i]
            // 제곱수들의 배수만큽 뺴고 그 중 2개를 고른 경우의 공배수(소수제곱들이라 그냥 곱) 의 개수만큼 더하고,,, 짝수개 고르면 플러스 홀수개는 마이너스
            val visited = BooleanArray(squareNum.size){false}
            fun combination(depth:Int,visiting:Int,lcm:Int){

                visited[visiting] = true
                if(depth%2 == 1)count += getMultiplesBetween(min,max,lcm)
                else count -= getMultiplesBetween(min,max,lcm)

                for(i in visiting until squareNum.size){
                    //lcm 에 곱하는건 오버플로우 안 날때만
                    if(!visited[i]){
                        val lNewLCM = lcm.toLong()*squareNum[i].toLong()
                        if(lNewLCM<=max.toLong()) {
                            val iNewLCM =  lNewLCM.toInt()
                            combination(depth+1,i,iNewLCM)
                        }
                    }
                }

                visited[visiting] = false
            }
            combination(1,i,s)
        }
        return (numBetween-count)
    }
    val k = BufferedReader(InputStreamReader(System.`in`)).readLine().toInt()
    var low = 0
    var high = 0
    var left = k
    var lastHigh:Int

    while(left>0){
        low = high + 1
        lastHigh = high
        high += (left*GOLDEN_RATIO).toInt()

        var tmp = getTNumBetween(low,high)
        if(left<tmp){
            low = lastHigh+1
            high = lastHigh+left
            tmp = getTNumBetween(low,high)
        }
        left -= tmp
    }

    // 타겟이 될 때 까지 상한선 낮추기
    while(getTNumBetween(high,high)==0){
        high -=1
    }

    println(high)

}