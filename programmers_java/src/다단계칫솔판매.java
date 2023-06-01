import java.util.HashMap;

public class 다단계칫솔판매 {
    class Solution {
        public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
            int n = enroll.length;
            HashMap<String,Integer> nameToNum = new HashMap<String,Integer>();
            int[] sold = new int[n];
            int[] parent = new int[n];
            for(int i=0;i<n;i++){nameToNum.put(enroll[i],i);}
            for(int i=0;i<n;i++){
                if(!referral[i].equals("-"))parent[i] = nameToNum.get(referral[i]);
                else parent[i] = -1;
            }
            for(int i=0;i<seller.length;i++){giveFeedback(nameToNum.get(seller[i]),amount[i],sold,parent);}
            return sold;
        }
        void giveFeedback(int me,int amountLeft,int[] sold,int[] parent){
            int toParent = amountLeft/10;
            int mine = amountLeft - toParent;
            sold[me] += mine;
            if(toParent>0 && parent[me]!=-1) giveFeedback(parent[me],toParent,sold,parent);
        }
    }
}