package com.philately.model.entity;


import jakarta.persistence.*;
import com.philately.model.entity.enums.PaperEnum;

import java.util.Set;

@Entity
@Table(name = "papers")
public class Paper extends BaseEntity{


    @Enumerated(EnumType.STRING)
        private PaperEnum paperEnum;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "paper")
    private Set<Stamp> stamps;

    public PaperEnum getPaperEnum() {
        return paperEnum;
    }

    public void setPaperEnum(PaperEnum paperEnum) {
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
