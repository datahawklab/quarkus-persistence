package org.agoncal.quarkus.jdbc;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;
import java.time.Instant;


@Entity
public class Artist extends PanacheEntity {

  public String name;
  public String bio;
  public Instant createdDate = Instant.now();

  public Artist() {}

  public Artist(String name, String bio) {
    this.name = name;
    this.bio = bio;
  }

  public Artist(String name) {
    this.name = name;
  }

}