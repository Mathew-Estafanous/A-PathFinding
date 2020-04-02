package com.mathewe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

public class GridPanel extends JPanel implements MouseInputListener, KeyListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public MainFrame frame;
    public int gridHeight;
    public int gridWidth;

    int gridSizeX;
    int gridSizeY;

    int tileSize = 25;
    int tileSpacing = 1;

    Color walkColour = Color.white;
    Color notWalkColour = Color.black;
    Color startColour = Color.red;
    Color endColour = Color.blue;
    Color pathColour = Color.CYAN;

    public Node[][] grid;
    Node startNode, endNode, path;
    char currentKey = (char) 0;
    boolean hasRun;

    public GridPanel(MainFrame frame, int width, int height) {
        this.frame = frame;
        setBackground(Color.black);

        gridHeight = height;
        gridWidth = width;

        gridSizeX = gridWidth / tileSize;
        gridSizeY = gridHeight / tileSize;
        System.out.println(gridSizeX);
        grid = new Node[gridSizeX][gridSizeY];

        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);

        initializeGrid();
    }

    public void initializeGrid() {
        for (int x = 0; x < gridSizeX; x++) {
            for (int y = 0; y < gridSizeY; y++) {
                int xCoordinate = x * tileSize;
                int yCoordinate = y * tileSize;
                int[] location = { x, y };
                int[] coordinates = { xCoordinate, yCoordinate };

                grid[x][y] = new Node(true, location, coordinates, walkColour);
            }
        }
        hasRun = false;
        startNode = null;
        endNode = null;
        repaint();
    }

    private void interactWithGrid(MouseEvent e) {
        int gridX, gridY;
        int mouseX, mouseY;
        if(hasRun == true) { return; }

        if (SwingUtilities.isLeftMouseButton(e)) {
            // Check if it is an "s"
            if (currentKey == 's') {
                mouseX = e.getX();
                mouseY = e.getY();

                gridX = getGridLocation(mouseX);
                gridY = getGridLocation(mouseY);
                if (startNode != null) {
                    startNode.setColour(walkColour);
                }
                startNode = grid[gridX][gridY];
                startNode.setColour(startColour);
            } else if (currentKey == 'e') {
                mouseX = e.getX();
                mouseY = e.getY();

                gridX = getGridLocation(mouseX);
                gridY = getGridLocation(mouseY);
                if (endNode != null) {
                    endNode.setColour(walkColour);
                }
                endNode = grid[gridX][gridY];
                endNode.setColour(endColour);
            } else {
                mouseX = e.getX();
                mouseY = e.getY();

                gridX = getGridLocation(mouseX);
                gridY = getGridLocation(mouseY);
                Node selectedNode = grid[gridX][gridY];
                if (selectedNode == startNode || selectedNode == endNode) {
                    return;
                }

                selectedNode.setWalkable(false);
                selectedNode.setColour(notWalkColour);
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            mouseX = e.getX();
            mouseY = e.getY();

            gridX = getGridLocation(mouseX);
            gridY = getGridLocation(mouseY);
            Node clickedNode = grid[gridX][gridY];

            if (clickedNode.getWalkable() == false) {
                clickedNode.setWalkable(true);
                clickedNode.setColour(walkColour);
            }
        }
        repaint();
    }

    private int getGridLocation(int location) {
        int gridLoc = location / tileSize;
        int rounding = ((location / tileSize) - gridLoc > 0) ? 1 : 0;

        gridLoc += rounding;
        return gridLoc;
    }

    private void analyzePath(Node path) {
        Node curNode = path;
        while (curNode.getParent() != null) {
            curNode.setColour(pathColour);
            curNode = curNode.getParent();
        }
        repaint();
        return;
    }

    public String beginProcess() {
        if(endNode == null || startNode == null) { return "Start/End Node not Set"; }

        hasRun = true;
        path = APathfinder.run(grid, startNode, endNode, gridSizeX, gridSizeY);
        if(path == null) {
            System.out.println("NO PATH!");
            return "No Path Found";
        }
        analyzePath(path);
        return "Clear To Start Again";
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (Node[] nodex : grid) {
            for (Node n : nodex) {
                g2.setColor(Color.black);
                Stroke oldStroke = g2.getStroke();
                g2.setStroke(new BasicStroke(tileSpacing));
                g2.drawRect(n.coordinates[0], n.coordinates[1], tileSize, tileSize);

                g2.setColor(n.currentColour);
                g2.setStroke(oldStroke);
                int size = tileSize - (2 * tileSpacing);
                g2.fillRect(n.coordinates[0] + tileSpacing, n.coordinates[1] + tileSpacing, size, size);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        interactWithGrid(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        interactWithGrid(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        interactWithGrid(e);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        currentKey = e.getKeyChar();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentKey = (char) 0;
    }
}