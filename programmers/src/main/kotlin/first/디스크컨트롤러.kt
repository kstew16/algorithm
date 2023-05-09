import java.util.*
fun main(){
    data class Job(val jobNo:Int,val cost:Int)
    class Solution {
        fun solution(jobs: Array<IntArray>): Int {
            val pq = PriorityQueue<Job>(){a,b->
                if(a.cost<b.cost) -1 else 1
            }
            var jobsLeft = jobs.size
            var curTime = 0
            var sumWaited = 0
            val jobsInQueue = BooleanArray(jobsLeft){false}
            while(jobsLeft>0){
                var minInTime = Int.MAX_VALUE
                // 현재 시간에 입력된 미처리 작업을 큐에 넣어줌
                for(i in jobs.indices){
                    if(!jobsInQueue[i]){
                        minInTime = jobs[i][0].coerceAtMost(minInTime)
                        if(jobs[i][0]<=curTime) {
                            pq.add(Job(i,jobs[i][1]))
                            jobsInQueue[i] = true
                        }
                    }
                }
                if(pq.isNotEmpty()){
                    // 처리할 일이 있는 경우 가장 짧은 작업을 처리해 줌
                    jobsLeft--
                    val (jobNo,cost) = pq.poll()
                    curTime +=  cost
                    sumWaited += (curTime - jobs[jobNo][0])

                }else{
                    // 처리할 수 있는 일이 없는 경우에는 다음 작업이 들어올 때까지 대기
                    curTime = minInTime
                }

            }

            return (sumWaited/jobs.size)
        }
    }

}