import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static org.junit.Assert.*;

public class SAPTest {

    @Test
    public void diagram_1_Test_1() {
        In in = new In("resources/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = 5;
        int w = 7;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        assertEquals(3, length);
        assertEquals(1, ancestor);
    }

    @Test
    public void diagram_1_Test_2() {
        In in = new In("resources/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = 2;
        int w = 6;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        assertEquals(-1, length);
        assertEquals(-1, ancestor);
    }

    @Test
    public void diagram_2_Test_1() {
        In in = new In("resources/digraph2.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = 0;
        int w = 1;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        assertEquals(1, length);
        assertEquals(0, ancestor);
    }

    @Test
    public void diagram_2_Test_2() {
        In in = new In("resources/digraph2.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = 1;
        int w = 5;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        assertEquals(2, length);
        assertEquals(0, ancestor);
    }


    @Test
    public void diagram_2_Test_3() {
        In in = new In("resources/digraph2.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = 1;
        int w = 3;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        assertEquals(2, length);
        assertEquals(3, ancestor);
    }

    @Test
    public void diagram_3_Test_1() {
        In in = new In("resources/digraph3.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = 2;
        int w = 9;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        assertEquals(-1, length);
        assertEquals(-1, ancestor);
    }


    @Test
    public void diagram_3_Test_2() {
        In in = new In("resources/digraph3.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = 10;
        int w = 7;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        assertEquals(3, length);
        assertEquals(10, ancestor);
    }


    @Test
    public void diagram_3_Test_3() {
        In in = new In("resources/digraph3.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = 14;
        int w = 11;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        assertEquals(2, length);
        assertEquals(11, ancestor);
    }

    @Test
    public void diagram_3_Test_4() {
        In in = new In("resources/digraph3.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = 13;
        int w = 9;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        assertEquals(5, length);
        assertEquals(11, ancestor);
    }

    @Test
    public void diagram_3_Test_5() {
        In in = new In("resources/digraph3.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = 10;
        int w = 14;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        assertEquals(3, length);
        assertEquals(11, ancestor);
    }

    @Test
    public void diagram_3_Test_6() {
        In in = new In("resources/digraph3.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = 8;
        int w = 13;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        assertEquals(5, length);
        assertEquals(8, ancestor);
    }

    @Test
    public void diagram_3_Test_7() {
        In in = new In("resources/digraph3.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = 2;
        int w = 3;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        assertEquals(1, length);
        assertEquals(3, ancestor);
    }


    @Test
    public void diagram_4_Test_1() {
        In in = new In("resources/digraph4.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = 1;
        int w = 4;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        assertEquals(3, length);
        assertEquals(4, ancestor);
    }

    @Test
    public void diagram_4_Test_2() {
        In in = new In("resources/digraph4.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = 1;
        int w = 8;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        assertEquals(2, length);
        assertEquals(8, ancestor);
    }

    @Test
    public void diagram_5_Test_1() {
        In in = new In("resources/digraph5.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = 21;
        int w = 15;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        assertEquals(7, length);
        assertEquals(21, ancestor);
    }

    @Test
    public void diagram_5_Test_2()
    {
        In in = new In("resources/digraph5.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = 19;
        int w = 8;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        assertEquals(5, length);
        assertEquals(9, ancestor);
    }
}