public class User {
    private String name;
    private int botId;
    private Bots botAssigned;
    private Location currentLocation;

    public User(String name,int type){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public int getBotId(){
        return botId;
    }

   /* public Bots assignBot(int type){
        if(type == 1){

        }
        else{

        }
    }*/
}
