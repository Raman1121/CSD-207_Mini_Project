import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.model.CityResponse;

import java.io.File;
import java.io.IOException;
import com.maxmind.db.NodeCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.InsightsResponse;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
            InetAddress ipAddress = InetAddress.getByName("103.27.164.38");
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

    public static InetAddress getIpAddress(){
        InetAddress ipAddress = null;
        try{
            ipAddress = InetAddress.getLocalHost();
        }
        catch(UnknownHostException e){
            System.out.println("Exception Location.java"+e);
        }
        System.out.println(ipAddress.toString());
        return ipAddress;
    }
}