fun main(){
    data class CharNode(var previous:CharNode?,var next:CharNode?,var c:Char)
    class CharList(var root:CharNode?,var last:CharNode?){
        var size:Int = 0
        fun stringfy():String{
            val sb = StringBuilder()
            var printing = root
            while(printing!=null){
                sb.append(printing.c)
                printing = printing.next
            }
            return sb.toString()
        }
        fun add(adding:CharNode){
            this.size+=1
            if(this.root==null){
                this.root = adding
                adding.previous = null
            }
            else {
                this.last!!.next = adding
                adding.previous = this.last!!
            }

            this.last = adding
        }
        fun poll():CharNode{
            this.size-=1
            val rNode = this.root!!
            this.root = this.root!!.next
            rNode.next = null
            if(this.root == null) this.last =null
            else this.root!!.previous = null

            return rNode
        }
        fun isEmptyList():Boolean{
            return this.size==0
        }
    }
    class Solution {
        fun solution(s: Array<String>): Array<String> {
            var answer = ArrayList<String>()
            s.forEach{ it ->
                val afterTask = CharList(null,null)
                val beforeTask = CharList(null,null)

                // String 을 CharList 화하여 before 에 넣음
                var lastNode:CharNode? = null
                var currentNode:CharNode? = null

                for(i in it.indices){
                    currentNode = CharNode(lastNode,null,it[i])
                    lastNode?.let{
                        it.next = currentNode
                    }
                    //if(i==0) beforeTask.root = currentNode
                    beforeTask.add(currentNode)
                    lastNode = currentNode
                }

                // before 앞에 있는 0 들을 모두 after에 옮김
                while(!beforeTask.isEmptyList()&&beforeTask.root!!.c == '0'){
                    afterTask.add(beforeTask.poll())
                }

                if(beforeTask.size>=3){
                    // before 들을 스캔하면서 110이 있으면 연결을 끊어서 after 의 끝에 붙여줌
                    val scanning = Array<CharNode>(3){
                        when(it){
                            0-> beforeTask.root!!
                            1-> beforeTask.root!!.next!!
                            else-> beforeTask.root!!.next!!.next!!
                        }
                    }


                    while(!beforeTask.isEmptyList()){
                        if(
                            scanning[0].c == '1' &&
                            scanning[1].c == '1' &&
                            scanning[2].c == '0'
                        ){
                            // 조건에 해당하는 경우

                            // 잘릴 리스트를 다시 잘 연결해줌
                            val front = scanning[0].previous
                            val back = scanning[2].next

                            if(front!=null && back!=null){
                                front.next = back
                                back.previous = front
                            }


                            // 다음 윈도우 위치 찾기
                            val next = Array<CharNode?>(3){null}
                            when{
                                (front?.previous != null)->{
                                    // 두 칸 앞
                                    next[0] = front.previous //notNull
                                    next[1] = front //notNull
                                    next[2] = back //nullable
                                }
                                (front != null && front.previous==null)->{
                                    // 한 칸 앞
                                    next[0] = front // notNull
                                    next[1] = back // nullable
                                    //back?.let{next[2] = it.next} //nullable
                                    next[2] = back?.next
                                }
                                else ->{
                                    //(front == null)
                                    next[0] = back // nullable
                                    next[1] = next[0]?.next //nullable
                                    next[2] = next[1]?.next //nullable
                                }

                            }

                            // 조건에 맞는 경우 after 제일 뒤에 붙임
                            for(i in 0..2){
                                scanning[i].next = null
                                afterTask.add(scanning[i])
                            }
                            if(next.any{it==null}) break
                            else{
                                for(i in 0..2){
                                    scanning[i] = next[i]!!
                                }
                            }

                        }else{
                            // 조건에 맞지 않는 경우 그냥 다음 문자 보면 됨
                            scanning[0] = scanning[1]
                            scanning[1] = scanning[2]
                            if(scanning[2].next==null) break
                            else scanning[2] = scanning[2].next!!
                        }
                    }

                }

                // 남은 글자들도 마저 옮겨줌
                while(!beforeTask.isEmptyList()){
                    afterTask.add(beforeTask.poll())
                }
                // 답안 기록
                answer.add(afterTask.stringfy())
            }
            return answer.toTypedArray()
        }
    }
/*
간단하게 생각해서 옮길 수 있는 건 110뿐이니까 110이 앞에 오든 뒤에 오든
1뒤에 삽입되는 경우
***1110***
0뒤에 삽입되는 경우
***0110***
1앞에 삽입되는 경우
***1101***
***1100***
-> 어떤 경우에도 110이 삽입된 자리에 110을 만들어내지 못함
그러나 사라진 자리가
11[]0
1[]10 인 경우에는 새로운 110이 생성이 됨
110이 삽입될 자리는 0이 가장 앞에 올 수 있는 자리인데
가장 앞에 있는 0 뒤에 삽입되는게 맞군 그게 없으면 맨 앞
*/


}