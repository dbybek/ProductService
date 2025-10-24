package com.dbybek.ProductService.Models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter //Annotation for getter methods
@Setter //Annotation for setter methods
@AllArgsConstructor //Annotation for a constructor with all the fields as arguments
@NoArgsConstructor //Annotation for a constructor with no arguments
@Entity //Annotation for letting Hibernate know tables to be created for which models.
public class Product extends BaseModel {
//    private Long id; -> This field is extended from parent class BaseModel.
    private String title;
    private String description;
    private double price;
    private String imageUrl;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Category category;
}
