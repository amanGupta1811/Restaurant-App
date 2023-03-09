package com.acpitzone.gulatikirasoi;

public class addressData {

    String id,saveAl, deliveryAl, completeAl, cityAl;

    public addressData(String id, String saveAl, String deliveryAl, String completeAl, String cityAl) {
        this.id = id;
        this.saveAl = saveAl;
        this.deliveryAl = deliveryAl;
        this.completeAl = completeAl;
        this.cityAl = cityAl;
    }

    public String getIdA() {
        return id;
    }

    public String getSaveAl() {
        return saveAl;
    }

    public String getDeliveryAl() {
        return deliveryAl;
    }

    public String getCompleteAl() {
        return completeAl;
    }

    public String getCityAl() {
        return cityAl;
    }
}
