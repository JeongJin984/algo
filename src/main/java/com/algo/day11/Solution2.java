package com.algo.day11;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

/*
문제: 구내식당 식권 구매하기

회사원 민수는 앞으로 N일 동안 회사에 출근할 예정이다.
각 날짜마다 출근 여부가 정해져 있으며, 출근하는 날에는 반드시 구내식당에서 점심을 먹어야 한다.

구내식당에서는 다음 세 종류의 식권을 판매한다.

식권 종류	가격	사용 가능한 기간
1일 식권	A원	구매한 날만 사용 가능
3일 식권	B원	구매한 날부터 3일 동안 사용 가능
7일 식권	C원	구매한 날부터 7일 동안 사용 가능

기간 식권은 구매한 날짜를 포함하여 연속된 날짜에 적용된다.
식권의 사용 기간에 주말이나 재택근무일이 포함되더라도 기간은 그대로 지나간다.

예를 들어 5일째에 3일 식권을 구매하면 5일째, 6일째, 7일째에 사용할 수 있다.

민수는 출근하는 모든 날에 식권이 적용되도록 구매하려고 한다. 필요한 최소 비용을 구하라.

입력

첫 번째 줄에 전체 기간 N이 주어진다.

두 번째 줄에 식권 가격 A, B, C가 공백으로 구분되어 주어진다.

세 번째 줄에 길이가 N인 문자열 S가 주어진다.

S[i]가 1이면 i + 1일째에 출근한다.
S[i]가 0이면 i + 1일째에 출근하지 않는다.
출력

출근하는 모든 날의 점심을 해결하기 위해 필요한 최소 비용을 출력한다.

제한
1 ≤ N ≤ 100,000
1 ≤ A, B, C ≤ 100,000
S의 길이는 N
S는 0과 1로만 이루어져 있다.
예제 입력 1
10
4000 9000 18000
1110011110
 */
public class Solution2 {
    static int N;
    static long[] cost;
    static int[] day;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        cost = new long[3];
        for(int i = 0; i < 3; i++) {
            cost[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        String s = st.nextToken();
        day = new int[N+1];
        for(int i=1; i<=N; i++) {
            day[i] = Integer.parseInt(s.charAt(i-1) + "");
        }

        long[] dp = new long[N+1]; // dp[i] = 1일째부터 i일째까지 필요한 최소 비용
        for(int i=1; i<=N; i++) {
            if(day[i] == 0) {
                dp[i] = dp[i-1];
                continue;
            }

            long minCost = Long.MAX_VALUE;

            minCost = Math.min(minCost, dp[i-1] + cost[0]);
            minCost = Math.min(minCost, dp[Math.max(i-3, 0)] + cost[1]);
            minCost = Math.min(minCost, dp[Math.max(i-7, 0)] + cost[2]);

            dp[i] = minCost;
        }

        System.out.println(dp[N]);
    }
}
