package net.entity;

import com.idanatz.oneadapter.external.interfaces.Diffable;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class DataEntity implements Serializable, Diffable {
    @Override
    public long getUniqueIdentifier() {
        return 0;
    }

    @Override
    public boolean areContentTheSame(@NotNull Object o) {
        return false;
    }

    public DataEntity(){}

    private String imageUrl;

    private String key;

    private String title;

    private String desc;
    private Double lat;
    private Double lng;

    private String panoid;

    private Boolean isFife = false;






    private String pannoId;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getPanoid() {
        return panoid;
    }

    public void setPanoid(String panoid) {
        this.panoid = panoid;
    }

    public Boolean getFife() {
        return isFife;
    }

    public void setFife(Boolean fife) {
        isFife = fife;
    }

    public String getPannoId() {
        return pannoId;
    }

    public void setPannoId(String pannoId) {
        this.pannoId = pannoId;
    }
}
