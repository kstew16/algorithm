import java.util.Random
// 끼우는 동작은 적당한 위치에서 바꾸는 행위로 대치 가능함을 기억
// 힙 리무브는 위에 빼고 꼬리 뎃고와서 내림
// 힙 애드는 끝에 넣고 부모랑 비교해가면서 올라오기
// 퀵정렬은
fun main(){
    val n = 10000
    val r = Random()
    val initialArr = IntArray(n){r.nextInt(100000)}
    val answerArr = initialArr.sorted().toIntArray()

    fun bubbleSort(inputArr:IntArray):IntArray{
        val outputArr = IntArray(n){inputArr[it]}
        for(i in 0 until n){
            for(j in 0 until n-1){
                if(outputArr[j]>outputArr[j+1]){
                    val tmp = outputArr[j]
                    outputArr[j] = outputArr[j+1]
                    outputArr[j+1] = tmp
                }
            }
        }
        return outputArr
    }

    fun selectionSort(inputArr:IntArray):IntArray{
        val outputArr = IntArray(n){inputArr[it]}
        var count = 0
        while(count<n){
            var minPosition = 0
            var minValue = Int.MAX_VALUE
            for(i in count until n){
                if(outputArr[i]<minValue){
                    minValue = outputArr[i]
                    minPosition = i
                }
            }
            outputArr[minPosition] = outputArr[count]
            outputArr[count] = minValue
            count++
        }
        return outputArr
    }


    fun insertSort(inputArr:IntArray):IntArray{
        val outputArr = IntArray(n){inputArr[it]}
        for(i in 1 until n){
            val pick = outputArr[i]
            var position = 0
            while(position<i && outputArr[position]<pick){ position++}
            for(j in i downTo position+1){outputArr[j] = outputArr[j-1]}
            outputArr[position] = pick
        }
        return outputArr
    }

    fun mergeSort(inputArr:IntArray):IntArray{
        val input = arrayListOf<Int>().apply { inputArr.forEach { add(it) } }
        fun splitAndMerge(inputList:ArrayList<Int>):ArrayList<Int>{
            if(inputList.size<2) return inputList
            val returnList = ArrayList<Int>()
            val half = ArrayList<Int>().apply{repeat(inputList.size/2){add(inputList.removeFirst())} }
            val left = splitAndMerge(half)
            val right = splitAndMerge(inputList)
            while(left.isNotEmpty() && right.isNotEmpty()){
                returnList.add(if(left.first()<right.first()) left.removeFirst() else right.removeFirst())
            }
            while(left.isNotEmpty()){returnList.add(left.removeFirst())}
            while(right.isNotEmpty()){returnList.add(right.removeFirst())}
            return returnList
        }
        return splitAndMerge(input).toIntArray()
    }

    fun quickSort(inputArr:IntArray):IntArray{
        val inputList = ArrayList<Int>().apply { inputArr.forEach { add(it) } }
        fun dnc(inputList:ArrayList<Int>):ArrayList<Int>{
            if(inputList.size<=1) return inputList
            val returnList = ArrayList<Int>()
            val pivot = inputList.removeFirst()
            val left = ArrayList<Int>()
            val right = ArrayList<Int>()
            while(inputList.isNotEmpty()){
                inputList.removeFirst().let{
                    if(it>pivot) right.add(it) else left.add(it)
                }
            }
            dnc(left).forEach { returnList.add(it) }
            returnList.add(pivot)
            dnc(right).forEach { returnList.add(it) }
            return returnList
        }
        return dnc(inputList).toIntArray()
    }

    fun quickSort2(inputArr:IntArray):IntArray{
        val outputArr = IntArray(inputArr.size){inputArr[it]}
        fun resolve(from:Int,to:Int){
            val pivot = outputArr[from]
            var lo = (from+1)
            var hi = to
            while(lo<=hi){
                while(lo<=to && outputArr[lo]<pivot) lo++
                while(hi>=from && outputArr[hi]>pivot) hi--
                if(lo<=hi){
                    val tmp = outputArr[lo]
                    outputArr[lo] = outputArr[hi]
                    outputArr[hi] = tmp
                    lo++
                    hi--
                }
            }
            if(hi>from){
                outputArr[from] = outputArr[hi]
                outputArr[hi] = pivot
            }
            if((hi-1)-from+1>1) resolve(from,hi-1)
            if(to-lo+1>1) resolve(lo,to)
        }
        resolve(0,n-1)
        return outputArr
    }

    fun heapSort(inputArr:IntArray):IntArray{
        val outputArr = IntArray(inputArr.size){inputArr[it]}
        var heapIndexLimit = inputArr.size-1
        if(heapIndexLimit<1) return outputArr

        fun Int.getLeftChildIndex():Int = this*2+1
        fun Int.getRightChildIndex():Int = this*2+2
        fun Int.getParent():Int = (this-1)/2

        fun swap(arr:IntArray, indexA:Int,indexB:Int){
            val tmp = arr[indexA]
            arr[indexA] = arr[indexB]
            arr[indexB] = tmp
        }

        fun heapify(arr:IntArray, rootIndex:Int, lastIndex:Int){
            var largestIndex = rootIndex
            var parentIndex = rootIndex
            var leftChildIndex = rootIndex.getLeftChildIndex()
            var rightChildIndex = rootIndex.getRightChildIndex()
            while(leftChildIndex<=lastIndex){
                if(leftChildIndex<=lastIndex && arr[leftChildIndex]>arr[largestIndex]) largestIndex = leftChildIndex
                if(rightChildIndex<=lastIndex && arr[rightChildIndex]>arr[largestIndex]) largestIndex = rightChildIndex
                if(largestIndex!=parentIndex){ // 교환이 필요함, 교환이 발생한 자리를 루트로 하는 힙 다시 봐야 함
                    swap(arr,largestIndex,parentIndex)
                    parentIndex = largestIndex
                    leftChildIndex = parentIndex.getLeftChildIndex()
                    rightChildIndex = parentIndex.getRightChildIndex()
                }else return
            }
        }
        var i = heapIndexLimit.getParent()
        while(i>=0){
            heapify(outputArr,i,heapIndexLimit)
            i--
        }
        while(heapIndexLimit>0){
            swap(outputArr,0,heapIndexLimit) // 제일 큰 거 뒤로 보내기
            heapify(outputArr,0,--heapIndexLimit)
        }
        return outputArr
    }

    for(i in 0 until 6){
        val returnArr = when(i){
            0-> {bubbleSort(initialArr)}
            1->{selectionSort(initialArr)}
            2->{insertSort(initialArr)}
            3->{mergeSort(initialArr)}
            4->{ quickSort2(initialArr) }
            else->{heapSort(initialArr) }
        }
        if(!returnArr.contentEquals(answerArr)) println("$i failed")
        else println("$i done")
    }

}