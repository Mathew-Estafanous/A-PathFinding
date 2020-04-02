package com.mathewe;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private MainFrame frame;
    private int controlHeight = 50;
    private int controlWidth = 900;

    private JButton runBtn;
    private JButton clearBtn;
    private JLabel resultlbl;
    private JLabel strtLbl;
    private JLabel endLbl;

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        Dimension controlSize = new Dimension(controlWidth, controlHeight);
        setPreferredSize(controlSize);
        setBackground(Color.lightGray);

        runBtn = new JButton("Run");
        runBtn.addActionListener(this);
        runBtn.setFocusable(false);

        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(this);
        clearBtn.setFocusable(false);

        strtLbl = new JLabel("[S + Click]: Start Node");
        endLbl = new JLabel("[E + Click]: End Node");
        resultlbl = new JLabel("Start Running:");

        add(resultlbl);
        add(strtLbl);
        add(endLbl);
        add(runBtn);
        add(clearBtn);
    }

    public void position() {
        setLayout(null);
        strtLbl.setBounds(10, 5, 175, 20);
        endLbl.setBounds(10, 25, 175, 20);
        resultlbl.setBounds(250, 15, 175, 20);
        runBtn.setBounds(155, 3, 75, 20);
        clearBtn.setBounds(155, 23, 75, 20);
    }

    public int getTheHeight() {
        return controlHeight;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        GridPanel gridPnl = frame.getGridPnl();
        if (action == "Run") {
            String result = gridPnl.beginProcess();
            resultlbl.setText(result);
        } else {
            gridPnl.initializeGrid();
            resultlbl.setText("Start Running:");
        }
    }
}