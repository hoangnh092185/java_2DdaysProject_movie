import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class PersonTest{

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void equals_returnsTrueIfNamesAreTheSame(){
    Person newPerson1 = new Person("Nhate");
    Person newPerson2 = new Person("Nhate");
    assertTrue(newPerson1.equals(newPerson2));
  }

  @Test
  public void save_returnsSavedPerson_true(){
    Person newPerson1 = new Person("Nhate");
    newPerson1.save();
    assertEquals(newPerson1, Person.all().get(0));
  }

  @Test
  public void all_returnsAllInstancesOfPerson_true(){
    Person newPerson1 = new Person("Jeremy");
    newPerson1.save();
    Person newPerson2 = new Person("Nhat");
    newPerson2.save();
    assertEquals(true, Person.all().get(0).equals(newPerson1));
    assertEquals(true, Person.all().get(1).equals(newPerson2));
  }

  @Test
  public void save_assignsIdToObject(){
    Person newPerson1 = new Person("Jeremy");
    newPerson1.save();
    Person savePerson = Person.all().get(0);
    assertEquals(newPerson1.getId(), savePerson.getId());
  }

  @Test
  public void getId_PersonsInstantiateWithId(){
    Person newPerson1 = new Person("Jeremy");
    newPerson1.save();
    assertTrue(newPerson1.getId()>0);
  }

  @Test
  public void find_returnsPersonWithSameId_newPerson2(){
  Person newPerson1 = new Person("Jeremy");
  newPerson1.save();
  Person newPerson2 = new Person("Nhat");
  newPerson2.save();
  assertEquals(true, Person.all().get(0).equals(newPerson1));
  assertEquals(Person.find(newPerson2.getId()), newPerson2);
  }

  @Test
  public void getMovies_returnsMovies_List(){
    Person newPerson1 = new Person("Jeremy");
    newPerson1.save();
    Movie newMovie1 = new Movie("Akira", newPerson1.getId());
    newMovie1.save();
    Movie newMovie2 = new Movie("Forrest Gump", newPerson1.getId());
    newMovie2.save();
    assertEquals(newPerson1.getMovies().get(0), newMovie1);
  }

}
