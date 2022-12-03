import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
// 전주할머니집에서 누워서 2시간 걸렸으면 준수하지 않냐
// upper bound lower bound 이거 좀 더 봐도 좋을 듯 공항문제 이어서 두 번째네
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    // 코드에 n 이 필요 없어서 n 은 버림
    st.getInt(); val m = st.getInt(); val k  = st.getInt()
    st = StringTokenizer(readLine())

    // 쓸 수 있는 카드들을 이분탐색하기 위해서 정렬
    val cardList = IntArray(m){st.getInt()}.sortedArray()
    val used = BooleanArray(m)
    val usedTill = IntArray(m){it}


    fun findSectionEnd(visiting:Int):Int{
        val mySection = usedTill[visiting]
        return if(mySection+1<m && used[mySection+1]){
            // 연결된 부분이 있으면 거기 머리 데려오기
            val point = if(mySection!=usedTill[mySection+1]) findSectionEnd(usedTill[mySection+1])
            else mySection
            usedTill[visiting] = point
            point
        }
        // 연결된 거 없으면 내가 머리
        else mySection
    }

    st = StringTokenizer(readLine())
    val conditions = IntArray(k){st.getInt()}
    val sb = StringBuilder("")

    // 정렬된 카드들을 이분탐색하며 최적의 카드를 찾고, 만약 그 카드를 썼다면 더 큰 카드 중 아직 안 쓴 카드(sectionEnd + 1) 을 사용
    for(i in 0 until k){
        // 타겟보다 큰 가드 중 가장 작은 카드를 찾아야 함
        val target = conditions[i]
        // lo 이상 hi 이하의 구간에 원하는 카드가 포함됨
        var lo = 0
        var hi = m-1
        var mid:Int
        while(lo+1<hi){
            mid = (lo+hi)/2
            // 중간지점 카드가 더 작거나 같으면 [mid+1:hi] 에 목표카드가 있고
            if(target>=cardList[mid]) lo = mid+1
            // 중간지점 카드가 크면 [lo:mid] 에 목표카드가 있음
            else hi = mid
        }
        var pick = if(cardList[lo]>target) lo else hi
        if(used[pick]) pick = findSectionEnd(pick) + 1
        used[pick] = true
        // 내가 새로운 연결을 만들 수는 있으나, 재사용시에 알아서 검사할 것
        usedTill[pick] = pick
        if(i==k-1) sb.append("${cardList[pick]}")
        else sb.append("${cardList[pick]}\n")
    }
    print(sb)
}