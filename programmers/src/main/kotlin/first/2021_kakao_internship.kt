package first

import java.util.LinkedList

fun main(){
    class Node(val no:Int,var next:Node?,var previous:Node?){
        fun moveDown(k:Int):Node{
            var cur = this
            repeat(k){
                cur = cur.next!!
            }
            return cur
        }
        fun moveUp(k:Int):Node{
            var cur = this
            repeat(k){cur = cur.previous!!}
            return cur
        }
    }
    class Solution {
        fun solution(n: Int, k: Int, cmd: Array<String>): String {
            val deleted = BooleanArray(n){false}
            val deletedStack = LinkedList<Node>()

            // 초기 테이블 설정
            var head = Node(0,null,null)
            var selected = head
            for(i in 1 until n){
                val addingNode = Node(i,null,selected)
                selected.next = addingNode
                selected = addingNode
            }
            //val addingLastNode = Node(n-1,head,selected)
            //head.previous = addingLastNode
            //selected.next = addingLastNode
            selected = head.moveDown(k)

            cmd.forEach {c->
                when(c.length){
                    1->{
                        if(c == "C"){
                            val deleting = selected
                            deletedStack.addLast(deleting)
                            deleting.previous?.let { it.next = deleting.next }
                            deleting.next?.let { it.previous = deleting.previous }
                            deleted[deleting.no] = true
                            selected = if(deleting.next==null){
                                // 마지막 행 삭제시 selected 는 바로 위로
                                deleting.previous!!
                            }else{
                                // 그게 아니면 selected 는 다음 걸로
                                deleting.next!!
                            }
                        }else{ // "Z"
                            val restoreNode = deletedStack.pollLast()
                            restoreNode.previous?.let { it.next = restoreNode }
                            restoreNode.next?.let { it.previous=restoreNode }
                            deleted[restoreNode.no] = false
                        }
                    }
                    else->{
                        val (command,xString) = c.split(" ")
                        val x = xString.toInt()
                        selected = if(command=="U") selected.moveUp(x)
                        else selected.moveDown(x)
                    }
                }
            }

            return deleted.map { if(it) 'X' else 'O' }.joinToString("")
        }
    }
    val n = 8
    val k = 2
    val cmd = arrayOf(
        "D 2","C","U 3","C","D 4","C","U 2","Z","Z","U 1","C"
    )
    println(Solution().solution(n, k, cmd))
}