package com.algo.day21;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

/*

문제

외판원 순회 문제는 영어로 Traveling Salesman problem (TSP) 라고 불리는 문제로 computer science 분야에서 가장 중요하게 취급되는 문제 중 하나이다. 여러 가지 변종 문제가 있으나, 여기서는 가장 일반적인 형태의 문제를 살펴보자.

1번부터 N번까지 번호가 매겨져 있는 도시들이 있고, 모든 도시 사이에는 길이 있다. 이제 한 외판원이 어느 한 도시에서 출발해 N개의 도시를 모두 거쳐 다시 원래의 도시로 돌아오는 순회 여행 경로를 계획하려고 한다. 단, 한 번 갔던 도시로는 다시 갈 수 없다. (맨 마지막에 여행을 출발했던 도시로 돌아오는 것은 예외) 이런 여행 경로는 여러 가지가 있을 수 있는데, 가장 적은 비용을 들이는 여행 계획을 세우고자 한다.

도시 A에서 도시 B로 가는 비용은 두 도시 사이의 거리와 같다. 한 도시 A의 좌표가 (xA, yA), B의 좌표가 (xB, yB)라고 한다면, 두 도시의 거리는 √((xB-xA)^2 + (yB-yA)^2)와 같다.

도시의 수 N과 모든 도시의 위치가 주어졌을 때, 가장 적은 비용을 들이는 외판원의 순회 여행 경로를 구하는 프로그램을 작성하시오.


입력

첫째 줄에 도시의 수 N이 주어진다. (2 ≤ N ≤ 16) 다음 N개의 줄에는 도시의 좌표 x, y가 주어진다. 모든 좌표는 -1,000보다 크거나 같고, 1,000보다 작거나 같은 정수이다. 두 도시의 위치가 같은 경우는 없다.


출력

첫째 줄에 외판원의 순회에 필요한 최소 비용을 출력한다. 절대/상대 오차는 10-6까지 허용한다.




 */
public class Solution2 {
    static int N;
    static int[][] map;
    static double[][] dp;

    static double dfs(int cur, int mask) {
        if(Integer.bitCount(mask) == N) return getDistance(map[cur], map[0]);

        if(dp[mask][cur] != -1) return dp[mask][cur];

        double min = 1000_000_000;
        for(int next = 0; next < N; next++) {
            if((mask & (1 << next)) != 0) continue;
            min = Math.min(min, dfs(next, mask | (1 << next)) + getDistance(map[cur], map[next]));
        }

        return dp[mask][cur] = min;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        map = new int[N][2];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            map[i][0] = Integer.parseInt(st.nextToken());
            map[i][1] = Integer.parseInt(st.nextToken());
        }

        dp = new double[1<<N][N];

        dfs(0, 1);
    }

    public static double getDistance(int[] a, int[] b) {
        return Math.sqrt((a[0] - b[0]) * (a[0] - b[0]) + (a[1] - b[1]) * (a[1] - b[1]));
    }
}
