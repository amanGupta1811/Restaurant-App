package com.acpitzone.gulatikirasoi;

public class item {

   // private int image;
   String name, type, price,id,dishImage;

    public item(String id, String name, String price,String type, String dishImage ){
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.dishImage = dishImage;
    }


    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getPrice(){return price;}

    public String getType(){ return type;}
    public String getImage() {
        return dishImage;
    }
}
