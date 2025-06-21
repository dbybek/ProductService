package com.dbybek.ProductService.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter //Annotation for getter methods
@Setter //Annotation for setter methods
@AllArgsConstructor //Annotation for a constructor with all the fields as arguments
@NoArgsConstructor //Annotation for a constructor with no arguments
public class Product {
    private Long id;
    private String title;
    private String description;
    private double price;
    private String imageUrl;
    private Category category;


}
