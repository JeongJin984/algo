package com.algo.day18;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
할 일 정하기 1

N명의 사람과 N개의 일이 있다. 각 사람은 일을 하나 담당해야 하고, 각 일을 담당하는 사람은 한 명이어야 한다. 또한, 모든 사람은 모든 일을 할 능력이 있다.

사람은 1번부터 N번까지 번호가 매겨져 있으며, 일도 1번부터 N번까지 번호가 매겨져 있다.

Dij를 i번 사람이 j번 일을 할 때 필요한 비용이라고 했을 때, 모든 일을 하는데 필요한 비용의 최솟값을 구하는 프로그램을 작성하시오.

입력

첫째 줄에 사람과 일의 수 N이 주어진다.
둘째 줄부터 N개의 줄에는 D의 내용이 주어진다.

비용은 10,000보다 작거나 같은 자연수이다.

출력

모든 일을 하는데 필요한 비용의 최솟값을 출력한다.

제한
1 ≤ N ≤ 20
시간 제한: 1초
메모리 제한: 512 MB
예제 입력 1
3
2 3 3
3 2 3
3 3 2
예제 출력 1
6
 */
public class Solution1 {
    static int N;
    static int[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int size = 1<<N;
        int[] dp = new int[size];
        Arrays.fill(dp, 1_000_000_00);
        dp[0] = 0;
        for(int mask=0; mask<size; mask++) {
            int person = Integer.bitCount(mask);
            if(person >= N)  continue;
            for(int job=0; job<N; job++) {
                if((mask & (1<<job)) != 0) continue;
                int nextMask = mask | (1<<job);
                dp[nextMask] = Math.min(dp[nextMask], dp[mask] + map[person][job]);
            }
        }

        System.out.println(dp[(size)-1]);
    }
}
