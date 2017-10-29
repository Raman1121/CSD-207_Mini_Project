import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.Random;

public class displayImage {

    JPanel gui;
    /** Displays the image. */
    JLabel imageCanvas;

    /** Set the image as icon of the image canvas (display it). */
    public void setImage(Image image) {
        imageCanvas.setIcon(new ImageIcon(image));
    }

    public void initComponents() {
        if (gui==null) { 
            gui = new JPanel(new BorderLayout());
            gui.setBorder(new EmptyBorder(5,5,5,5));
            imageCanvas = new JLabel();

            JPanel imageCenter = new JPanel(new GridBagLayout());
            imageCenter.add(imageCanvas);
            JScrollPane imageScroll = new JScrollPane(imageCenter);
            imageScroll.setPreferredSize(new Dimension(300,100));
            gui.add(imageScroll, BorderLayout.CENTER);
        }
    }

    public Container getGui() {
        initComponents();
        return gui;
    }

    public static Image getRandomImage(Random random) {
        int w = 100 + random.nextInt(400);
        int h = 50 + random.nextInt(200);
        BufferedImage bi = new BufferedImage(
                w,h,BufferedImage.TYPE_INT_RGB);

        return bi;
    }

    public static void main(String[] args) throws Exception {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame("Image Viewer");
                // TODO Fix kludge to kill the Timer
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                final displayImage viewer = new displayImage();
                f.setContentPane(viewer.getGui());

                f.pack();
                f.setLocationByPlatform(true);
                f.setVisible(true);

                ActionListener animate = new ActionListener() {

                    Random random = new Random();

                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        viewer.setImage(getRandomImage(random));
                    }
                };
                Timer timer = new Timer(1500,animate);
                timer.start();
            }
        };
        SwingUtilities.invokeLater(r);
    }
}