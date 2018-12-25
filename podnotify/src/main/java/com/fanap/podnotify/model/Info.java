package com.fanap.podnotify.model;

/**
 * Created by ArvinRokni
 * on Mon, 17 December 2018 at 12:09 PM.
*/
public class Info {

    private Double lat;
    private Double lng;
    private String os;
    private String brand;
    private String version;
    private String model;
    private SDKType sdkType = SDKType.ANDROID;

    public enum SDKType{
        ANDROID,
        IOS,
        WEB
    }


    public static class builder{
        private Double lat;
        private Double lng;
        private String os;
        private String brand;
        private String version;
        private String model;
        private SDKType sdkType = SDKType.ANDROID;

        public builder setLat(Double lat) {
            this.lat = lat;
            return this;
        }

        public builder setLng(Double lng) {
            this.lng = lng;
            return this;
        }

        public builder setOs(String os) {
            this.os = os;
            return this;
        }

        public builder setBrand(String brand) {
            this.brand = brand;
            return this;
        }

        public builder setVersion(String version) {
            this.version = version;
            return this;
        }

        public builder setModel(String model) {
            this.model = model;
            return this;
        }

        public builder setSdkType(SDKType sdkType) {
            this.sdkType = sdkType;
            return this;
        }
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public SDKType getSdkType() {
        return sdkType;
    }

    public void setSdkType(SDKType sdkType) {
        this.sdkType = sdkType;
    }
}
