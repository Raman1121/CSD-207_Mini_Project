public class TouristBot extends Bots {
    private static int touristType = 1;

    public TouristBot(){
        super.assignBot(touristType);
        System.out.println("BotId "+super.getId());
    }


}
