package com.algo.day16;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

/*
놀이 공원
시간 제한: 2초
메모리 제한: 128 MB
문제

N명의 아이들이 한 줄로 줄을 서서 놀이공원에서 1인승 놀이기구를 기다리고 있다. 이 놀이공원에는 총 M종류의 1인승 놀이기구가 있으며, 1번부터 M번까지 번호가 매겨져 있다.

모든 놀이기구는 각각 정해진 운행 시간이 정해져 있어서, 운행 시간이 지나면 탑승하고 있던 아이는 내리게 된다. 놀이 기구가 비어 있으면 현재 줄에서 가장 앞에 서 있는 아이가 빈 놀이 기구에 탑승한다. 만일 여러 개의 놀이기구가 동시에 비어 있으면, 더 작은 번호가 적혀 있는 놀이기구를 먼저 탑승한다고 한다.

놀이기구가 모두 비어 있는 상태에서 첫 번째 아이가 놀이기구에 탑승한다고 할 때, 줄의 마지막 아이가 타게 되는 놀이기구의 번호를 구하는 프로그램을 작성하시오.

입력

첫째 줄에 N(1 ≤ N ≤ 2,000,000,000)과 M(1 ≤ M ≤ 10,000)이 빈칸을 사이에 두고 주어진다. 둘째 줄에는 각 놀이기구의 운행 시간을 나타내는 M개의 자연수가 순서대로 주어진다. 운행 시간은 1 이상 30 이하의 자연수이며, 단위는 분이다.

출력

첫째 줄에 마지막 아이가 타게 되는 놀이기구의 번호를 출력한다.

예제 입력 1
22 5
1 2 3 4 5
예제 출력 1
4
 */
public class Solution2 {
    static int N, M;
    static int[] times;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        if (N <= M) {
            System.out.println(N);
            return;
        }

        st = new StringTokenizer(br.readLine());
        times = new int[M];
        for(int i = 0; i < M; i++) {
            times[i] = Integer.parseInt(st.nextToken());
        }

        long l = 0;
        long r = 30L * N;

        while(l < r) {
            long mid = l + (r - l) / 2;
            long count = 0;

            for(int time : times) {
                count += mid / time;
            }

            if(count >= N) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        long targetTime = l;

        long countBefore = M;

        for (int time : times) {
            countBefore += (targetTime - 1) / time;
        }

        for (int i = 0; i < M; i++) {
            if (targetTime % times[i] == 0) {
                countBefore++;

                if (countBefore == N) {
                    System.out.println(i + 1);
                    break;
                }
            }
        }
    }
}
