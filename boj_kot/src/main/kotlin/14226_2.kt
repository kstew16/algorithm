import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
// 3시간 걸렸음 visited 에 시간까지 기록하니까 쭉 짧아졌는데 이유가 뭘까? (원래 Boolean 으로 하고 있었음)
// 요점은 그게 아니라 큐에 데이터를 넣기 전에 visited 표시를 하는 거였음. 중복 calling 이 엄청나게 커지나봐
// visited 를 Boolean 으로 사용하면 크기가 1/4 로 줄어들지만,
// 큐에 해당 정보를 넣어야 함 -> 이건 결론적으로 시간적 손해, 메모리 이득이었음
private const val BOUNDARY = 1050
fun main()= with(BufferedReader(InputStreamReader(System.`in`))){
    val target = readLine().toInt()
    val onScreen = 1
    val onClipboard = 0

    // 현재상태에 Screen, Clipboard 에 각각 몇 개의 이모티콘이 있고, 몇 초가 경과된 상태인가를 의미
    val queue = LinkedList<IntArray>().apply { add(intArrayOf(onScreen,onClipboard))}
    // screen, clipboard 상태에 대한 방문기록.
    val visited = Array(BOUNDARY){
        IntArray(BOUNDARY){-1}
    }.apply {
        this[onScreen][onClipboard] = 0
    }
    while(queue.isNotEmpty()){
        val (curScreen,curClip) = queue.pollFirst()
        if(curScreen>=BOUNDARY || curClip>=BOUNDARY) continue

        if(target == curScreen) {
            print(visited[curScreen][curClip])
            break
        }
        else{
            val curTime = visited[curScreen][curClip]
            // copyAll from screen to clipboard
            if(visited[curScreen][curScreen]==-1){
                visited[curScreen][curScreen] = curTime+1
                queue.add(intArrayOf(curScreen,curScreen))
            }

            // paste from clipboard to screen
            if((curScreen+curClip)<BOUNDARY && curClip>0 && visited[curScreen+curClip][curClip]==-1){
                visited[curScreen+curClip][curClip] = curTime+1
                queue.add(intArrayOf(curScreen + curClip,curClip))
            }


            // delete 1 from screen,
            if(curScreen>0 && visited[curScreen-1][curClip]==-1){
                visited[curScreen-1][curClip] = curTime+1
                queue.add(intArrayOf(curScreen-1,curClip))
            }

        }
    }
    close()
}