package org.agoncal.quarkus.panache.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import java.time.Instant;
import java.util.Optional;
import java.util.List;

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

  public static List<Publisher> findContainingName(String name) {
    return Publisher.list("name like ?1", "%" + name + "%");
  }
  public static Optional<Publisher> findByName(String name){
    return Publisher.find("name",name).firstResultOptional();
  }

}