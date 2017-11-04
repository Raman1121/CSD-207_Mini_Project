import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class loadAndDisplay implements ActionListener{

    public void actionPerformed(ActionEvent event){
        
    }

    // File representing the folder that you select using a FileChooser
    static final File dir = new File("/home/raman/IdeaProjects/Java_Mini_Project/images");

    // array of supported extensions (use a List if you prefer)
    static final String[] EXTENSIONS = new String[]{
            "gif", "png", "bmp", "jpg" // and other formats you need
    };
    // filter to identify images based on their extensions
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };

    public static void main(String[] args) {

        if (dir.isDirectory()) { // make sure it's a directory
            for (final File f : dir.listFiles(IMAGE_FILTER)) {
                BufferedImage img = null;

                try {
                    img = ImageIO.read(f);

                    // you probably want something more involved here
                    // to display in your UI
                    System.out.println("image: " + f.getName());
                    System.out.println(" width : " + img.getWidth());
                    System.out.println(" height: " + img.getHeight());
                    System.out.println(" size  : " + f.length());
                    JFrame frame = new JFrame();
                    JButton button1 = new JButton("Yes");
                    JButton button2 = new JButton("No");
                    frame.getContentPane().setLayout(new FlowLayout());
                    frame.getContentPane().add(new JLabel(new ImageIcon(img)));
                    frame.add(button1);
                    frame.add(button2);
                    frame.pack();
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } catch (final IOException e) {
                    // handle errors here
                }
            }
        }
    }
}
