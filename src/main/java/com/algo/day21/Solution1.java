package com.algo.day21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
불켜기
문제

농부 존은 최근에 N × N개의 방이 있는 거대한 헛간을 새로 지었다. 각 방은 (1, 1)부터 (N, N)까지 번호가 매겨져 있다(2 ≤ N ≤ 100). 어둠을 무서워하는 암소 베시는 최대한 많은 방에 불을 밝히고 싶어한다.

베시는 유일하게 불이 켜져있는 방인 (1, 1)방에서 출발한다. 어떤 방에는 다른 방의 불을 끄고 켤 수 있는 스위치가 달려있다. 예를 들어, (1, 1)방에 있는 스위치로 (1, 2)방의 불을 끄고 켤 수 있다. 베시는 불이 켜져있는 방으로만 이동할 수 있고, 각 방에서는 상하좌우에 인접한 방으로 움직일 수 있다.

베시가 불을 켤 수 있는 방의 최대 개수를 구하시오.

입력

첫째 줄에 방의 한 변의 길이 N과 스위치의 개수 M이 주어진다.

다음 M개의 줄에는 네 정수 x y a b가 주어진다. 이는 (x, y)방에 (a, b)방의 불을 켜고 끌 수 있는 스위치가 있다는 의미이다.

출력

베시가 불을 켤 수 있는 방의 최대 개수를 출력한다.

예제 입력 1
3 4
1 1 1 2
1 2 2 2
2 2 2 3
2 3 3 3
예제 출력 1
5
설명
(1,1)에서 시작한다.
(1,2)의 불을 켠 뒤 이동한다.
(2,2)의 불을 켠다.
(2,3)의 불을 켠다.
(3,3)의 불을 켠다.

총 5개의 방의 불을 켤 수 있다.

예제 입력 2
4 6
1 1 2 1
2 1 2 2
2 2 3 2
3 2 4 2
4 2 4 3
4 3 4 4
예제 출력 2
7
설명

스위치를 차례대로 사용하며 이동 가능한 방을 확장하면 총 7개의 방의 불을 켤 수 있다.
 */
public class Solution1 {
    static int N, M;
    static List<int[]>[][] switches;
    static boolean[][] visited;
    static boolean[][] light;
    static int[] di = {-1, 1, 0, 0};
    static int[] dj = {0, 0, -1, 1};

    static class Switch {
        int i, j;
        boolean lightOn;

        public Switch(int i, int j, boolean lightOn) {
            this.i = i;
            this.j = j;
            this.lightOn = lightOn;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        switches = new ArrayList[N][N];
        visited = new boolean[N][N];
        light = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                switches[i][j] = new ArrayList<>();
            }
        }

        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            switches[x][y].add(new int[]{a, b});
        }

        int answer = 1;
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{0,0});
        visited[0][0] = true;
        light[0][0] = true;
        while(!q.isEmpty()) {
            int[] cur = q.poll();

            for(int[] s : switches[cur[0]][cur[1]]) {
                light[s[0]][s[1]] = true;
                answer++;
            }

            for(int i=0; i<4; i++) {
                int[] next = new int[]{cur[0] + di[i], cur[1] + dj[i]};
                if(next[0] < 0 || next[0] >= N || next[1] < 0 || next[1] >= N) continue;
                if(visited[next[0]][next[1]]) continue;
                if(!light[next[0]][next[1]]) continue;

                visited[next[0]][next[1]] = true;
                q.offer(next);
            }
        }

        System.out.println(answer);
    }
}
