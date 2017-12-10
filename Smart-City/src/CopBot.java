import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class CopBot extends Bots {
    public static int copType = 2;
    Timer timer = new Timer();
    public static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

    public CopBot(User user,int type,int id) throws IOException{
        super();
        super.setBotId(id);
        super.setCurrentUser(user);
        super.setBotType(type);
        try{
            super.setCurrentLocation(Location.getCurrentLocation());}
        catch(Exception e)
        {
            super.setCurrentLocation(null);
        }
        setTimer();
    }

    private void setTimer(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Random random = new Random();
                    int val = random.nextInt(50)+1;
                    if(val > CopBot.super.getBattery()){
                        CopBot.super.setBattery(0);
                    }
                    else {
                        CopBot.super.setBattery(CopBot.super.getBattery() - val);
                    }
                    System.out.println("Bot " + CopBot.super.getId() + " Has low battery new bot will be assigned to you");
                    Main.assignedBotIds.remove(CopBot.super.getId());
                    Bots.assignBot(CopBot.super.getBotType(), CopBot.super.getUser());
                    setTimer();
                }
                catch(IOException e){
                    System.out.println("#TouristBot.java IOException "  + e);
                }
            }
        },super.getBattery()*1000);
    }

    @Override
    public void interact() throws IOException{
        while (true) {
            System.out.println("Bot " + super.getId());
            System.out.println("1. Get suspicious pictures");
            System.out.println("2. Capture Picture");
            System.out.println("3. Exit");
            int choice;
            choice = Integer.parseInt(buffer.readLine());
            switch (choice) {
                case 1:
                    try {
                        JFrame frame = new JFrame();

                        String sourcePath = "/Smart-City/images";
                        String destPath1 = "/home/Desktop/CSD-207_Mini_Project/Smart-City/suspicious";
                        String destPath2 = "/home/Desktop/CSD-207_Mini_Project/Smart-City/non-suspicious";


                        // File representing the folder that you select using a FileChooser
                        final File dir = new File(sourcePath);

                        // array of supported extensions (use a List if you prefer)
                        final String[] EXTENSIONS = new String[]{
                                "gif", "png", "bmp", "jpg" // and other formats you need
                        };
                        // filter to identify images based on their extensions
                        final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

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

                                    frame.pack();
                                    frame.setVisible(true);
                                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


                                } catch (final IOException e) {
                                    System.out.print("BUMMER!!!!!");
                                    e.printStackTrace();
                                }
                            }
                        }
                        else{
                            System.out.println(1);
                        }
                    } catch (Exception e) {

                    }
                    break;
                case 2:
                    super.capturePicture();
                    break;
                case 3:
                    System.exit(0);

            }

        }
    }

}
