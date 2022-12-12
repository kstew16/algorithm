import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,m) = readLine().split(" ").map{it.toInt()}
    val isBlocked = Array(n){ readLine().map { it == '1' }.toBooleanArray() }
    val pureDistance = Array(n){IntArray(m){Int.MAX_VALUE} }.apply { this[0][0] = 1 }
    val distanceBeyondWall = Array(n){IntArray(m){Int.MAX_VALUE} }
    data class Node(val y:Int,val x:Int, var distance:Int, var isBeyondWall:Boolean)
    val queue = LinkedList<Node>().apply { this.add(Node(0,0,1,false)) }

    val dy = intArrayOf(0,1,0,-1)
    val dx = intArrayOf(1,0,-1,0)
    while(queue.isNotEmpty()){
        val cur = queue.pollFirst()
        if(cur.isBeyondWall){
            if(distanceBeyondWall[cur.y][cur.x]<cur.distance) continue
        }else{
            if(pureDistance[cur.y][cur.x]<cur.distance) continue
        }
        for(i in 0..3){
            val ny = cur.y + dy[i]
            val nx = cur.x + dx[i]
            if(ny !in 0 until n || nx !in 0 until m) continue
            val newDistance= cur.distance+1
            if(cur.isBeyondWall){
                // 이미 벽 부쉈으면 비어있는 데만 갈 수 있음
                if(!isBlocked[ny][nx] && distanceBeyondWall[ny][nx]>newDistance){
                    distanceBeyondWall[ny][nx] = newDistance
                    queue.add(Node(ny,nx,newDistance,true))
                }
            }else{
                if(isBlocked[ny][nx]){
                    if(distanceBeyondWall[ny][nx]>newDistance){
                        distanceBeyondWall[ny][nx] = newDistance
                        queue.add(Node(ny,nx,newDistance,true))
                    }
                }else{
                    if(pureDistance[ny][nx]>newDistance){
                        pureDistance[ny][nx] = newDistance
                        queue.add(Node(ny,nx,newDistance,false))
                    }
                }
            }
        }
    }
    val minDist = kotlin.math.min(pureDistance[n-1][m-1],distanceBeyondWall[n-1][m-1])
    print(if(minDist!=Int.MAX_VALUE) minDist else -1)
}