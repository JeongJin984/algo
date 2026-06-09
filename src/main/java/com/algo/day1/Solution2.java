package com.algo.day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
문제: 보석 금고 경로

N × N 금고가 있다. 각 칸에는 보석 가치가 적혀 있다.

도둑은 (0, 0)에서 시작해서 정확히 K개의 칸을 방문해야 한다. 시작 칸도 방문한 칸에 포함된다.

이동은 상하좌우로만 가능하다.

단, 다음 조건을 모두 만족해야 한다.

조건
같은 칸을 두 번 방문할 수 없다.
장애물 # 칸은 방문할 수 없다.
방문한 칸의 보석 가치 합은 정확히 S여야 한다.
방문 경로에서 인접한 세 칸의 가치가 모두 증가하거나 모두 감소하면 안 된다.

즉, 연속한 세 칸의 가치 a, b, c에 대해 다음은 금지다.

a < b < c
a > b > c
입력
N K S
N개의 줄

각 줄은 길이 N이다.

# : 장애물
0~9 : 해당 칸의 보석 가치
출력

조건을 만족하는 경로의 개수를 출력하라.

제한
3 ≤ N ≤ 8
1 ≤ K ≤ 20
0 ≤ S ≤ 100
예제 입력
4 6 18
1234
2#56
3147
2221
예제 출력
3
 */
public class Solution2 {
    static int N, K, S;
    static int[][] map;
    static boolean[][] visited;
    static int[] di = {-1, 1, 0, 0};
    static int[] dj = {0, 0, -1, 1};
    static int answer = 0;

    static void backtracking(int[] current, int[] before, int value, int depth) {

        if(value > S) return;
        if (depth == K) {
            if (value == S) answer++;
            return;
        }

        for(int i = 0; i<4; i++) {
            int[] next = new int[] {current[0] + di[i], current[1] + dj[i]};
            if(next[0] < 0 || next[0] >= N || next[1] < 0 || next[1] >= N) continue;
            if(visited[next[0]][next[1]]) continue;
            if(
                depth >= 2
                    && map[next[0]][next[1]] > map[current[0]][current[1]]
                    && map[current[0]][current[1]] > map[before[0]][before[1]]
            ) continue;
            if(
                depth >= 2
                    && map[next[0]][next[1]] < map[current[0]][current[1]]
                    && map[current[0]][current[1]] < map[before[0]][before[1]]
            ) continue;
            visited[next[0]][next[1]] = true;
            backtracking(next, current, value + map[next[0]][next[1]], depth + 1);
            visited[next[0]][next[1]] = false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        K = Integer.parseInt(input[1]);
        S = Integer.parseInt(input[2]);

        map = new int[N][N];
        visited = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            input = br.readLine().split(" ");
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(input[0].charAt(j) + "");
            }
        }

        visited[0][0] = true;
        backtracking(new int[] {0, 0}, new int[] {0, 0},  map[0][0], 0);

        System.out.print(answer);
    }
}
