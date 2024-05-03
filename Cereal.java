public class Cereal{

    private String name;
    private boolean isCold;
    private double sugar;
    private double rating;

    
    public Cereal(String name, boolean isCold, double sugar, double rating) {
        this.name = name;
        this.isCold = isCold;
        this.sugar = sugar;
        this.rating = rating;
    }
    
    public String getName() {
        return name;
    }
    public boolean isCold() {
        return isCold;
    }
    public double getSugar() {
        return sugar;
    }
    public double getRating() {
        return rating;
    }


    @Override
    public String toString() {
        return "Cereal [name=" + name + ", isCold=" + isCold + ", sugar=" + sugar + ", rating=" + rating + "]";
    }

}
