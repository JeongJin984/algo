package com.algo.day8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
문제: 장을 다 보고 집으로

민수는 동네 사람들의 장보기 심부름을 맡았다.

동네에는 N개의 장소가 있고, 장소 사이에는 M개의 양방향 도로가 있다. 장소는 1번부터 N번까지 번호가 붙어 있으며, 민수의 집은 1번 장소에 있다.

민수가 맡은 심부름은 총 K개이다. 각 심부름은 다음과 같이 이루어진다.

지정된 장소에서 물건을 받은 뒤, 다른 지정된 장소에 가져다준다.

민수의 가방에는 물건을 최대 C개까지만 넣을 수 있다. 이미 전달을 끝낸 물건은 가방에서 사라지므로 자리를 차지하지 않는다.

민수는 집에서 출발해 모든 심부름을 끝낸 뒤 다시 집으로 돌아오려고 한다. 이동 시간을 최소로 만들어 보자.

이동 규칙

각 심부름 i에는 물건을 받는 장소 S_i와 물건을 전달할 장소 E_i가 주어진다.

민수는 다음 규칙에 따라 움직인다.

물건은 반드시 S_i에서 받은 뒤 E_i에 전달해야 한다.
가방에 들어 있는 물건의 수는 항상 C개 이하여야 한다.
물건을 받거나 전달하는 데에는 시간이 걸리지 않는다.
같은 장소에서 여러 물건을 연달아 받거나 전달할 수 있다.
한 장소와 도로를 여러 번 지나가도 된다.
같은 장소가 여러 심부름의 출발지나 도착지로 사용될 수 있다.

모든 심부름을 끝내고 1번 장소로 돌아오는 데 필요한 최소 시간을 구하여라.

불가능하다면 -1을 출력한다.

입력

첫 번째 줄에 장소의 수 N, 도로의 수 M, 심부름의 수 K, 가방에 넣을 수 있는 물건의 최대 개수 C가 주어진다.

N M K C

다음 M개의 줄에는 도로의 정보가 주어진다.

A B T

이는 A번 장소와 B번 장소를 연결하는 양방향 도로가 있으며, 이 도로를 지나는 데 T분이 걸린다는 뜻이다.

다음 K개의 줄에는 심부름의 정보가 주어진다.

S_i E_i

이는 i번 물건을 S_i번 장소에서 받아 E_i번 장소에 전달해야 한다는 뜻이다.

출력

모든 심부름을 끝내고 집으로 돌아오는 데 필요한 최소 시간을 출력한다.

심부름을 모두 끝낼 수 없다면 -1을 출력한다.

제한
2 ≤ N ≤ 100,000
1 ≤ M ≤ 200,000
1 ≤ K ≤ 9
1 ≤ C ≤ K
1 ≤ A, B, S_i, E_i ≤ N
A ≠ B
S_i ≠ E_i
1 ≤ T ≤ 10^9
같은 두 장소를 연결하는 도로가 여러 개 존재할 수 있다.
정답은 64비트 정수 범위 안에 있다.
예제 입력 1
6 7 2 2
1 2 2
2 3 2
3 4 2
4 5 2
5 6 2
1 6 10
2 5 3
2 6
3 5
예제 출력 1
17
예제 설명 1

민수는 다음과 같이 움직일 수 있다.

집에서 2번 장소로 이동한다. 2분
첫 번째 물건을 받는다.
3번 장소로 이동한다. 2분
두 번째 물건을 받는다.
5번 장소로 이동해 두 번째 물건을 전달한다. 4분
6번 장소로 이동해 첫 번째 물건을 전달한다. 2분
1번 장소로 돌아온다. 7분

총 이동 시간은 17분이다.

예제 입력 2
4 2 1 1
1 2 3
3 4 1
2 4
예제 출력 2
-1

2번 장소에서 물건을 받을 수는 있지만, 물건을 전달해야 하는 4번 장소로 이동할 수 없다.
 */
public class Solution1 {

    static final long INF = Long.MAX_VALUE / 4;

    static int N, M, K, C;

    static List<Edge>[] graph;

    // pickup[i]   : i번 심부름의 물건을 받는 장소
    // delivery[i] : i번 심부름의 물건을 전달하는 장소
    static int[][] errand;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in)
        );

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        graph = new ArrayList[N + 1];

        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph[a].add(new Edge(b, cost));
            graph[b].add(new Edge(a, cost));
        }

        errand = new int[K][2];

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            errand[i] = new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
        }

        List<long[]> distances = new ArrayList<>();
        distances.add(dijkstra(new Node(1, 0)));
        for(int i=0; i<K; i++) {
            distances.add(dijkstra(new Node(errand[i][0], 0)));
        }
        for(int i=0; i<K; i++) {
            distances.add(dijkstra(new Node(errand[i][1], 0)));
        }
    }

    static long[] dijkstra(Node start) {
        long[] distance = new long[N + 1];
        Arrays.fill(distance, INF);

        PriorityQueue<Node> pq = new PriorityQueue<>(
            Comparator.comparingLong(node -> node.cost)
        );

        distance[start.idx] = 0;
        pq.offer(start);

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (distance[current.idx] < current.cost) {
                continue;
            }

            for (Edge edge : graph[current.idx]) {
                long nextCost = current.cost + edge.cost;

                if (nextCost < distance[edge.to]) {
                    distance[edge.to] = nextCost;
                    pq.offer(new Node(edge.to, nextCost));
                }
            }
        }

        return distance;
    }

    static class Edge {
        int to;
        int cost;

        public Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    static class Node {
        int idx;
        long cost;

        public Node(int idx, long cost) {
            this.idx = idx;
            this.cost = cost;
        }
    }
}