package com.sonbill.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by sonbill on 2017-06-24.
 */

public class MST {
    ArrayList<Line> arLine = new ArrayList<>();
    int amount;

    public MST(ArrayList<Line> arLine, int amount) {
        this.arLine = arLine;
        this.amount = amount;
    }

    public int getMst() {

        int N = amount;
        int len = 0;
        int[] label = new int[N];
        final Vertex[] points = new Vertex[100];

        for (int i = 0; i < N; ++i) {
            label[i] = arLine.get(i).lineId;
        }
        for( int i=0; i<arLine.size(); ++i){
            points[len++] = new Vertex(arLine.get(i).startCircleIndex, arLine.get(i).endCircleIndex, arLine.get(i).value);
        }

        quickSort(points, 0, len-1);

        int nodes = 0, index = 0;
        int cost = 0, tmp = 0;

        while (nodes < N - 1) {
            if (label[points[index].x] != label[points[index].y]) {
                tmp = label[points[index].y];
                for (int i = 0; i < N; ++i) {
                    if (tmp == label[i])
                        label[i] = label[points[index].x];
                }
                cost += points[index].cost;
                nodes++;
            }
            index++;
        }
        return cost;
    }

    public static void quickSort(Vertex[] array, int s, int e) {
        if(s >= e){
            return;
        }
        int i = s + 1;
        int j = e;
        Vertex pivot = array[s];

        while (i <= j) {
            while (i <= e && array[i].cost <= pivot.cost)
                i++;
            while (s + 1 <= j && pivot.cost <= array[j].cost)
                j--;

            if (i <= j) {
                Vertex temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            } else {
                array[s] = array[j];
                array[j] = pivot;
            }
        }
        quickSort(array, s, j - 1 );
        quickSort(array, j + 1, e );
    }

    class Vertex {
        public int x;
        public int y;
        public int cost;

        Vertex(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }
}

