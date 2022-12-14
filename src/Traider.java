public class Traider implements Seller{

    @Override
    public String sell(Goods goods) {
        String result = "";
        if (goods == Goods.POTION){
            result ="Лечебное зелье приобретено";
        }
        return result;
    }
    public enum Goods{
        POTION
    }
}
