import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val eggs = Array(n){
        val st = StringTokenizer(readLine())
        intArrayOf(st.nextToken().toInt(),st.nextToken().toInt())
    }

    var maxBroken = 0
    var brokenEgg = 0
    // depth 는 0부터 시작하는 좌측으로부터의 계란의 위치\
    fun hitDFS(depth:Int){
        if(depth == n){
            // 가장 최근에 든 계란이 가장 오른쪽 계란일 경우 과정을 종료한다
            var temp = maxBroken
            maxBroken = brokenEgg.coerceAtLeast(maxBroken)
        }else{
            if(eggs[depth][0]<=0){
                // 계란이 깨져 있는 경우 다음 계란을 봄
                hitDFS(depth+1)
            }else{

                var isHit = false
                var (hittingHP,hittingPower) = eggs[depth]
                for(i in 0 until n){
                    if(i!= depth){
                        val (targetHP,targetPower) = eggs[i]
                        if(targetHP>0){
                            isHit = true
                            //stackForTest[depth] = i
                            eggs[i][0] = targetHP - hittingPower
                            eggs[depth][0] = hittingHP - targetPower
                            if(targetHP<=hittingPower) brokenEgg+=1
                            if(hittingHP<=targetPower) brokenEgg+=1
                            hitDFS(depth+1)
                            //stackForTest[depth] = -1
                            eggs[i][0] = targetHP
                            eggs[depth][0] = hittingHP
                            if(targetHP<=hittingPower) brokenEgg-=1
                            if(hittingHP<=targetPower) brokenEgg-=1
                        }
                    }
                }
                // 모든 계란이 깨져 있는 경우 다음으로 넘어간다
                if(!isHit) hitDFS(depth+1)
            }
        }
    }
    hitDFS(0)
    print(maxBroken)

}