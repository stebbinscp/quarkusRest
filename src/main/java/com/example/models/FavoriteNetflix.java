package com.example.models;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
// else when serialized, there will be no output
// now when compiled, include all reflection info for serialization
public class FavoriteNetflix {

    private String title;
    private String synopsis;
    private String img;
    private String id;

    @Override
    public String toString() {
        return "FavoriteNetflix{" +
                "title='" + title + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", img='" + img + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
