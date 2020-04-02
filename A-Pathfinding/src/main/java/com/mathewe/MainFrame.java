package com.mathewe;

import javax.swing.JFrame;
import java.awt.Insets;
import java.awt.BorderLayout;

public class MainFrame extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public int frameHeight = 800 + 30;
    public int frameWidth = 1500 + 20;

    private ControlPanel controlPnl;
    private GridPanel gridPnl;

    public MainFrame() {
        super("A* Pathfinding");

        setLayout(new BorderLayout());
        controlPnl = new ControlPanel(this);
        gridPnl = new GridPanel(this, frameWidth, frameHeight - controlPnl.getTheHeight());

        add(controlPnl, BorderLayout.SOUTH);
        add(gridPnl);

        setSize(frameWidth, frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        controlPnl.position();
    }

    @Override
    public Insets getInsets() {
        return new Insets(30, 10, 0, 10);
    }

    public GridPanel getGridPnl() {
        return gridPnl;
    }

}