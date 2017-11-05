import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.model.CityResponse;

import java.util.List;
import java.io.IOException;
import com.maxmind.geoip2.record.City;
import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Place;

import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.io.BufferedReader;

public class Location {
    private double latitude;
    private double longitude;
    private String cityName;
    private static String licenseKey = "EOZxoiT22Ix3";

    public Location(double lat,double lon,String name){
        this.latitude = lat;
        this.longitude = lon;
        this.cityName = name;
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

    public static List<Place> getNearbyRecommendedLocation() throws IOException{
        GooglePlaces client = new GooglePlaces("AIzaSyC_kPMIXazwobsCXiLXPehQutiBGGWAjH4");
        Location currentLoc = getCurrentLocation();
        List<Place> places = client.getNearbyPlaces(currentLoc.getLatitude(),currentLoc.getLongitude(),10000);
        return places;
    }

    public static Location getLocation() throws IOException{
        Location currentLocation = null;
        try(WebServiceClient client = new WebServiceClient.Builder(128415,licenseKey).build()){
            InetAddress ipAddress = InetAddress.getByName(getIpAddress());
            CityResponse response = client.city(ipAddress);
            com.maxmind.geoip2.record.Location location = response.getLocation();
            City city = response.getCity();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            currentLocation = new Location(latitude,longitude,city.getName());

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
        return ipAddress;
    }

    public String getCityName(){
        return cityName;
    }

}