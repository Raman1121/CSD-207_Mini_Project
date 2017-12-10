import se.walkercrou.places.Place;
import se.walkercrou.places.Review;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.*;
import java.util.Timer;

public class TouristBot extends Bots {
    public static int touristType = 1;
    public static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
    private  ArrayList<Place> nearbyPlaces  = new ArrayList<Place>();
    private Timer timer = new Timer();

    public TouristBot(User user,int type,int id) throws IOException{
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
                    if(val > TouristBot.super.getBattery()){
                        TouristBot.super.setBattery(0);
                    }
                    else {
                        TouristBot.super.setBattery(TouristBot.super.getBattery() - val);
                    }
                    System.out.println("Bot " + TouristBot.super.getId() + " Has low battery new bot will be assigned to you");
                    Main.assignedBotIds.remove(TouristBot.super.getId());
                    Bots.assignBot(TouristBot.super.getBotType(), TouristBot.super.getUser());
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
        while(true) {
            System.out.println("Bot " + super.getId());
            System.out.println("1. Get current location");
            System.out.println("2. Capture Picture");
            System.out.println("3. Get Nearby Places");
            System.out.println("4. Exit");
            int choice;
            choice = Integer.parseInt(buffer.readLine());
            switch (choice) {
                case 1:
                    Location current = super.getCurrentLocation();
                    super.getCurrentUser().getPlacesVisited().add(current);
                    System.out.println("Latitude: " + current.getLatitude());
                    System.out.println("Longitude: " + current.getLongitude());
                    break;

                case 2:
                    super.capturePicture();
                    break;

                case 3:
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                nearbyPlaces = (ArrayList<Place>) Location.getNearbyRecommendedLocation();
                            } catch (IOException e) {
                                System.out.println("Exception TouristBot.java " + e);
                            }
                        }
                    };
                    Thread thread = new Thread(runnable);
                    thread.run();
                    if (nearbyPlaces.size() > 0) {
                        System.out.println("Select a place to get more info about it");
                        placeInfo(nearbyPlaces);
                    }
                    break;

                case 4:
                    break;

                default:
                    System.out.println("Select a valid option");
            }
            if(choice == 4){
                break;
            }
        }
    }

    private void placeInfo(ArrayList<Place> nearbyPlaces) throws IOException{
        int count = 1;
        for(Place place : nearbyPlaces){
            System.out.println(count + ". " + place.getName());
            count++;
        }
        System.out.println("Select your option");
        int option;
        option = Integer.parseInt(buffer.readLine());
        if(option < nearbyPlaces.size()){
            Place selectedPlace = nearbyPlaces.get(option);
            System.out.print(selectedPlace.getName());
            System.out.println(selectedPlace.getAddress());
            System.out.println(selectedPlace.getVicinity());
            ArrayList<Review> reviews = (ArrayList<Review>) selectedPlace.getReviews();
            if(reviews.size() > 0){
                for(int i = 0;i <= reviews.size()-1 ;i++){
                    System.out.println(reviews.get(i).getText());
                }
            }
            else{
                System.out.println("No reviews present");
            }
        }
        else{
            System.out.println("Enter a valid index");
        }
    }

}
