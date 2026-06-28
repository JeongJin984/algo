package com.algo.day15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
문제 동전 뒤집기

N²개의 동전이 N행 N열을 이루어 탁자 위에 놓여 있다. 그 중 일부는 앞면(H)이 위를 향하도록 놓여 있고, 나머지는 뒷면(T)이 위를 향하도록 놓여 있다. <그림 1>은 N이 3일 때의 예이다.

H H T  T T H
T H H  T H H
T H T  T H T

<그림 1>

이들 N²개의 동전에 대하여 임의의 한 행 또는 한 열에 놓인 N개의 동전을 모두 뒤집는 작업을 수행할 수 있다. 예를 들어 <그림 1>의 상태에서 첫 번째 열에 놓인 동전을 모두 뒤집으면 <그림 2>와 같이 되고, <그림 2>의 상태에서 첫 번째 행에 놓인 동전을 모두 뒤집으면 <그림 3>과 같이 된다.

T H T          H T H
H H H          H H H
H H T          H H T

<그림 2>       <그림 3>

<그림 3>의 상태에서 뒷면이 위를 향하여 놓인 동전의 개수는 두 개이다. <그림 1>의 상태에서 이와 같이 한 행 또는 한 열에 놓인 N개의 동전을 모두 뒤집는 작업을 계속 수행할 때 뒷면이 위를 향하도록 놓인 동전의 개수를 2개보다 적게 만들 수는 없다.

N²개의 동전들의 초기 상태가 주어질 때, 한 행 또는 한 열에 놓인 N개의 동전을 모두 뒤집는 작업들을 수행하여 뒷면이 위를 향하는 동전 개수를 최소로 하려 한다. 이때의 최소 개수를 구하는 프로그램을 작성하시오.

입력

첫째 줄에 20이하의 자연수 N이 주어진다. 둘째 줄부터 N줄에 걸쳐 N²개 동전들의 초기 상태가 주어진다. 각 줄에는 한 행에 놓인 N개의 동전의 상태가 왼쪽부터 차례대로 주어지는데, 앞면이 위를 향하도록 놓인 경우 H, 뒷면이 위를 향하도록 놓인 경우 T로 표시되며 이들 사이에 공백은 없다.
 */
public class Solution1 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in)
        );

        int n = Integer.parseInt(br.readLine());

        // 각 열에서 T인 행을 비트로 저장
        int[] columnMasks = new int[n];

        for (int row = 0; row < n; row++) {
            String line = br.readLine();

            for (int col = 0; col < n; col++) {
                if (line.charAt(col) == 'T') {
                    columnMasks[col] |= (1 << row);
                }
            }
        }

        int answer = n * n;
        int totalRowCases = 1 << n;

        // 뒤집을 행의 모든 조합 탐색
        for (int rowMask = 0; rowMask < totalRowCases; rowMask++) {
            int tailCount = 0;

            for (int col = 0; col < n; col++) {
                // 선택한 행들을 뒤집은 뒤 해당 열의 T 개수
                int count = Integer.bitCount(columnMasks[col] ^ rowMask);

                // 이 열을 뒤집는 경우와 뒤집지 않는 경우 중 최솟값
                tailCount += Math.min(count, n - count);

                // 이미 현재 최솟값 이상이면 더 계산할 필요 없음
                if (tailCount >= answer) {
                    break;
                }
            }

            answer = Math.min(answer, tailCount);
        }

        System.out.println(answer);
    }
}
