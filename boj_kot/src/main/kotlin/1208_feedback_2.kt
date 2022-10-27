import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
// 이 개선으로 알 수 있는 것 : 문제를 쪼개고 답들을 전달해나가는 것도 오버헤드임을 명심하자.
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val offset = 4000000
    var st = StringTokenizer(readLine())
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    val n = st.getInt(); val target = st.getInt()
    st = StringTokenizer(readLine())
    val arr = IntArray(n){st.getInt()}

    var mid = n/2
    // feedback 2 이 정도 수준에선 해쉬가 비싸고 크다. 8,000,000 개면 64 MB 임. 쓸 수 있으면 써 해쉬는 90메가 띄우더라
    val visited = LongArray(8000001){0}

    // 좌측 절반 그룹의 합 방문 테이블 생성
    fun getPartSums(f:Int,t:Int,currentSum:Int){
        val cur = arr[f]
        visited[currentSum+cur+offset] += 1L
        if(f+1<=t){
            getPartSums(f+1,t,currentSum+cur)
            getPartSums(f+1,t,currentSum)
        }
    }
    getPartSums(0,mid,0)

    // feedback 2 원래 풀이에서 굳이 문제를 더 쪼개지 않아도 되는 곳이었음
    fun matchWithTarget(f:Int, lim:Int, currentSum:Int):Long{
        // 좌측 그룹과 매칭 + 우측 그룹만 사용
        return if(f==lim) visited[target-currentSum+offset] + (if(target==currentSum) 1 else 0)
        else matchWithTarget(f+1,lim,currentSum+arr[f]) + matchWithTarget(f+1,lim,currentSum)
    }

    // 한 개도 안 사용하고서 타겟을 맞추는 건 타겟이 0일때 뿐임, 해당 조건을 추가하면 오버헤드가 커지므로 마지막에 처리
    print(if(target==0)matchWithTarget(mid+1,n,0)-1 else matchWithTarget(mid+1,n,0))
}