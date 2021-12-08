package org.agoncal.quarkus.panache.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Book extends Item {

  @Column(length = 15)
  public String isbn;

  @Column(name = "nb_of_pages")
  public Integer nbOfPages;

  @Column(name = "publication_date")
  public LocalDate publicationDate;

  @Column(length = 20)
  @Enumerated(EnumType.STRING)
  public Language language;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "publisher_fk")
  public Publisher publisher;

}