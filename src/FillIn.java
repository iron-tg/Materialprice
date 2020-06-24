import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FillIn {

    private SimpleStringProperty name;
    private SimpleStringProperty scale;
    private SimpleStringProperty price;
    private SimpleStringProperty no;
    private SimpleStringProperty unit;
    private SimpleStringProperty sumPrice;


    public FillIn(){}

    public FillIn(String no, String name, String unit, String price, String scale, String sumPrice){
        this.name = new SimpleStringProperty(name);
        this.scale = new SimpleStringProperty(scale);
        this.price = new SimpleStringProperty(price);
        this.unit = new SimpleStringProperty(unit);
        this.no = new SimpleStringProperty(no);
        this.sumPrice = new SimpleStringProperty(sumPrice);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getScale() {
        return scale.get();
    }

    public SimpleStringProperty scaleProperty() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale.set(scale);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getNo() {
        return no.get();
    }

    public SimpleStringProperty noProperty() {
        return no;
    }

    public void setNo(String no) {
        this.no.set(no);
    }

    public String getUnit() {
        return unit.get();
    }

    public SimpleStringProperty unitProperty() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public String getSumPrice() {
        return sumPrice.get();
    }

    public void setSumPrice(String sumPrice) {
        this.sumPrice.set(sumPrice);
    }

    public SimpleStringProperty sumPriceProperty() {
        return sumPrice;
    }
}
