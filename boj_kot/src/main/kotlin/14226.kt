import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList

private const val BOUNDARY = 1050
fun main()= with(BufferedReader(InputStreamReader(System.`in`))){
    val target = readLine().toInt()
    val onScreen = 1
    val onClipboard = 0

    // 현재상태에 Screen, Clipboard 에 각각 몇 개의 이모티콘이 있고, 몇 초가 경과된 상태인가를 의미
    val queue = LinkedList<IntArray>().apply { add(intArrayOf(onScreen,onClipboard,0)) }
    // screen, clipboard 상태에 대한 방문기록.
    val visited = Array(BOUNDARY){
        BooleanArray(BOUNDARY){false}
    }.apply {
        this[onScreen][onClipboard] = true
    }

    while(queue.isNotEmpty()){
        val (curScreen,curClip,curTime) = queue.pollFirst()
        if(curScreen>=BOUNDARY || curClip>=BOUNDARY) continue
        visited[curScreen][curClip] = true
        if(target == curScreen) {
            print(curTime)
            break
        }
        else{
            val nextTime = curTime+1
            // copyAll from screen to clipboard
            if(!visited[curScreen][curScreen]){
                visited[curScreen][curScreen] = true
                queue.add(intArrayOf(curScreen,curScreen,nextTime))
            }

            // paste from clipboard to screen
            if((curScreen+curClip)<BOUNDARY && curClip>0 && !visited[curScreen+curClip][curClip]){
                visited[curScreen+curClip][curClip] = true
                queue.add(intArrayOf(curScreen + curClip,curClip,nextTime))
            }


            // delete 1 from screen,
            if(curScreen>0 && !visited[curScreen-1][curClip]){
                visited[curScreen-1][curClip]
                queue.add(intArrayOf(curScreen-1,curClip,nextTime))
            }

        }
    }
    close()
}