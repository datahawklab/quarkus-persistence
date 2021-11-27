package org.agoncal.quarkus.jdbc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;


@Entity
@Table(name = "t_artist")
public class Artist {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "name", length = 50, nullable = false)
  private String name;

  @Column(name = "bio", length = 50, nullable = false)
  private String bio;

  @Column(name = "created_date", nullable = false)
  private Instant createdDate = Instant.now();

  public Artist() {}

  public Artist(String name, String bio) {
    this.name = name;
    this.bio = bio;
  }

  public Artist(String name) {
    this.name = name;
  }


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

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public Instant getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Instant createdDate) {
    this.createdDate = createdDate;
  }

  @Override
  public String toString() {
    return "Artist{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", bio='" + bio + '\'' +
      ", createdDate=" + createdDate +
      '}';
  }
}