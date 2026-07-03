package com.algo.day17;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
문제

예술을 사랑하는 사람들이 시장에 모여서 그들의 그림을 서로 거래하려고 한다. 모든 그림의 거래는 다음과 같은 조건을 만족해야 한다.

그림을 팔 때, 그림을 산 가격보다 크거나 같은 가격으로 팔아야 한다.
같은 그림을 두 번 이상 산 것은 불가능하다.

방금 시장에 새로운 그림이 들어왔다. 1번 아티스트는 그 그림을 외부 상인에게 가격 0원 주고 샀다. 이제 그 그림을 자신의 예술가 친구들에게 팔려고 한다. 위의 조건을 모두 만족하는 거래만 이루어진다고 가정했을 때, 그림을 소유했던 사람의 수의 최댓값을 출력하는 프로그램을 작성하시오. (1번 아티스트와 마지막으로 그 그림을 소유한 사람도 포함한다).

입력

첫째 줄에 예술가의 수 N이 주어진다. N은 2보다 크거나 같고, 15보다 작거나 같은 자연수이다.

둘째 줄부터 N개의 줄에는 N개의 수가 주어진다. i번째 줄의 j번째 수는 i번 예술가가 j번 예술가에게 그 그림을 살 때의 가격이다. 모든 가격은 0이 제일 낮은 가격이고, 9가 제일 높은 가격이다.

출력

첫째 줄에 그 그림을 소유했던 사람들 (잠시라도 소유했던 사람도 포함)의 최대값을 출력한다.

예제 입력 1
3
022
101
110
예제 출력 1
2
 */
public class Solution1 {
    static int N;
    static int[][] cost;
    static int[][][] dp;

    static int dfs(int owner, int visited, int price) {
        if (dp[owner][visited][price] != -1) {
            return dp[owner][visited][price];
        }

        int max = 1;

        for (int next = 0; next < N; next++) {
            if ((visited & (1 << next)) != 0) {
                continue;
            }

            if (cost[owner][next] < price) {
                continue;
            }

            int nextVisited = visited | (1 << next);
            max = Math.max(max, 1 + dfs(next, nextVisited, cost[owner][next]));
        }

        return dp[owner][visited][price] = max;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        cost = new int[N][N];

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < N; j++) {
                cost[i][j] = line.charAt(j) - '0';
            }
        }

        dp = new int[N][1 << N][10];

        for (int i = 0; i < N; i++) {
            for (int mask = 0; mask < (1 << N); mask++) {
                for (int price = 0; price < 10; price++) {
                    dp[i][mask][price] = -1;
                }
            }
        }

        System.out.println(dfs(0, 1, 0));
    }
}
