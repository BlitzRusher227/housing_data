public class Housing {
    private double price;
    private double area;

    public Housing(double area, double price){
        this.price=price;
        this.area=area;

    }

    public double getPrice(){
        return price;

    }

    public double getArea(){
        return area;
    }

    @Override
    public String toString() {
        return "House [price=" + price + ", area=" + area + "]";    }
}
