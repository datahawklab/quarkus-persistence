package org.agoncal.quarkus.panache.repository;

import javax.enterprise.context.ApplicationScoped;

import org.agoncal.quarkus.jdbc.Artist;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ArtistRepository implements PanacheRepository<Artist>{

}
    
