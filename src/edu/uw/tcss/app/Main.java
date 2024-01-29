package edu.uw.tcss.app;

import edu.uw.tcss.ui.CanvasPanel;

import javax.swing.*;

public class Main {

    public static void main(final String[] theArgs) {

        SwingUtilities.invokeLater(CanvasPanel::createAndShowGui);
    }
}
