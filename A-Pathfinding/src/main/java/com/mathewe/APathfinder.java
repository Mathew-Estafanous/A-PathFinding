package com.mathewe;

import java.lang.Math;
import java.util.ArrayList;

import java.awt.Color;

public class APathfinder {

    static Node[][] entireGrid;
    static int sizeX, sizeY;
    static Node startNode, endNode;
    static Node currentNode;

    static MinHeapFinder openHeap = new MinHeapFinder();
    static ArrayList<Node> closed = new ArrayList<Node>();

    static Color openColour = Color.MAGENTA;
    static Color closeColour = Color.ORANGE;

    public static void setVariables(Node[][] grid, Node start, Node end, int xSize, int ySize) {
        entireGrid = grid;
        startNode = start;
        endNode = end;
        sizeX = xSize;
        sizeY = ySize;
    }

    public static Node run(Node[][] grid, Node start, Node end, int sizeX, int sizeY) {
        setVariables(grid, start, end, sizeX, sizeY);

        openHeap.add(startNode);
        while(!openHeap.isEmpty()) {
            currentNode = openHeap.poll();

            closed.add(currentNode);
            if(currentNode == endNode) {
                openHeap.resetHeap();
                closed.clear();
                return currentNode.parent;
            }

            if(currentNode != startNode)
                currentNode.setColour(closeColour);

            for(int x = currentNode.gridLoc[0] -1; x <= currentNode.gridLoc[0] + 1;x++)
                for(int y = currentNode.gridLoc[1] -1; y <= currentNode.gridLoc[1] + 1;y++) {
                    if(x < 0 || x >= sizeX || y < 0 || y >= sizeY) { continue;}
                    if(entireGrid[x][y] == currentNode || closed.contains(entireGrid[x][y]) ||
                            isAcrossWalls(currentNode.gridLoc[0] -1, currentNode.gridLoc[1] -1, entireGrid[x][y]) == true) { continue; }

                    Node neighbour = entireGrid[x][y];
                    if(neighbour.getWalkable() == false) { continue; }

                    if(neighbour.gCost > processGCost(neighbour, currentNode)|| !openHeap.contains(neighbour)) {
                        neighbour.setGCost(processGCost(neighbour, currentNode));
                        neighbour.setHCost(processHCost(endNode, neighbour));
                        neighbour.setParent(currentNode);
                        if(!openHeap.contains(neighbour)) {
                            openHeap.add(neighbour);
                            if(neighbour != endNode)
                                neighbour.setColour(openColour);
                        }
                    }
                }
        }
        return null;
    }

    public static boolean isAcrossWalls(int minX, int minY, Node checkNode) {
        int curX = checkNode.getLoc()[0];
        int curY = checkNode.getLoc()[1];
        int directionX = curX + ((curX > minX)? -1:1);
        int directionY = curY + ((curY > minY)? -1:1);

        if(directionX < 0 || directionX >= sizeX || directionY < 0 || directionY >= sizeY) { return false; }

        Node verticalNode = entireGrid[curX][directionY];
        Node horizontalNOde = entireGrid[directionX][curY];
        if(verticalNode.getWalkable() == false && horizontalNOde.getWalkable() == false) {
            return true;
        }
        return false;
    }

    public static int processGCost(Node cur, Node parent) {
        int curX = cur.getLoc()[0];
        int curY = cur.getLoc()[1];
        int parentX = parent.getLoc()[0];
        int parentY = parent.getLoc()[1];

        int addAmount = (curX != parentX && curY != parentY)? 14:10;
        int totalGCost = parent.gCost + addAmount;
        return totalGCost;
    }

    public static int processHCost(Node a, Node b) {
        int xDif = Math.abs(b.getLoc()[0] - a.getLoc()[0]);
        int yDif = Math.abs(b.getLoc()[1] - a.getLoc()[1]);


        int cross = (yDif > xDif)? xDif:yDif;
        int side = ((yDif > xDif)? yDif:xDif) - cross;

        int totalHCost = (14 * cross) + (10 * side);
        return totalHCost;
    }
}