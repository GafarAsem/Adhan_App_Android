package com.farms.adhanapp.pray.model;

public class AdhanModel {

    private String nameSheikh;
    private String pathAdhan;

    public AdhanModel(String nameSheikh, String pathAdhan) {
        this.nameSheikh = nameSheikh;
        this.pathAdhan = pathAdhan;
    }

    public AdhanModel() {
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
