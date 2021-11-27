package org.agoncal.quarkus.jdbc;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.agoncal.quarkus.jdbc.Artist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@QuarkusTest
public class ArtistRepositoryTest {

  @Test
  @TestTransaction
  public void shouldCreateAndFindAnArtist(){
    Artist artist = new Artist("name", "bio");

    Artist.persist(artist);
    assertNotNull(artist.id);

    artist = Artist.findById(artist.id);
    assertEquals("name", artist.name);
  }
}