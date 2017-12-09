import com.mongodb.*;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DB {

    public static void writeData(ArrayList<User> existingUsers){
        MongoClientURI uri = new MongoClientURI("mongodb://user1:userAppData@ds129166.mlab.com:29166/csd-207-mini-project");
        MongoClient client = new MongoClient(uri);
        com.mongodb.DB database = client.getDB("csd-207-mini-project");
        DBCollection collection = database.getCollection("appData");
        for(User user : existingUsers){
            BasicDBObject object = new BasicDBObject();
            object.put("name",user.getName());
            object.put("id",""+user.getUserId());
            ArrayList<JSONObject> visited = process(user.getPlacesVisited());
            object.put("placesvisited",visited);
            object.put("botid",""+user.getBotId());
            object.put("battery",""+user.getBot().getBattery());
            object.put("bottype",""+user.getBotType());
            collection.insert(object);
        }
    }

    public static ArrayList<JSONObject> process(ArrayList<Location> placesVisited){
        ArrayList<JSONObject> visited = new ArrayList<JSONObject>();
        for(Location location : placesVisited){
            JSONObject loc = new JSONObject();
            loc.put("place",location.getCityName());
            loc.put("latitude",""+location.getLatitude());
            loc.put("longitude",""+location.getLongitude());
            visited.add(loc);
        }
        return visited;
    }

    public static ArrayList<User> readData() throws IOException{
        MongoClientURI uri = new MongoClientURI("mongodb://user1:userAppData@ds129166.mlab.com:29166/csd-207-mini-project");
        MongoClient client = new MongoClient(uri);
        com.mongodb.DB database = client.getDB("csd-207-mini-project");
        DBCollection collection = database.getCollection("appData");
        ArrayList<User> existingUsers = new ArrayList<User>();
        DBCursor cursor = collection.find();
        while(cursor.hasNext()){
            DBObject object = cursor.next();
            String username = (String) object.get("name");
            int userid = Integer.parseInt((String) object.get("id"));
            int bottype = Integer.parseInt((String) object.get("bottype"));
            User user = new User(username,bottype,userid);
            int botid = Integer.parseInt((String) object.get("botid"));
            int battery = Integer.parseInt((String) object.get("battery"));
            ArrayList<JSONObject> visited = (ArrayList<JSONObject>) object.get("placesvisited");
            ArrayList<Location> visitedPlaces = getVisitesPlaces(visited);
            if(bottype == 1){
                TouristBot bot = new TouristBot(user,bottype,botid);
                user.setBot(bot);
                bot.setBattery(battery);
            }
            else{
                CopBot bot = new CopBot(user,bottype,botid);
                user.setBot(bot);
                bot.setBattery(battery);
            }
            for(Location location : visitedPlaces){
                user.getPlacesVisited().add(location);
            }
            existingUsers.add(user);
            collection.remove(object);
        }
        return existingUsers;
    }

    public static ArrayList<Location> getVisitesPlaces(ArrayList<JSONObject> visited){
        ArrayList<Location> visitedPlaces = new ArrayList<Location>();
        for(int i = 0;i <= visited.size() - 1;i++){
            JSONObject place = (JSONObject) visited.get(i);
            String placeName = (String) place.get("place");
            double latitude = Double.parseDouble((String) place.get("latitude"));
            double longitude = Double.parseDouble((String) place.get("longitude"));
            Location location = new Location(latitude,longitude,placeName);
            visitedPlaces.add(location);
        }
        return visitedPlaces;
    }

}
