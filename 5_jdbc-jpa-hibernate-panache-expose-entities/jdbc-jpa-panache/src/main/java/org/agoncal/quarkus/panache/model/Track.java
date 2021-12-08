package org.agoncal.quarkus.panache.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

//import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Duration;
import java.time.Instant;
//import java.util.Objects;

@Entity
@Table(name = "t_tracks")
public class Track extends PanacheEntity {

  @Column(nullable = false)
  public String title;

  @Column(nullable = false)
  public Duration duration;

  @ManyToOne()
  @JoinColumn(name = "cd_fk")
  public CD cd;

  @Column(name = "created_date", nullable = false)
  public Instant createdDate = Instant.now();



}