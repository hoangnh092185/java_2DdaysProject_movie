import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class MovieTest{

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void equals_returnTrueIfMoviesAreTheSame(){
    Movie newMovie1 = new Movie("Mad Max", 1);
    Movie newMovie2 = new Movie("Mad Max", 1);
    assertTrue(newMovie1.equals(newMovie2));
  }

  @Test
  public void save_returnTrueIfMoviesAreTheSame(){
    Movie newMovie1 = new Movie("Mad Max", 1);
    newMovie1.save();
    assertTrue(Movie.all().get(0).equals(newMovie1));
  }

  @Test
  public void all_returnsAllInstancesOfMovie_true(){
    Movie newMovie1 = new Movie("Mad Max", 1);
    newMovie1.save();
    Movie newMovie2 = new Movie("Django", 1);
    newMovie2.save();
    assertEquals(true, Movie.all().get(0).equals(newMovie1));
    assertEquals(true, Movie.all().get(1).equals(newMovie2));
  }

  @Test
  public void save_assignsIdToObject(){
    Movie newMovie1 = new Movie("Mad Max", 1);
    newMovie1.save();
    Movie saveMovie = Movie.all().get(0);
    assertEquals(newMovie1.getId(), saveMovie.getId());
  }

  @Test
  public void getId_MoviesInstantiateWithId(){
    Movie newMovie1 = new Movie("Mad Max", 1);
    newMovie1.save();
    assertTrue(newMovie1.getId()>0);
  }

  @Test
  public void find_returnsMovieWithSameId_newMovie2(){
  Movie newMovie1 = new Movie("Mad Max", 1);
  newMovie1.save();
  Movie newMovie2 = new Movie("Django", 1);
  newMovie2.save();
  assertEquals(true, Movie.all().get(0).equals(newMovie1));
  assertEquals(Movie.find(newMovie2.getId()), newMovie2);
  }

}
