fun main(){
    val size = 50
    val table = Array(size){CharArray(size){'F'} }
    val visited = Array(size){BooleanArray(size){false} }
    fun dfs(vy:Int,vx:Int,last:Char){
        if(visited[vy][vx]) return
        else visited[vy][vx] = true
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
                dfs(ty,tx,cur)
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
                dfs(ty,tx,cur)
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
            dfs(ty,tx,cur)
        }
    }
    dfs(0,0,'R')
    table.forEach {
        println(it.joinToString(""))
    }
}