import se.walkercrou.places.Place;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static ArrayList<Integer> assignedBotIds = new ArrayList<Integer>();
    public static ArrayList<User> existingUsers = new ArrayList<User>();
    public static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        existingUsers = DB.readData();
        while (true) {
            System.out.println("1. Existing User");
            System.out.println("2. New User");
            System.out.println("3. Exit");
            System.out.println("Select an option");
            int choice1 = Integer.parseInt(buffer.readLine());
            switch(choice1){
                case 1:
                    System.out.println("Enter you userId");
                    int id = Integer.parseInt(buffer.readLine());
                    if(User.checkExistingIds(id)){
                        consoleMessage(User.getUserFromId(id));
                    }
                    else{
                        System.out.println("No such user exists");
                        User.newUser();
                    }
                    break;

                case 2:
                    User.newUser();
                    break;

                case 3:
                    break;

                default:
                    System.out.println("Select a valid option");
            }
            if(choice1 == 3){
                DB.writeData(existingUsers);
                break;
            }
        }
    }

    public static void consoleMessage(User currentUser) throws IOException{
       currentUser.getBotAssigned().interact();
    }
}