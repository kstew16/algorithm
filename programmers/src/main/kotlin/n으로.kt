import java.util.LinkedList

fun main(){
    class Solution {
        fun solution(n: Int, target: Int): Int {
            var ans = 1
            // dp[i] n을 i 번 써서 만들 수 있는 숫자들
            val dp = Array(9){ArrayList<Int>()}
            val visited = hashMapOf<Int,Int>()
            // dp 를 중첩해가며 새로운 dp를 생성하되, dp[i] 의 i 는 8 이하


            // 먼저 나열하는 걸 추가해줌
            val sb = StringBuilder("")
            repeat(8){
                sb.append(n.toString())
                sb.toString().toInt().run{
                    if(it+1<visited.getOrDefault(this,Int.MAX_VALUE)){
                        visited[this] = it+1
                        dp[it+1].add(this)
                    }
                }
            }

            while(ans<9){
                // dp 중첩과정
                for(i in 1..kotlin.math.min(ans,8-ans)){
                    // ans 번째까지 완성이 되었음. ans 이하의 애들하고 ans+i 애들하고 중첩
                    dp[ans].forEach {dpj->
                        dp[i].forEach {dpi->
                            intArrayOf(
                                dpi+dpj,
                                dpi-dpj,
                                dpj-dpi,
                                dpi*dpj,
                                if(dpi%dpj==0) dpi/dpj else n,
                                if(dpj%dpi==0) dpj/dpi else n
                            ).forEach{
                                if(it==target){println("$dpi & $dpj")}
                                if(it>0 && visited.getOrDefault(it,Int.MAX_VALUE)>i+ans){
                                    visited[it] = i+ans
                                    dp[i+ans].add(it)
                                }
                            }
                        }

                    }
                }
                // 답 확인 과정
                visited.getOrDefault(target,Int.MAX_VALUE).run {
                    if(this!=Int.MAX_VALUE) return this
                    else ans++
                }
            }
            return -1
        }
    }
    println(Solution().solution(8,99))
}