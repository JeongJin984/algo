package com.algo.day25;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
문제

하나의 양팔 저울을 이용하여 물건의 무게를 측정하려고 한다. 이 저울의 양 팔의 끝에는 물건이나 추를 올려놓는 접시가 달려 있고, 양팔의 길이는 같다. 또한, 저울의 한쪽에는 저울추들만 놓을 수 있고, 다른 쪽에는 무게를 측정하려는 물건만 올려놓을 수 있다.

무게가 양의 정수인 N개의 저울추가 주어질 때, 이 추들을 사용하여 측정할 수 없는 양의 정수 무게 중 최소값을 구하는 프로그램을 작성하시오.

예를 들어, 무게가 각각 3, 1, 6, 2, 7, 30, 1인 7개의 저울추가 주어졌을 때, 이 추들로 측정할 수 없는 양의 정수 무게 중 최소값은 21이다.

입력
첫째 줄에는 저울추의 개수를 나타내는 양의 정수 N이 주어진다. (1 ≤ N ≤ 1,000)
둘째 줄에는 저울추의 무게를 나타내는 N개의 양의 정수가 빈칸을 사이에 두고 주어진다.
각 추의 무게는 1 이상 1,000,000 이하이다.
출력

첫째 줄에 주어진 추들로 측정할 수 없는 양의 정수 무게 중 최소값을 출력한다.
 */
public class Solution1 {
    static int N;
    static long[] weight;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        weight = new long[N];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            weight[i] = Long.parseLong(st.nextToken());
        }

        Arrays.sort(weight);

        if(weight[0] != 1) {
            System.out.println(1);
            return;
        }

        long curWeight = 1;
        for(int i = 1; i < N; i++) {
            if(curWeight + 1 < weight[i]) {
                System.out.println(curWeight + 1);
                return;
            } else {
                curWeight = curWeight + weight[i];
            }
        }

        System.out.println(curWeight + 1);
    }
}
