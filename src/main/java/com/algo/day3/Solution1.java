package com.algo.day3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
문제: 야근 도시락 배달

회사에는 N개의 사무실이 있고, 야근 중인 직원들에게 도시락을 배달해야 한다.

배달원은 0번 사무실에서 출발한다.
각 사무실에는 도시락을 원하는 직원이 있을 수도 있고, 없을 수도 있다.

도시락을 원하는 사무실들을 모두 정확히 한 번 이상 방문해야 한다.
단, 같은 사무실을 여러 번 지나가는 것은 가능하다.

사무실 사이에는 이동 통로가 있으며, 각 통로를 지나면 시간이 든다.

문제는 단순히 최단거리만 구하는 것이 아니다.

배달원은 피곤하기 때문에, 도시락을 원하는 사무실을 방문하는 순서를 직접 정해야 한다.
그리고 그 순서가 가능한 모든 경우 중에서 총 이동 시간이 가장 짧은 경우를 찾아야 한다.

입력

첫째 줄에 사무실 수 N, 통로 수 M, 도시락 요청 사무실 수 K가 주어진다.

2 ≤ N ≤ 15
1 ≤ M ≤ N(N-1)/2
1 ≤ K ≤ N-1

둘째 줄에 도시락을 요청한 사무실 번호 K개가 주어진다.
0번 사무실은 요청 사무실에 포함되지 않는다.

이후 M개의 줄에 다음 정보가 주어진다.

A B C

이는 A번 사무실과 B번 사무실 사이를 이동하는 데 C분이 걸린다는 뜻이다.

1 ≤ C ≤ 100

모든 사무실은 서로 이동 가능하다.

출력

도시락을 요청한 모든 사무실을 방문하는 데 필요한 최소 시간을 출력하라.

예시 입력
5 7 3
1 3 4
0 1 4
0 2 2
2 1 1
1 3 5
2 3 8
3 4 3
1 4 7
예시 출력
9
 */
public class Solution1 {
    static int N, M, K;
    static int[][] dist;
    static int[] targets;
    static int answer = Integer.MAX_VALUE;

    static void dfs(int cur, int mask, int cost) {
        if (mask == (1 << K) - 1) {
            answer = Math.min(answer, cost);
            return;
        }

        for (int i = 0; i < K; i++) {
            if ((mask & (1 << i)) != 0) continue;

            int nextOffice = targets[i];
            dfs(
                nextOffice,
                mask | (1 << i),
                cost + dist[cur][nextOffice]
            );
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        K = Integer.parseInt(input[2]);

        targets = new int[K];
        input = br.readLine().split(" ");
        for(int i = 0; i < K; i++) {
            targets[i] = Integer.parseInt(input[i]);
        }

        dist = new int[N][N];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                dist[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int i = 0; i < M; i++) {
            input = br.readLine().split(" ");
            dist[Integer.parseInt(input[0])][Integer.parseInt(input[1])] = Integer.parseInt(input[2]);
            dist[Integer.parseInt(input[1])][Integer.parseInt(input[0])] = Integer.parseInt(input[2]);
        }

        for (int mid = 0; mid < N; mid++) {
            for (int start = 0; start < N; start++) {
                for (int end = 0; end < N; end++) {
                    if (dist[start][end] > dist[start][mid] + dist[mid][end]) {
                        dist[start][end] = dist[start][mid] + dist[mid][end];
                    }
                }
            }
        }

        dfs(0, 0, 0);
    }
}
