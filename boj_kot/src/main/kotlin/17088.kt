import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
// 아니 ㅋㅋㅋㅋㅋㅋ 제발 처음에 생각한 케이스좀 지켜 제발! 9개 나올 것 같으면 9개 다 쓰란말이야
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    var minCount = 100001 // 10^5, 수열의 모든 수를 변화시켜야 할 때
    val num = br.readLine().toInt()
    val st = StringTokenizer(br.readLine())
    val arr = IntArray(num){
        st.nextToken().toInt()
    }
    //if(arr.first()>arr.last()) arr.reverse()
    val differ = arr.last()-arr.first()
    // first 에 더하면 end 에 가까워지는 offset
    val offset = if(arr.last()<arr.first()) -1 else +1
    val interval = arr.size-1
    // 처음 수, 가능한 등차, 몇 번 바꾸고 시작하는지
    val possible = mutableSetOf<IntArray>()
    if(interval!=0){
        for(i in -2..2){
            if((differ+i)%(interval)==0){
                val predict = (differ+i)/(interval)
                when(if(differ>0)i else -i){
                    // 2와 1은 first 와 end 가 멀어져야 한다는 것을 의미
                    2 -> {possible.add(intArrayOf(arr.first()-offset,predict,1)) }
                    1 -> {
                        possible.add(intArrayOf(arr.first()-offset,predict,1))
                        possible.add(intArrayOf(arr.first(),predict,0))
                    }
                    0 -> {
                        possible.add(intArrayOf(arr.first()-offset,predict,1))
                        possible.add(intArrayOf(arr.first(),predict,0))
                        possible.add(intArrayOf(arr.first()+offset,predict,1))
                    }
                    // first 와 end 가 가까워져야 한다는 것을 의미
                    -1 ->{
                        possible.add(intArrayOf(arr.first()+offset,predict,1))
                        possible.add(intArrayOf(arr.first(),predict,0))
                    }
                    -2 ->{possible.add(intArrayOf(arr.first()+offset,predict,1)) }
                }
            }
        }
    } else minCount = 0

    possible.forEach{
        var (current,diff,count) = it
        for(i in 1 until num){
            current+=diff
            if(arr[i]!=current){
                if(kotlin.math.abs(arr[i] - current) >=2)
                {
                    count += 110000
                    break
                }
                else count+=1
            }
        }
        minCount = count.coerceAtMost(minCount)
    }
    if(minCount>=100001)println(-1)
    else println(minCount)

}