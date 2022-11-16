import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.LinkedList
import java.util.StringTokenizer
// 한 번에 맞았는데 LIS 라는 힌트를 받은 상태에, 예전에 dp 경로기록 풀었던 기억 때문에 어느정도 아이디어 있는 상황이었음
// dp 문제에서 dp[i] 를 채우기 위해 i-1 까지의 모든 dp 를 둘러봐야 하는 경우,
// dp 값들을 갱신시킨 최근의 값을 기록하는 dp 값에 대한 배열을 이분탐색하는 것으로 그 과정을 가볍게 만들 수 있음
// 여기에 경로까지 기록해야 하는 경우, 갱신하는 시점에서의 (시점 꼬일 수 있음 꼭 갱신시점임) 경로를 기록해서 역순으로 따라가면서 (또는 역행한 후 시작지점부터) 출력하면 됨
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val destination = IntArray(500001){Int.MAX_VALUE}

    var st:StringTokenizer
    var maxPoint = 0
    val defined = BooleanArray(500001){false}
    repeat(n){
        st = StringTokenizer(readLine())
        val s = st.nextToken().toInt()
        val e =st.nextToken().toInt()
        destination[s] = e
        maxPoint = s.coerceAtLeast(maxPoint)
        defined[s] = true
    }

    // 부분수열에 특정 수를 이어붙이 때 앞의 수가 되어준 수 (Destination)
    val dpSource = IntArray(maxPoint+1){Int.MAX_VALUE}

    // 특정 길이의 LIS 를 만드는 가장 작은 destination
    // 오름차순일 수 밖에 없음, 따라서 이분탐색으로 이번 숫자를 이어붙일 수 있는 LIS 의 최장길이를 찾을 수 있겠음.
    val dpDestination = IntArray(n+1){Int.MAX_VALUE}.apply {
        this[0] = -1 // 어떤 수든 길이 1짜리의 주인이 될 수 있음
    }
    // 현재 특정 길이 IS 를 가장 작은 Destination 으로 만들 수 있게 해주는 수의 Index
    val dpIndex = IntArray(n+1){Int.MAX_VALUE}.apply { this[0] = 0 }

    var maxLength = 0
    for(i in 1..maxPoint){
        if(!defined[i]) continue
        val curDestination = destination[i]
        // 현재 발견된 모든 길이의 LIS 중 현재 destination 보다 작은 끝을 가진 가장 큰 LIS 를 찾는다
        // 정답 destination 은 [lo,hi] 에 있음
        var lo = 0
        var hi = maxLength
        var mid:Int
        while(lo+1<hi){
            mid = (lo+hi)/2
            if(dpDestination[mid]<curDestination) lo = mid
            else hi = mid
        }
        // destination[i] 가 만들 수 있는 가장 긴 부분수열의 길이
        val curLength = if(dpDestination[hi]<curDestination) hi+1 else lo+1
        // 내가 내 길이의 최고 효율을 가지고 있으면 (Destination 이 가장 작으면) 기록
        if(dpDestination[curLength]>curDestination){
            // 나를 부분수열에 끼워준 그 분 샤라웃 (필요없는거 기록 안하고 최고효율로 만든것만 기록해도 따라가는데는 문제 없음)
            dpSource[i] = dpIndex[curLength-1]
            dpDestination[curLength]=curDestination
            dpIndex[curLength] = i
        }
        // 최장기록 경신했으면 기록
        if(curLength>maxLength) maxLength = curLength
    }
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    // 가장 긴 LIS = 꼭 잘라야 하는 애들만 뺀 상태
    bw.write("${n - maxLength}\n")
    // 가장 긴 LIS 를 있게 해준 Index 부터 역으로 그 소스들을 따라가기
    val queue = LinkedList<Int>().apply{this.add(dpIndex[maxLength])}
    while (queue.isNotEmpty()){
        val cur = queue.pollFirst()
        if(cur==0) break
        defined[cur] = false
        queue.add(dpSource[cur])
    }
    for(i in 1..maxPoint) if(defined[i]) bw.write("$i\n")
    bw.flush()
    bw.close()
    close()
}