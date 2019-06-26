package com.beet.model;

/**
 * City
 */
public class City {

    private String id;
    private String province;
    private String city;
    private String district;

    public String getId() {
        return id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getCity();
    }
    
}