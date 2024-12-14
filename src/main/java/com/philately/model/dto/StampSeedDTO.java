package com.philately.model.dto;

import com.philately.model.entity.enums.PaperEnum;

public class StampSeedDTO {

    private Long id;

    private String name;

    private String description;


    private int price;

    private PaperEnum paper;

    private String imageUrl;

    private Long addedBy;

    private boolean wished;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public PaperEnum getPaper() {
        return paper;
    }

    public void setPaper(PaperEnum paper) {
        this.paper = paper;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Long addedBy) {
        this.addedBy = addedBy;
    }

    public boolean isWished() {
        return wished;
    }

    public void setWished(boolean wished) {
        this.wished = wished;
    }
}
