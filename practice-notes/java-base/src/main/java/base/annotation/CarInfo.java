package base.annotation;

@TestClassAnnotation(name = "carInfo", type="car", length = 20)
public class CarInfo {

    @TestAnnotation(name = "品牌", type = "字符串", length = 50)
    private String brand;
    @TestAnnotation(name = "价格", type = "数字", length = 10)
    private int price;
    @TestAnnotation(name = "型号", type = "字符串", length = 10)
    private String model;

    @TestAnnotation(name = "方法", type = "get", length = 111)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

}
