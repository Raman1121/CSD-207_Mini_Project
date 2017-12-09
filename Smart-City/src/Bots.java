import java.io.IOException;
import java.util.Random;

public abstract class Bots {
    private int botId;
    private int botType;
    private Location currentLocation;
    private int battery = 100;
    private User user;

    public int getBattery(){
        return battery;
    }

    public void setBattery(int battery){
        this.battery = battery;
    }

    public int getId(){
        return this.botId;
    }

    public int getBotType(){
        return this.botType;
    }

    public User getCurrentUser(){
        return this.user;
    }

    public void setCurrentUser(User user){
        this.user = user;
    }

    public Location getCurrentLocation(){
        return currentLocation;
    }

    public void capturePicture(){
        new TakeSnapshotFromVideo();
    }

    public static void assignBot(int type,User user) throws IOException{
        while (true) {
            Random random = new Random();
            int finalId = random.nextInt(99999) + 1;
            if (checkId(finalId)) {
                continue;
            } else {
                Main.assignedBotIds.add(finalId);
                if(type == TouristBot.touristType){
                    TouristBot tourist = new TouristBot(user,type,finalId);
                    user.setAssignedBot(tourist);
                    user.setBotId(finalId);
                }
                else{
                    CopBot cop = new CopBot(user,type,finalId);
                    user.setAssignedBot(cop);
                    user.setBotId(finalId);
                }
                break;
            }
        }
    }

    public void setCurrentLocation(Location currentLocation){
        this.currentLocation = currentLocation;
    }

    public abstract void interact() throws IOException;

    public static boolean checkId(int id){
        for(int assignedId : Main.assignedBotIds){
            if(assignedId == id){
                return true;
            }
        }
        return false;
    }

    public void setBotType(int type){
        this.botType = type;
    }

    public void setBotId(int id){
        this.botId = id;
    }

}
