import java.io.IOException;

public class CopBot extends Bots {
    public static int copType = 2;

    public CopBot(User user,int type,int id) throws IOException{
        super();
        super.setBotId(id);
        super.setCurrentUser(user);
        super.setBotType(type);
        super.setCurrentLocation(Location.getCurrentLocation());
    }

    @Override
    public void interact(){

    }

}
