import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.model.CityResponse;

import java.io.File;
import java.io.IOException;
import com.maxmind.db.NodeCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.InsightsResponse;

import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.io.BufferedReader;

public class Location {
    private double latitude;
    private double longitude;
    private static String licenseKey = "EOZxoiT22Ix3";

    public Location(double lat,double lon){
        this.latitude = lat;
        this.longitude = lon;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public static Location getCurrentLocation() throws IOException{
        Location currentLocation = getLocation();
        return currentLocation;
    }

    /*public static ArrayList<Location> getNearbyRecommendedLocation(){

    }*/

    public static Location getLocation() throws IOException{
        Location currentLocation = null;
        try(WebServiceClient client = new WebServiceClient.Builder(128415,licenseKey).build()){
            InetAddress ipAddress = InetAddress.getByName(getIpAddress());
            CityResponse response = client.city(ipAddress);
            com.maxmind.geoip2.record.Location location = response.getLocation();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            currentLocation = new Location(latitude,longitude);

        }
        catch(com.maxmind.geoip2.exception.GeoIp2Exception e){
            System.out.println("Exception Location.java "+e);
        }
        return currentLocation;
    }

    public static String getIpAddress(){
        String ipAddress = "";
        try{
            URL ipUrl = new URL("http://bot.whatismyipaddress.com");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(ipUrl.openStream()));
            ipAddress = buffer.readLine().trim();
        }
        catch(IOException e){
            System.out.println("Exception Location.java" + e);
        }
        System.out.println(ipAddress);
        return ipAddress;
    }
}