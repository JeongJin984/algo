package com.algo.day18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
보석 도둑

시간 제한: 1초
메모리 제한: 256 MB

문제

세계적인 도둑 상덕이는 보석점을 털기로 결심했다.

상덕이가 털 보석점에는 보석이 총 N개 있다. 각 보석은 무게 M과 가격 V를 가지고 있다. 상덕이는 가방을 K개 가지고 있고, 각 가방에 담을 수 있는 최대 무게는 C이다. 가방에는 최대 한 개의 보석만 넣을 수 있다.

상덕이가 훔칠 수 있는 보석의 최대 가격을 구하는 프로그램을 작성하시오.

입력

첫째 줄에 N과 K가 주어진다. (1 ≤ N, K ≤ 300,000)

다음 N개의 줄에는 각 보석의 정보 M과 V가 주어진다. (0 ≤ M, V ≤ 1,000,000)

다음 K개의 줄에는 가방에 담을 수 있는 최대 무게 C가 주어진다. (1 ≤ C ≤ 100,000,000)

모든 수는 양의 정수이다.

출력

첫째 줄에 상덕이가 훔칠 수 있는 보석 가격의 합의 최댓값을 출력한다.

예제 입력 1
2 1
5 10
100 100
11
예제 출력 1
10
 */
public class Solution2 {
    static int N, K;
    static int[][] gem;
    static Integer[] bag;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        gem = new int[N][2];
        bag = new Integer[K];

        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            gem[i][0] = Integer.parseInt(st.nextToken());
            gem[i][1] = Integer.parseInt(st.nextToken());
        }

        for(int i=0; i<K; i++) {
            bag[i] = Integer.parseInt(br.readLine());
        }

        Arrays.sort(gem, Comparator.comparingInt(a -> a[0]));
        Arrays.sort(bag);

        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder()); // 현재 가방에 넣을 수 있는 gem의 후보군

        long answer = 0;
        int idx = 0;
        for (int b : bag) {
            while (idx < N && gem[idx][0] <= b) {
                pq.offer(gem[idx][1]);
                idx++;
            }

            if (!pq.isEmpty()) {
                answer += pq.poll();
            }
        }

        System.out.println(answer);
    }
}
