package com.algo.day17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
수영장 만들기

지민이는 수영장을 만들려고 한다. 수영장을 만들 곳의 크기는 N×M이고, 각 칸은 직육면체이다. 따라서, 각 칸의 직육면체의 높이가 쓰여 있는 다음과 같은 땅을 생각할 수 있다.

16661
61116
16661

이 수영장은 15만큼의 물이 들어있는 수영장을 만들 수 있다. 가운데 3개의 칸에 5만큼 물을 채우면 되기 때문이다.

자 이제 가운데 물을 더 추가했다고 생각하면, 벽(높이가 6인 직육면체)을 넘어서 밖으로 나갈 것이다. 물은 항상 높이가 더 낮은 곳으로만 흐르고, 직육면체의 위의 표면에는 물이 없다. 그리고, 땅의 높이는 0이고, 땅은 물을 무한대로 흡수할 수 있다.

땅의 모양이 주어질 때, 수영장에 물이 얼마나 있을 수 있는지 구하는 프로그램을 작성하시오.

입력

첫째 줄에 N과 M이 주어진다.
N과 M은 50보다 작거나 같은 자연수이다.

둘째 줄부터 N개의 줄에 땅의 높이가 주어진다.
높이는 1보다 크거나 같고, 9보다 작거나 같은 자연수이다.

출력

첫째 줄에 문제의 정답을 출력한다.

예제 입력 1
3 5
16661
61116
16661
예제 출력 1
15
예제 입력 2
4 6
999999
955119
955119
999999
예제 출력 2
48
예제 입력 3
5 9
111111111
115111611
131516161
115111611
111111111
예제 출력 3
7
예제 입력 4
9 13
1111111111111
1555555555551
1511111111151
1511199911151
1511192911151
1511199911151
1511111111151
1555555555551
1111111111111
예제 출력 4
151
 */
public class Solution2 {
    static int N, M;
    static int[][] map;
    static boolean[][] visited;
    static int[] di = {-1, 1, 0, 0};
    static int[] dj = {0, 0, -1, 1};

    static class Cell {
        int r, c, h;

        Cell(int r, int c, int h) {
            this.r = r;
            this.c = c;
            this.h = h;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        visited = new boolean[N][M];
        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String input = st.nextToken();
            for(int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(input.charAt(j) + "");
            }
        }

        PriorityQueue<Cell> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.h));

        for (int i = 0; i < N; i++) {
            addBoundary(pq, i, 0);
            addBoundary(pq, i, M - 1);
        }

        for (int j = 1; j < M - 1; j++) {
            addBoundary(pq, 0, j);
            addBoundary(pq, N - 1, j);
        }

        int answer = 0;

        while (!pq.isEmpty()) {
            Cell cur = pq.poll();

            for (int d = 0; d < 4; d++) {
                int nr = cur.r + di[d];
                int nc = cur.c + dj[d];

                if (nr < 0 || nr >= N || nc < 0 || nc >= M) continue;
                if (visited[nr][nc]) continue;

                visited[nr][nc] = true;

                if (map[nr][nc] < cur.h) {
                    answer += cur.h - map[nr][nc];
                    pq.offer(new Cell(nr, nc, cur.h));
                } else {
                    pq.offer(new Cell(nr, nc, map[nr][nc]));
                }
            }
        }

        System.out.println(answer);
    }

    static void addBoundary(PriorityQueue<Cell> pq, int r, int c) {
        if (!visited[r][c]) {
            visited[r][c] = true;
            pq.offer(new Cell(r, c, map[r][c]));
        }
    }
}
