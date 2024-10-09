import java.util.*;
import java.io.*;

class Solution {
    public int solution(int[][] jobs) {
        int count = 0; // 진행한 작업 시간 
        int answer = 0; // 
        int end = 0; // 끝나는 시점
        int jobsIdx = 0; // jobs 배열의 인덱스
        
        // 작업이 요청되는 시점을 오름차순으로 정렬
        Arrays.sort(jobs, (o1, o2) -> o1[0] - o2[0]);
        // 작업의 소요시간을 기준으로 오름차순으로 정렬
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
        
        while(count < jobs.length){
            while(jobsIdx < jobs.length && jobs[jobsIdx][0] <= end){
                pq.add(jobs[jobsIdx++]);
            }
            if(pq.isEmpty()){
                end = jobs[jobsIdx][0];
            }else{
                int[] temp = pq.poll();
                // 누적합 += 현재 작업의 길이 + 마지막 위치 - 현재 작업의 시작 위치
                // => 겹치는 부분을 빼주는 것이다. 
                answer += temp[1] + end - temp[0];
                // 마지막 위치에서 현재 배열의 길이를 더해준다.
                // => 
                end += temp[1];
                count++;
            }
        }
        return (int) Math.floor(answer/jobs.length);
    }
}