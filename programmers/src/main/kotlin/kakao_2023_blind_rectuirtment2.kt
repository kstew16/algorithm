fun main(){
    class Coordinate(val y:Int,val x:Int){
        override fun equals(other: Any?): Boolean {
            return if (other != null) {
                if(this.javaClass != other.javaClass) false
                else{
                    val otherCoordinate = other as Coordinate
                    this.x == otherCoordinate.x && this.y == otherCoordinate.y
                }
            }
            else false
        }

        fun toValue():Int{
            return this.y*50 + this.x
        }
    }
    class Solution {
        fun solution(commands: Array<String>): Array<String> {
            var answer = ArrayList<String>()
            var text = Array(50){Array(50){""} }
            val parent = Array(50){y->
                Array(50){x->
                    Coordinate(y,x)
                }
            }

            fun find(r:Int, c:Int):Coordinate{
                val py = parent[r][c].y
                val px = parent[r][c].x
                if(py == r && px == c) return Coordinate(r,c)
                parent[r][c] = find(py,px)
                return  parent[r][c]
            }

            fun union(ay:Int,ax:Int,by:Int,bx:Int){
                val pa = find(ay,ax)
                val pb = find(by,bx)
                if(pa==pb) return
                else if(pb.toValue()<pa.toValue()) parent[ay][ax] = pb
                else parent[by][bx] = pa
            }
            commands.forEach {cmd->
                val commandBasket = cmd.split(" ")
                when(commandBasket[0]){
                    "UPDATE"->{
                        if(commandBasket.size==4){ // "UPDATE r c value"
                            val r = commandBasket[1].toInt()-1
                            val c = commandBasket[2].toInt()-1
                            val parent = find(r,c)
                            text[parent.y][parent.x] = commandBasket[3]
                        }else{ //"UPDATE value1 value2"
                            for(i in 0 until 50){
                                for(j in 0 until 50) if(text[i][j] == commandBasket[1]) text[i][j] = commandBasket[2]
                            }
                        }
                    }
                    "MERGE"->{ // "MERGE r1 c1 r2 c2"
                        val tr1 = commandBasket[1].toInt()-1
                        val tc1 = commandBasket[2].toInt()-1
                        val tr2 = commandBasket[3].toInt()-1
                        val tc2 = commandBasket[4].toInt()-1
                        if(tr1!=tr2 || tc1!=tc2){
                            // 진짜로 뭘 선택한지 찾기
                            val r1:Int; val c1:Int
                            val r2:Int; val c2:Int
                            with(find(tr1,tc1)){
                                r1 = this.y
                                c1 = this.x
                            }
                            with(find(tr2,tc2)){
                                r2 = this.y
                                c2 = this.x
                            }
                            val newText = if(text[r1][c1] != "") text[r1][c1] else text[r2][c2]
                            union(r1,c1,r2,c2)
                            with(find(r1,c1)){
                                text[this.y][this.x] = newText
                            }
                        }
                    }
                    "UNMERGE"->{ // "UNMERGE r c"
                        val r = commandBasket[1].toInt()-1
                        val c = commandBasket[2].toInt()-1
                        val unmerging:Coordinate
                        val abandonedText = with(find(r,c)){
                            unmerging = this
                            text[this.y][this.x]
                        }

                        for(y in 0 until 50){
                            for(x in 0 until 50){
                                find(y,x)
                                if(parent[y][x] == unmerging){
                                    parent[y][x] = Coordinate(y,x)
                                    text[y][x] = ""
                                }
                            }
                        }
                        text[r][c] = abandonedText

                    }
                    else->{//PRINT
                        val r = commandBasket[1].toInt()-1
                        val c = commandBasket[2].toInt()-1
                        val t = with(find(r,c)){
                            text[this.y][this.x]
                        }
                        answer.add(
                            if(t=="") "EMPTY" else t
                        )
                    }
                }
            }

            return answer.toTypedArray()
        }
    }
    println(Solution().solution(
        arrayOf(
            "UPDATE 1 1 a", "UPDATE 1 2 b", "UPDATE 2 1 c", "UPDATE 2 2 d", "MERGE 1 1 1 2", "MERGE 2 2 2 1", "MERGE 2 1 1 1", "PRINT 1 1", "UNMERGE 2 2", "PRINT 1 1"
        )
    ).joinToString(" "))
}