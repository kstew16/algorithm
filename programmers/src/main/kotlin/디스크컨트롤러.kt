import java.util.PriorityQueue
fun main(){
    data class Job(val start:Int,val cost:Int)
    class Solution {
        fun solution(jobs: Array<IntArray>): Int {
            var totalDelayed = 0 //SJF
            val waitingJobs = PriorityQueue<Job>(){ a, b-> if(a.start<b.start) -1 else 1}
            val jobQueue = PriorityQueue<Job>(){a,b-> if(a.cost<b.cost) -1 else 1}
            jobs.forEach{waitingJobs.add(Job(it[0],it[1]))}
            var curTime = 0
            while(waitingJobs.isNotEmpty() || jobQueue.isNotEmpty()){
                while(waitingJobs.isNotEmpty() && waitingJobs.peek().start<=curTime){jobQueue.add(waitingJobs.poll())}
                if(waitingJobs.isNotEmpty() && jobQueue.isEmpty()){
                    curTime =  waitingJobs.peek().start
                    continue
                }
                val (start,cost) = jobQueue.poll()
                curTime += cost
                totalDelayed += (curTime-start)
            }
            return totalDelayed/jobs.size
        }
    }
}