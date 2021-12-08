package org.agoncal.quarkus.panache.repository;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.agoncal.quarkus.jdbc.Artist;
import org.junit.jupiter.api.Test;
import javax.inject.Inject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class ArtistRepositoryTest {

  @Inject
  ArtistRepository repository;

  @Test
  @TestTransaction
  public void shouldCreateAndFindAnArtist() {

    long count = repository.count();
    int listAll = repository.listAll().size();
    assertEquals(count, listAll);
    assertEquals(repository.listAllArtistsSorted().size(), listAll);

    Artist artist = new Artist("name", "bio");

    repository.persist(artist);
    assertNotNull(artist.getId());

    assertEquals(count + 1, repository.count());

    artist = repository.findById(artist.getId());
    assertEquals("name", artist.getName());

    repository.deleteById(artist.getId());
    assertEquals(count, repository.count());
  }
}