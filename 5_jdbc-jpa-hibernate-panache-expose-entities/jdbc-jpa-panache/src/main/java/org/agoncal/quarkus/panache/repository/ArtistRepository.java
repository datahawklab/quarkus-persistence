package org.agoncal.quarkus.panache.repository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import org.agoncal.quarkus.jdbc.Artist;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;



@ApplicationScoped
public class ArtistRepository implements PanacheRepository<Artist>{

    public List<Artist> listAllArtistsSorted(){
        return listAll(Sort.descending("name"));
    }


}


