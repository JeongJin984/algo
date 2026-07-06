package com.algo.day18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
구슬 탈출 2
문제

스타트링크에서 판매하는 어린이용 장난감 중에서 가장 인기가 많은 제품은 구슬 탈출이다. 구슬 탈출은 직사각형 보드에 빨간 구슬과 파란 구슬을 하나씩 넣은 다음, 빨간 구슬을 구멍을 통해 빼내는 게임이다.

보드의 세로 크기는 N, 가로 크기는 M이고, 편의상 1×1 크기의 칸으로 나누어져 있다. 가장 바깥 행과 열은 모두 막혀져 있고, 보드에는 구멍이 하나 있다. 빨간 구슬과 파란 구슬의 크기는 한 칸을 차지하며, 동시에 움직인다.

기울이기는 상, 하, 좌, 우 네 방향으로 할 수 있다. 한 번 기울이면 구슬은 벽이나 다른 구슬에 막힐 때까지 계속 움직인다. 구슬은 손으로 직접 움직일 수 없다.

빨간 구슬이 구멍에 빠지면 성공이고, 파란 구슬이 구멍에 빠지면 실패이다. 빨간 구슬과 파란 구슬이 동시에 구멍에 빠져도 실패이다.

구슬이 움직이는 동안 두 구슬이 같은 칸을 차지할 수는 없다. 이동 과정에서 같은 위치를 지나가는 것은 가능하지만, 이동이 끝났을 때는 반드시 서로 다른 칸에 위치해야 한다.

보드의 상태가 주어졌을 때, 최소 몇 번 만에 빨간 구슬을 구멍을 통해 빼낼 수 있는지 구하는 프로그램을 작성하시오.

---

입력

첫째 줄에 보드의 세로 크기 N, 가로 크기 M이 주어진다.

다음 N개의 줄에는 보드의 상태가 주어진다.

각 문자는 다음 중 하나이다.

. : 빈 칸
# : 벽
O : 구멍
R : 빨간 구슬
B : 파란 구슬

항상 가장 바깥쪽은 벽이며, 구멍은 정확히 하나 존재한다.

빨간 구슬과 파란 구슬도 각각 정확히 하나씩 존재한다.

제한
3 ≤ N, M ≤ 10

---

출력

빨간 구슬을 구멍으로 빼낼 수 있는 최소 횟수를 출력한다.

단, 10번 이하의 기울이기로 성공할 수 없으면 -1을 출력한다.

---

예제 입력 1
5 5
#####
#..B#
#.#.#
#RO.#
#####
예제 출력 1
1
 */

public class Solution1 {
    static int N,M;
    static char[][] map;
    static int[] hole = new int[2];

    static int[] di = {0, 0, -1, 1};
    static int[] dj = {-1, 1, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        int[] red = new int[2];
        int[] blue = new int[2];

        map = new char[N][M];

        for(int i = 0; i < N; i++) {
            String s = br.readLine();

            for(int j = 0; j < M; j++) {
                map[i][j] = s.charAt(j);

                if(map[i][j] == 'R') {
                    red = new int[] {i, j};
                    map[i][j] = '.';
                } else if(map[i][j] == 'B') {
                    blue = new int[] {i, j};
                    map[i][j] = '.';
                } else if(map[i][j] == 'O') {
                    hole = new int[] {i, j};
                }
            }
        }

        int[][][][] distance = new int[N][M][N][M];

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[] {red[0], red[1], blue[0], blue[1], 0});
        distance[red[0]][red[1]][blue[0]][blue[1]] = 1;

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            if(cur[4] >= 10) continue;

            for(int d = 0; d < 4; d++) {
                int[] next = move(cur, d);

                int rr = next[0];
                int rc = next[1];
                int br2 = next[2];
                int bc = next[3];
                int cnt = next[4];

                if(br2 == hole[0] && bc == hole[1]) continue;

                if(rr == hole[0] && rc == hole[1]) {
                    System.out.println(cnt);
                    return;
                }

                if(distance[rr][rc][br2][bc] != 0) continue;

                distance[rr][rc][br2][bc] = cnt + 1;
                q.offer(next);
            }
        }

        System.out.println(-1);
    }

    static int[] move(int[] p, int d) {
        int[] red = roll(p[0], p[1], d);
        int[] blue = roll(p[2], p[3], d);

        if(red[0] == blue[0] && red[1] == blue[1]) {
            if(red[0] != hole[0] || red[1] != hole[1]) {
                if(red[2] > blue[2]) {
                    red[0] -= di[d];
                    red[1] -= dj[d];
                } else {
                    blue[0] -= di[d];
                    blue[1] -= dj[d];
                }
            }
        }

        return new int[] {red[0], red[1], blue[0], blue[1], p[4] + 1};
    }

    static int[] roll(int i, int j, int d) {
        int count = 0;

        while(true) {
            int ni = i + di[d];
            int nj = j + dj[d];

            if(map[ni][nj] == '#') break;

            i = ni;
            j = nj;
            count++;

            if(map[i][j] == 'O') break;
        }

        return new int[] {i, j, count};
    }
}
