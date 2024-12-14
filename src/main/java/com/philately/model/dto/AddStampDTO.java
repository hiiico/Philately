package com.philately.model.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import com.philately.model.entity.User;
import com.philately.model.entity.enums.PaperEnum;

public class AddStampDTO {

    Long id;

    @Size(min = 5, max = 20, message = "Name length must be between 5 and 20 characters!")
    @NotNull
    private String name;

    @Size(min = 5, max = 25, message = "Description length must be between 5 and 25 characters!")
    @NotNull
    private String description;

    @Positive
    @NotNull(message = "Please enter positive number!")
    private int price;

    @NotNull(message = "You must select a type of paper!")
    private PaperEnum paper;

    @URL(message = "Please enter valid image URL!")
    @NotBlank(message = "Please enter valid image URL!")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User addedBy;

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

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }
}
