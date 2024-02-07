package com.farms.adhanapp.model;

public class Adhan {

    private String nameSheikh;
    private String pathAdhan;

    public Adhan(String nameSheikh, String pathAdhan) {
        this.nameSheikh = nameSheikh;
        this.pathAdhan = pathAdhan;
    }

    public Adhan() {
    }

    public String getNameSheikh() {
        return nameSheikh;
    }

    public void setNameSheikh(String nameSheikh) {
        this.nameSheikh = nameSheikh;
    }

    public String getPathAdhan() {
        return pathAdhan;
    }

    public void setPathAdhan(String pathAdhan) {
        this.pathAdhan = pathAdhan;
    }
}
