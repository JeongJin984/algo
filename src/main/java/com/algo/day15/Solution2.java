package com.algo.day15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
벽 부수고 이동하기 3
시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2초	512 MB	14807	3634	2409	23.685%
문제

N×M의 행렬로 표현되는 맵이 있다. 맵에서 0은 이동할 수 있는 곳을 나타내고, 1은 이동할 수 없는 벽이 있는 곳을 나타낸다. 당신은 (1,1)에서 (N,M)의 위치까지 이동하려 하는데, 이때 최단 경로로 이동하려 한다. 최단 경로는 맵에서 가장 적은 개수의 칸을 지나는 경로를 말하는데, 이때 시작하는 칸과 끝나는 칸도 포함해서 센다. 이동하지 않고 같은 칸에 머물러 있는 경우도 가능하다. 이 경우도 방문한 칸의 개수가 하나 늘어나는 것으로 생각해야 한다.

이번 문제에서는 낮과 밤이 번갈아가면서 등장한다. 가장 처음에 이동할 때는 낮이고, 한 번 이동할 때마다 낮과 밤이 바뀌게 된다. 이동하지 않고 같은 칸에 머무르는 경우에도 낮과 밤이 바뀌게 된다.

만약 이동하는 도중에 벽을 부수고 이동하는 것이 좀 더 경로가 짧아진다면, 벽을 K개까지 부수고 이동해도 된다. 단, 벽은 낮에만 부술 수 있다.

한 칸에서 이동할 수 있는 칸은 상하좌우로 인접한 칸이다.

맵이 주어졌을 때, 최단 경로를 구해 내는 프로그램을 작성하시오.

입력

첫째 줄에 N(1≤N≤1,000), M(1≤M≤1,000), K(1≤K≤10)가 주어진다. 다음 N개의 줄에 M개의 숫자로 맵이 주어진다. (1,1)과 (N,M)은 항상 0이라고 가정하자.

출력

첫째 줄에 최단 거리를 출력한다. 불가능할 때는 -1을 출력한다.
 */
public class Solution2 {
    static int N;
    static int M;
    static int K;

    static int[][] map;

    static int[] di = new int[] {0, 0, 1, -1};
    static int[] dj = new int[] {1, -1, 0, 0};

    static final int DAY = 0;
    static final int NIGHT = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new  StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for(int i = 0; i < N; i++){
            String s = br.readLine();
            for(int j = 0; j < M; j++){
                map[i][j] = s.charAt(j) - '0';
            }
        }

        int[][][][] distance = new int[N][M][K+1][2];

        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                for(int k = 0; k <= K; k++){
                    distance[i][j][k][DAY] = Integer.MAX_VALUE;
                    distance[i][j][k][NIGHT] = Integer.MAX_VALUE;
                }
            }
        }

        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{0,0,0,0,1}); // i좌표, j좌표, 벽갯수, 밤낮, 거리
        distance[0][0][0][DAY] = 1;
        while(!q.isEmpty()){
            int[] cur = q.poll();

            if(cur[0] == N-1 && cur[1] == M-1) {
                System.out.println(cur[4]);
                return;
            }

            for(int i = 0; i<4; i++) {
                int[] next = new int[] {cur[0] + di[i], cur[1] + dj[i], cur[2], (cur[3] + 1) % 2, cur[4] + 1};

                if(next[0]<0 || next[0]>=N || next[1]<0 || next[1]>=M) continue;
                if(map[next[0]][next[1]] == 0) {
                    if(distance[next[0]][next[1]][next[2]][next[3]] <= next[4]) continue;
                    distance[next[0]][next[1]][next[2]][next[3]] = next[4];
                    q.offer(next);
                } else {
                    if(cur[2] >= K) continue;
                    if(cur[3] == DAY) {
                        next[2]++;
                        if(distance[next[0]][next[1]][next[2]][next[3]] <= next[4]) continue;
                        distance[next[0]][next[1]][next[2]][next[3]] = next[4];
                        q.offer(next);
                    } else  {
                        next = new int[] {cur[0], cur[1], cur[2], (cur[3] + 1) % 2,  cur[4] + 1};
                        if(distance[next[0]][next[1]][next[2]][next[3]] <= next[4]) continue;
                        distance[next[0]][next[1]][next[2]][next[3]] = next[4];
                        q.offer(next);
                    }
                }
            }
        }
        System.out.println(-1);
    }
}
