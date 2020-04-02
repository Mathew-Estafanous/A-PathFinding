package com.mathewe;

import java.util.Arrays;
import java.util.List;

public class MinHeapFinder {
    private int capacity = 10;
    private int startCapacity = capacity;
    private int size = 0;

    Node[] nodes = new Node[capacity];

    public void resetHeap() {
        nodes = new Node[startCapacity];
        capacity = startCapacity;
        size = 0;
    }

    private int getLeftChildIndex(int parentIndex) {
        return 2 * parentIndex + 1;
    }

    private int getRightChildIndex(int parentIndex) {
        return 2 * parentIndex + 2;
    }

    private int getParentIndex(int childIndex) {
        return (childIndex - 1) / 2;
    }

    private boolean hasLeftChild(int index) {
        return getLeftChildIndex(index) < size;
    }

    private boolean hasRightChild(int index) {
        return getRightChildIndex(index) < size;
    }

    private boolean hasParent(int index) {
        return getParentIndex(index) >= 0;
    }

    private Node leftChild(int index) {
        return nodes[getLeftChildIndex(index)];
    }

    private Node rightChild(int index) {
        return nodes[getRightChildIndex(index)];
    }

    private Node parent(int index) {
        return nodes[getParentIndex(index)];
    }

    private void swap(int indexOne, int indexTwo) {
        Node temp = nodes[indexOne];
        nodes[indexOne] = nodes[indexTwo];
        nodes[indexTwo] = temp;
    }

    private void ensureExtraCapacity() {
        if(size == capacity) {
            nodes = Arrays.copyOf(nodes, capacity * 2);
            capacity *= 2;
        }
    }

    public boolean isEmpty() {
        if(size == 0) {
            return true;
        }
        return false;
    }

    public boolean contains(Node compare) {
        List<Node> list = Arrays.asList(nodes);
        if(list.contains(compare)) {
            return true;
        }
        return false;
    }

    public Node[] getOpenNodes() {
        return nodes;
    }

    public Node peek() {
        if(size == 0) throw new IllegalStateException();
        return nodes[0];
    }

    public Node poll() {
        if(size == 0) throw new IllegalStateException();
        Node item = nodes[0];
        nodes[0] = nodes[size - 1];
        size--;
        heapifyDown();
        return item;
    }

    public void add(Node item) {
        ensureExtraCapacity();
        nodes[size] = item;
        size++;
        heapifyUp();
    }

    private void heapifyDown() {
        int index = 0;
        while(hasLeftChild(index)) {
            int smallerChildIndex = getLeftChildIndex(index);
            if(hasRightChild(index) && rightChild(index).fCost() < leftChild(index).fCost()) {
                smallerChildIndex = getRightChildIndex(index);
            }

            if(nodes[index].fCost() < nodes[smallerChildIndex].fCost()) {
                break;
            } else {
                swap(index, smallerChildIndex);
            }
            index = smallerChildIndex;
        }
    }

    private void heapifyUp() {
        int index = size - 1;
        while(hasParent(index) && parent(index).fCost() > nodes[index].fCost() ||
             hasParent(index) && parent(index).fCost() > nodes[index].fCost() && parent(index).hCost > nodes[index].hCost) {
            swap(getParentIndex(index), index);
            index = getParentIndex(index);
        }
    }
}