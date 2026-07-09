package com.algo.day22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
컬러볼
문제

지훈이는 최근에 즐기는 컴퓨터 게임이 있다. 이 게임은 여러 플레이어가 참여하며, 각 플레이어는 특정한 색과 크기를 가진 자기 공 하나를 조종한다.

각 플레이어의 목표는 자기 공보다 크기가 작고 색이 다른 공을 사로잡아 그 공의 크기만큼 점수를 얻는 것이다.

다른 공을 사로잡은 이후에도 본인의 공의 색과 크기는 변하지 않는다.

각 공에 대해, 그 공이 사로잡을 수 있는 공들의 크기 합을 구하시오.

입력

첫째 줄에 공의 개수 N이 주어진다.

둘째 줄부터 N개의 줄에 걸쳐 각 공의 색 Cᵢ와 크기 Sᵢ가 주어진다.

입력으로 주어지는 공의 순서가 공 번호이다.

출력

N개의 줄에 걸쳐 각 공이 사로잡을 수 있는 공들의 크기 합을 공 번호 순서대로 출력한다.

제한
1 ≤ N ≤ 200,000
1 ≤ Cᵢ ≤ N
1 ≤ Sᵢ ≤ 2,000
예제 입력 1
4
1 10
3 15
1 3
4 8
예제 출력 1
8
21
0
3
 */
public class Solution1 {

    static class Ball implements Comparable<Ball> {
        int idx;
        int color;
        int size;

        Ball(int idx, int color, int size) {
            this.idx = idx;
            this.color = color;
            this.size = size;
        }

        @Override
        public int compareTo(Ball o) {
            return this.size - o.size;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        Ball[] balls = new Ball[N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int color = Integer.parseInt(st.nextToken());
            int size = Integer.parseInt(st.nextToken());
            balls[i] = new Ball(i, color, size);
        }

        Arrays.sort(balls);

        int[] answer = new int[N];
        int[] colorSum = new int[N + 1];

        int totalSum = 0;
        int j = 0;

        for (int i = 0; i < N; i++) {

            while (balls[j].size < balls[i].size) {
                totalSum += balls[j].size;
                colorSum[balls[j].color] += balls[j].size;
                j++;
            }

            answer[balls[i].idx] = totalSum - colorSum[balls[i].color];
        }

        StringBuilder sb = new StringBuilder();
        for (int x : answer) {
            sb.append(x).append('\n');
        }

        System.out.print(sb);
    }
}
