package com.philately.model.dto;

import com.philately.model.entity.Stamp;

import java.util.HashSet;
import java.util.Set;

public class PaperSeedDTO {

    private String paperEnum;

    private String description;

    private Set<Stamp> stamps;

    public PaperSeedDTO(String paperEnum, String description) {
        this.paperEnum = paperEnum;
        this.description = description;
        this.stamps = new HashSet<>();
    }

    public String getPaperEnum() {
        return paperEnum;
    }

    public void setPaperEnum(String paperEnum) {
        this.paperEnum = paperEnum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Stamp> getStamps() {
        return stamps;
    }

    public void setStamps(Set<Stamp> stamps) {
        this.stamps = stamps;
    }
}
