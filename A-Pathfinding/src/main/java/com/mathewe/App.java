package com.mathewe;

import javax.swing.SwingUtilities;
/**
 * Hello world!
 */
public final class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
    }
}
