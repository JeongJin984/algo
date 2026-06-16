package com.algo.day7;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
문제: 냉장고 정리 대작전

민수는 주말마다 냉장고를 정리한다.
냉장고 안에는 음식이 N개 들어 있고, 음식들은 왼쪽부터 오른쪽까지 일렬로 놓여 있다.

민수는 음식을 버리지 않고 모두 먹으려고 한다.
다만 한 번에 먹을 음식들은 연속된 구간이어야 한다.

예를 들어 음식이 1번부터 7번까지 있다면, 한 번에 2번~5번 음식을 먹는 것은 가능하지만, 2번, 4번, 5번처럼 중간을 건너뛰는 것은 불가능하다.

각 음식에는 맛 점수 A_i가 있다.
민수가 한 번에 어떤 연속 구간의 음식을 먹으면, 그때 얻는 만족도는 다음과 같다.

구간 안 음식들의 맛 점수 합 × 그 구간에서 가장 맛 점수가 낮은 음식의 점수

즉, 구간 [l, r]을 먹었을 때 만족도는

(A_l + A_{l+1} + ... + A_r) × min(A_l, A_{l+1}, ..., A_r)

이다.

민수는 냉장고의 모든 음식을 정확히 한 번씩 먹어야 한다.
즉, 1번~N번 음식을 여러 개의 연속 구간으로 나누어 먹어야 한다.

하지만 민수는 너무 자주 먹으면 귀찮기 때문에, 음식을 먹는 횟수는 최대 K번까지만 가능하다.

민수가 얻을 수 있는 만족도의 최댓값을 구하라.

입력

첫째 줄에 음식의 개수 N과 먹을 수 있는 최대 횟수 K가 주어진다.

둘째 줄에 각 음식의 맛 점수 A_1, A_2, ..., A_N이 주어진다.

1 ≤ K ≤ N ≤ 500
1 ≤ A_i ≤ 1,000,000
출력

민수가 얻을 수 있는 만족도의 최댓값을 출력한다.

정답은 64비트 정수 범위를 사용할 수 있다.

예제 입력 1
5 2
3 1 4 1 5
예제 출력 1
45
 */
public class Solution1 {
    static int N, K;
    static long[] A;
    static long[][] score;
    static long[][] dp;
    static final long NEG = Long.MIN_VALUE / 4;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        A = new long[N];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            A[i] = Long.parseLong(st.nextToken());
        }

        score = new long[N][N];
        for(int l=0; l<N; l++) {
            long sectionSum = 0;
            long sectionMin = Long.MAX_VALUE;

            for(int r=l; r<N; r++) {
                sectionSum += A[r];
                sectionMin = Math.min(sectionMin, A[r]);
                score[l][r] = sectionSum * sectionMin;
            }
        }


        // dp[t][i] = 1번부터 i번까지를 정확히 t개 구간으로 나눴을 때 최대 점수
        dp = new long[K + 1][N + 1];

        for (int t = 0; t <= K; t++) {
            Arrays.fill(dp[t], NEG);
        }

        dp[0][0] = 0;

        for (int t = 1; t <= K; t++) {
            for (int i = 1; i <= N; i++) {

                // 마지막 구간을 [cut + 1, i]로 잡는다.
                for (int cut = 0; cut < i; cut++) {
                    if (dp[t - 1][cut] == NEG) continue;

                    dp[t][i] = Math.max(
                        dp[t][i],
                        dp[t - 1][cut] + score[cut + 1][i]
                    );
                }
            }
        }

        long answer = 0;

        // 문제 조건이 "최대 K번"이면 1번~K번 중 최댓값
        for (int t = 1; t <= K; t++) {
            answer = Math.max(answer, dp[t][N]);
        }

        System.out.println(answer);
    }
}
