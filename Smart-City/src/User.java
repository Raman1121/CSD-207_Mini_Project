import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class User {
    private String name;
    private int botId;
    private int botType;
    private Bots botAssigned;
    private int userId;
    private Location currentLocation;

    private static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

    public User(String name,int type,int id){
        this.name = name;
        this.userId = id;
        this.botType = type;
    }

    public String getName(){
        return name;
    }

    public int getBotId(){
        return botId;
    }

    public void setAssignedBot(Bots bot){
        this.botAssigned = bot;
    }

    public int getBotType(){
        return this.botType;
    }

    public void setBotId(int id){
        this.botId = id;
    }

    public Bots getBotAssigned(){
        return botAssigned;
    }

    public int getUserId(){
        return userId;
    }

    public static void newUser() throws IOException{
        String name;
        int choice;
        while(true) {
            System.out.println("Enter your name");
            name = buffer.readLine();
            System.out.println("Which type of bot do you want");
            System.out.println("1. Tourist Bot");
            System.out.println("2. Cop Bot");
            System.out.println("3. Exit");
            choice = Integer.parseInt(buffer.readLine());
            if (choice == 1 || choice == 2) {
                User user = new User(name, choice,User.generateUserId());
                System.out.println("Your id is " + user.getUserId());
                Main.existingUsers.add(user);
                Bots.assignBot(choice, user);
                System.out.println("Bot has been assigned id: " + user.getBotId());
                user.getBotAssigned().interact();
                return;
            }
            else if (choice == 3) {
                return;
            } else {
                System.out.println("Select a valid choice");
                continue;
            }
        }
    }

    public static int generateUserId(){
        int id = 0;
        while(true){
            Random random = new Random();
            id = random.nextInt(9999)+1;
            if(checkExistingIds(id)){
                continue;
            }
            else{
                break;
            }
        }
        return id;
    }

    public static boolean checkExistingIds(int id){
        for(User user : Main.existingUsers){
            if(user.getUserId() == id){
                return true;
            }
        }
        return false;
    }

    public static User getUserFromId(int id){
        for(int i = 0;i <= Main.existingUsers.size()-1;i++){
            if(Main.existingUsers.get(i).getUserId() == id){
                return Main.existingUsers.get(i);
            }
        }
        return null;
    }

}
