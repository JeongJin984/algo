package com.algo.day24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
멀티탭 스케줄링
문제

기숙사에서 살고 있는 준규는 한 개의 멀티탭을 이용하고 있다. 준규는 키보드, 헤어드라이기, 핸드폰 충전기, 디지털 카메라 충전기 등 여러 개의 전기용품을 사용하면서 어쩔 수 없이 각종 전기용품의 플러그를 뺐다 꽂았다 하는 불편함을 겪고 있다. 그래서 준규는 자신의 생활 패턴을 분석하여, 자기가 사용하고 있는 전기용품의 사용순서를 알아내었고, 이를 기반으로 플러그를 빼는 횟수를 최소화하는 방법을 고안하여 보다 쾌적한 생활환경을 만들고자 한다.

예를 들어 3구(구멍이 세 개 달린) 멀티탭을 쓸 때, 전기용품의 사용 순서가 아래와 같이 주어진다면,

키보드
헤어드라이기
핸드폰 충전기
디지털 카메라 충전기
키보드
헤어드라이기

키보드, 헤어드라이기, 핸드폰 충전기의 플러그를 순서대로 멀티탭에 꽂은 다음 디지털 카메라 충전기 플러그를 꽂기 전에 핸드폰충전기 플러그를 빼는 것이 최적일 것이므로 플러그는 한 번만 빼면 된다.

입력

첫 줄에는 멀티탭 구멍의 개수 N (1 ≤ N ≤ 100)과 전기 용품의 총 사용횟수 K (1 ≤ K ≤ 100)가 정수로 주어진다.

두 번째 줄에는 전기용품의 이름이 K 이하의 자연수로 사용 순서대로 주어진다.

각 줄의 모든 정수 사이는 공백문자로 구분되어 있다.

출력

하나씩 플러그를 빼는 최소의 횟수를 출력하시오.

예제 입력 1
2 7
2 3 2 3 1 2 7
예제 출력 1
2
 */
public class Solution1 {

    static int N;
    static int K;
    static int[] sequence;

    public static void main(String[] args) throws IOException {
        BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        sequence = new int[K];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            sequence[i] = Integer.parseInt(st.nextToken());
        }

        Set<Integer> plugged = new HashSet<>();
        int answer = 0;

        for (int i = 0; i < K; i++) {
            int current = sequence[i];

            // 이미 꽂혀 있는 경우
            if (plugged.contains(current)) {
                continue;
            }

            // 빈 구멍이 있는 경우
            if (plugged.size() < N) {
                plugged.add(current);
                continue;
            }

            int removeTarget = -1;
            int farthestNextIndex = -1;

            for (int device : plugged) {
                int nextIndex = findNextUseIndex(device, i + 1);

                // 앞으로 다시 사용되지 않는 제품
                if (nextIndex == K) {
                    removeTarget = device;
                    break;
                }

                // 다음 사용 시점이 가장 먼 제품
                if (nextIndex > farthestNextIndex) {
                    farthestNextIndex = nextIndex;
                    removeTarget = device;
                }
            }

            plugged.remove(removeTarget);
            plugged.add(current);
            answer++;
        }

        System.out.println(answer);
    }

    private static int findNextUseIndex(int device, int startIndex) {
        for (int i = startIndex; i < K; i++) {
            if (sequence[i] == device) {
                return i;
            }
        }

        return K;
    }
}