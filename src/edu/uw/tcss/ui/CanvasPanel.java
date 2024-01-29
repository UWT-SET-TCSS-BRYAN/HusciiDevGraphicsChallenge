package edu.uw.tcss.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class CanvasPanel extends JPanel {

    private static final int SIZE = 50;
    private static final int PREFERRED_CANVAS_SIZE = 500;

    private final Ellipse2D mySource;
    private final Ellipse2D myTarget;
    private final Line2D myConnection;

    private final Shape myArrowHead;


    public CanvasPanel() {
        super();
        mySource = new Ellipse2D.Double(0, 0, SIZE, SIZE);
        myTarget = new Ellipse2D.Double(
                PREFERRED_CANVAS_SIZE / 2.0 - SIZE / 2.0,
                PREFERRED_CANVAS_SIZE / 2.0 - SIZE / 2.0,
                SIZE,
                SIZE);
        myConnection = new Line2D.Double(
                findCenter(mySource),
                findCenter(myTarget));

        myArrowHead = new Line2D.Double(0, 0, 0, 0);

        final DragListener dragListener = new DragListener();
        // add pressed and released
        addMouseListener(dragListener);
        // add dragged. WTH are these different listeners Java?
        addMouseMotionListener(dragListener);
    }

    private Point2D findCenter(final Shape shape) {
        final Rectangle2D bounds = shape.getBounds2D();
        return new Point2D.Double(
                bounds.getX() + bounds.getWidth() / 2.0,
                bounds.getY() + bounds.getHeight() / 2.0);
    }

    private void moveSource(final double dx, final double dy) {
        final Rectangle2D bounds = mySource.getBounds2D();
        mySource.setFrame(
                bounds.getX() - dx,
                bounds.getY() - dy,
                SIZE,
                SIZE);
        myConnection.setLine(
                findCenter(mySource),
                findCenter(myTarget));
        repaint();
    }

    private void adjustArrowHead(/* TODO feel free to add Params */ ) {
        // TODO adjust the arrow head here!
    }


    @Override
    protected void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);
        final Graphics2D g2d = (Graphics2D) graphics;
        // for better graphics display
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(Color.BLACK);
        g2d.setStroke(new BasicStroke(
                SIZE / 4.0f,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER));
        g2d.draw(myConnection);

//      TODO  draw or fill the arrowhead here
        g2d.fill(myArrowHead);
//             --or--
//        g2d.draw(myArrowHead);

        g2d.setPaint(new Color(0, 0, 255, 175));
        g2d.fill(mySource);

        g2d.setPaint(new Color(255, 0, 255, 175));
        g2d.fill(myTarget);


    }

    public static void createAndShowGui() {
        final JFrame frame = new JFrame("Huscii Dev Challenge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        final CanvasPanel newContentPane = new CanvasPanel();
        newContentPane.setOpaque(true); //content panes must be opaque

        frame.setContentPane(newContentPane);

        //Display the window.

        frame.setMinimumSize(new Dimension(PREFERRED_CANVAS_SIZE, PREFERRED_CANVAS_SIZE));
        frame.setPreferredSize(new Dimension(PREFERRED_CANVAS_SIZE, PREFERRED_CANVAS_SIZE));
        frame.pack(); // pack needed AFTER the setPreferredSize call.


        frame.setLocationRelativeTo(null); //Centers the window
        frame.setVisible(true);
    }

    class DragListener extends MouseAdapter {

        private boolean isDragging = false;
        private Point2D mouseLocation;

        @Override
        public void mousePressed(final MouseEvent event) {
            super.mousePressed(event);

            if (mySource.contains(event.getPoint())) {
                mouseLocation = event.getPoint();
                isDragging = true;
            }
        }

        @Override
        public void mouseDragged(final MouseEvent event) {
            super.mouseDragged(event);
            if (isDragging) {
                final Point2D  loc = event.getPoint();
                moveSource(
                        mouseLocation.getX() - loc.getX(),
                        mouseLocation.getY() - loc.getY());
                mouseLocation = loc;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            isDragging = false;
        }
    }

}
