package com.acpitzone.gulatikirasoi;

public class coupen {
    String id1, offerName1, offerCode1, offerPercent1, offerMinAmt1;



    public coupen(String id1, String offerName1, String offerCode1, String offerPercent1, String offerMinAmt1) {
        this.id1 = id1;
        this.offerName1 = offerName1;
        this.offerCode1 = offerCode1;
        this.offerPercent1 = offerPercent1;
        this.offerMinAmt1 = offerMinAmt1;
    }

    public String getId1() {
        return id1;
    }

    public String getOfferName1() {
        return offerName1;
    }

    public String getOfferCode1() {
        return offerCode1;
    }

    public String getOfferPercent1() {
        return offerPercent1;
    }

    public String getOfferMinAmt1() {
        return offerMinAmt1;
    }
}
