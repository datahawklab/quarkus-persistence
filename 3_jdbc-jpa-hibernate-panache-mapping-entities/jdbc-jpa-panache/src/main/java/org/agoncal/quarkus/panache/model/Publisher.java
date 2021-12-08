package org.agoncal.quarkus.panache.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import java.time.Instant;
@Entity
@Table(name="t_publishers")
public class Publisher extends PanacheEntity {

  @Column(length=50,nullable=false)
  public String name;
  public Instant createdDate = Instant.now();

  public Publisher() {}

  public Publisher(String name) {
    this.name = name;
  }

}