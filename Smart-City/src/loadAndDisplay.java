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
import java.nio.file.Files;
import java.nio.file.Paths;

public class loadAndDisplay implements ActionListener{

    static JFrame  frame = new JFrame();
    static JButton button1 = new JButton("Yes");
    static JButton button2 = new JButton("No");

    static String sourcePath =  "/home/Desktop/CSD-207_Mini_Project/Smart-City/images";
    static String destPath1 = "/home/Desktop/CSD-207_Mini_Project/Smart-City/Suspicious";
    static String destPath2 = "/home/Desktop/CSD-207_Mini_Project/Smart-City/Non-Suspicious";



    // File representing the folder that you select using a FileChooser
    static final File dir = new File(sourcePath);

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

    public void go(){
        if (dir.isDirectory()) {                // make sure it's a directory
            for (final File f : dir.listFiles(IMAGE_FILTER)) {

                BufferedImage img = null;
                try {
                    img = ImageIO.read(f);

                    System.out.println("image: " + f.getName());
                    System.out.println(" width : " + img.getWidth());
                    System.out.println(" height: " + img.getHeight());
                    System.out.println(" size  : " + f.length());

                    frame.getContentPane().setLayout(new FlowLayout());
                    frame.getContentPane().add(new JLabel(new ImageIcon(img)));
                    frame.add(button1);
                    frame.add(button2);
                    frame.pack();
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    button1.addActionListener(this);
                    button2.addActionListener(this);

                } catch (final IOException e) {
                    System.out.print("BUMMER!!!!!");
                    e.printStackTrace();
                }
            }
        }
    }

    public void actionPerformed(ActionEvent event){

        if(event.getSource() == button1){
            try {
                Files.move(Paths.get(sourcePath), Paths.get(destPath2));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(event.getSource() == button2){
            try {
                Files.move(Paths.get(sourcePath), Paths.get(destPath1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        loadAndDisplay ld = new loadAndDisplay();
        ld.go();
    }

}
