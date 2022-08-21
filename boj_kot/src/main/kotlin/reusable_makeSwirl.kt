import java.util.LinkedList

fun main(){
    data class MazeNode(val y:Int, val x:Int, val direction:Char)
    val size = 500
    val table = Array(size){CharArray(size){'F'} }
    val visited = Array(size){BooleanArray(size){false} }
    val queue = LinkedList<MazeNode>().apply{add(MazeNode(0,0,'R'))}
    while(queue.isNotEmpty()){
        val (vy,vx,last) = queue.pollFirst()
        visited[vy][vx] = true
        var ty = vy
        var tx = vx
        var cur = 'F'
        when(last){
            'R' -> tx+=1
            'D' -> ty+=1
            'L' -> tx-=1
            'U' -> ty-=1
        }
        if(ty in 0 until size && tx in 0 until size){
            if(table[ty][tx]=='F'){
                cur = last
                table[vy][vx] = cur
                if(!visited[ty][tx]) queue.add(MazeNode(ty,tx,cur))
            }else{
                cur = when(last){
                    'R' -> {
                        tx = vx
                        ty = vy+1
                        'D'
                    }
                    'D' -> {
                        tx = vx-1
                        ty = vy
                        'L'
                    }
                    'L' -> {
                        tx = vx
                        ty =vy-1
                        'U'
                    }
                    'U' -> {
                        tx = vx+1
                        ty = vy
                        'R'
                    }
                    else -> 'F'
                }
                table[vy][vx] = cur
                if(!visited[ty][tx]) queue.add(MazeNode(ty,tx,cur))
            }
        }else{
            cur = when(last){
                'R' -> {
                    tx = vx
                    ty = vy+1
                    'D'
                }
                'D' -> {
                    tx = vx-1
                    ty = vy
                    'L'
                }
                'L' -> {
                    tx = vx
                    ty =vy-1
                    'U'
                }
                'U' -> {
                    tx = vx+1
                    ty = vy
                    'R'
                }
                else -> 'F'
            }
            table[vy][vx] = cur
            if(!visited[ty][tx]) queue.add(MazeNode(ty,tx,cur))
        }
    }

    table.forEach {
        println(it.joinToString(""))
    }
}