package com.algo.day26;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/*
로봇 조종하기
문제

NASA에서는 화성 탐사를 위해 화성에 무선 조종 로봇을 보냈다. 실제 화성의 모습은 굉장히 복잡하지만, 로봇의 메모리가 얼마 안 되기 때문에 지형을 N×M 배열로 단순화하여 생각하기로 한다.

지형의 고저차의 특성상, 로봇은 움직일 때 배열에서 왼쪽, 오른쪽, 아래쪽으로 이동할 수 있지만, 위쪽으로는 이동할 수 없다. 또한 한 번 탐사한 지역(배열에서 하나의 칸)은 다시 탐사하지 않는다.

각각의 지역은 탐사 가치가 있는데, 로봇을 배열의 왼쪽 위 (1, 1) 에서 출발시켜 오른쪽 아래 (N, M) 으로 보내려고 한다. 이때 위의 조건을 만족하면서, 탐사한 지역들의 가치의 합이 최대가 되도록 하는 프로그램을 작성하시오.

입력

첫째 줄에 N, M (1 ≤ N, M ≤ 1,000) 이 주어진다.

다음 N개의 줄에는 M개의 수로 배열이 주어진다. 배열의 각 수는 절댓값이 100을 넘지 않는 정수이다. 이 값은 그 지역의 가치를 나타낸다.

출력

첫째 줄에 최대 가치의 합을 출력한다.

예제 입력 1
5 5
10   25    7   8  13
68   24  -78  63  32
12  -69  100 -29 -25
-16 -22  -57 -33  99
7   -76  -11  77  15
예제 출력 1
319
 */
public class Solution1 {
    static int N, M;
    static int[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[] dp = new int[M];
        dp[0] = map[0][0];

        // 첫 번째 행은 오른쪽으로만 이동 가능
        for (int j = 1; j < M; j++) {
            dp[j] = dp[j - 1] + map[0][j];
        }

        for (int i = 1; i < N; i++) {
            int[] left = new int[M];
            int[] right = new int[M];

            // 왼쪽 -> 오른쪽
            left[0] = dp[0] + map[i][0];
            for (int j = 1; j < M; j++) {
                left[j] = Math.max(dp[j], left[j - 1]) + map[i][j];
            }

            // 오른쪽 -> 왼쪽
            right[M - 1] = dp[M - 1] + map[i][M - 1];
            for (int j = M - 2; j >= 0; j--) {
                right[j] = Math.max(dp[j], right[j + 1]) + map[i][j];
            }

            for (int j = 0; j < M; j++) {
                dp[j] = Math.max(left[j], right[j]);
            }

            System.out.println(dp[M - 1]);
        }
    }
}
