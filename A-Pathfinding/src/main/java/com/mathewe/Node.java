package com.mathewe;

import java.awt.Color;

public class Node {

    boolean isWalkable;
    int[] gridLoc;
    int[] coordinates;
    Color currentColour;

    Node parent;
    public int gCost, hCost;
    public int fCost() {
        return gCost + hCost;
    }

    public Node(boolean walkable, int[] location, int[] coordinates, Color colour) {
        this.isWalkable = walkable;
        this.gridLoc = location;
        this.coordinates = coordinates;
        this.currentColour = colour;
    }

    public void setColour(Color colour) {
        this.currentColour = colour;
    }

    public void setWalkable(boolean canWalk) {
        this.isWalkable = canWalk;
    }

    public void setGCost(int gVal) {
        gCost = gVal;
    }

    public void setHCost(int hVal) {
        hCost = hVal;
    }

    public void setParent(Node pNode) {
        parent = pNode;
    }

    public Node getParent() {
        return parent;
    }

    public boolean getWalkable() {
        return this.isWalkable;
    }

    public int[] getLoc() {
        return gridLoc;
    }

    public int[] getCoordinate() {
        return coordinates;
    }

    public Color getColour() {
        return currentColour;
    }
}