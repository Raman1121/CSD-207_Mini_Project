import java.util.Random;

public abstract class Bots {
    private int botId;
    private int botType;
    private Location currentLocation;

    public int getId(){
        return this.botId;
    }

    public int getBotType(){
        return this.botType;
    }

    public Location getCurrentLocation(){
        return currentLocation;
    }

    public void capturePicture(){

    }

    public void assignBot(int type){
        String initialId = "";
        if(type == 1){
            initialId = "1610110";
            getBotId(initialId);
        }
        else{
            initialId = "1510110";
            getBotId(initialId);
        }
    }

    public void getBotId(String initialId) {
        while (true) {
                Random random = new Random();
                int id = random.nextInt(999) + 1;
                initialId = initialId + id;
                int finalId = Integer.parseInt(initialId);
                if (checkId(finalId)) {
                    continue;
                } else {
                    Main.assignedBotIds.add(finalId);
                    break;
                }
            }
    }

    public boolean checkId(int id){
        for(int assignedId : Main.assignedBotIds){
            if(assignedId == id){
                return true;
            }
        }
        return true;
    }

}
