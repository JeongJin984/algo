package com.algo.day14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
문제



반도체를 설계할 때 n개의 포트를 다른 n개의 포트와 연결해야 할 때가 있다.

예를 들어 왼쪽 그림이 n개의 포트와 다른 n개의 포트를 어떻게 연결해야 하는지를 나타낸다. 하지만 이와 같이 연결을 할 경우에는 연결선이 서로 꼬이기 때문에 이와 같이 연결할 수 없다. n개의 포트가 다른 n개의 포트와 어떻게 연결되어야 하는지가 주어졌을 때, 연결선이 서로 꼬이지(겹치지, 교차하지) 않도록 하면서 최대 몇 개까지 연결할 수 있는지를 알아내는 프로그램을 작성하시오.

입력

첫째 줄에 정수 n(1 ≤ n ≤ 40,000)이 주어진다. 다음 줄에는 차례로 1번 포트와 연결되어야 하는 포트 번호, 2번 포트와 연결되어야 하는 포트 번호, ..., n번 포트와 연결되어야 하는 포트 번호가 주어진다. 이 수들은 1 이상 n 이하이며 서로 같은 수는 없다고 가정하자.

출력

첫째 줄에 최대 연결 개수를 출력한다.

예제 입력 1
6
4 2 6 3 1 5
예제 출력 1
3
 */
public class Solution2 {
    static int N;
    static int[] A;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        A = new int[N];
        for(int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        int[] dp = new int[N];
        int l = 0;
        for(int i=0; i<N; i++) {
            int idx = lowerBound(dp, l, A[i]);
            dp[l] = A[i];
            if(l == idx) {
                l++;
            }
        }

        System.out.println(l);
    }

    public static int lowerBound(int[] arr, int length, int target) {
        int left = 0;
        int right = length;
        while(left < right) {
            int mid = left + (right - left) / 2;

            if(arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}
