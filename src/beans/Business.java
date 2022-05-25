package beans;

import javafx.beans.property.*;

public class Business {
    private final StringProperty name;
    private final StringProperty mobile;
    private final StringProperty phone;
    private final StringProperty address;
    private final StringProperty location;

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getMobile() {
        return mobile.get();
    }

    public StringProperty mobileProperty() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile.set(mobile);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public Business() {
        this.name = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.mobile = new SimpleStringProperty();
        this.phone = new SimpleStringProperty();
        this.location = new SimpleStringProperty();
    }
}
