package com.monolithic.ecommerce.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@ToString
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "language")
    private String language;
    @Column(name = "description")
    private String description;
    @Column(name = "publication_date")
    private String publicationDate;
    @Column(name = "number_of_pages")
    private Integer numberOfPages;
    @Column(name = "category")
    private String category;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "genre")
    private String genre;
    @Column(name = "quantity")
    private Integer quantity;
}
