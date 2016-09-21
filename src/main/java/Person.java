import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Person {
  private String name;
  private int id;

  public Person(String _name){
    name = _name;
  }

  public String getName(){
    return name;
  }

  public int getId(){
    return id;
  }

  public static List<Person> all() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT id, name FROM persons";
      return con.createQuery(sql).executeAndFetch(Person.class);
    }
  }

  public List<Movie> getMovies(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM movies where personId=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Movie.class);
    }
  }

  public static Person find (int _id){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT id, name FROM persons where id=:id";
      Person person = con.createQuery(sql)
        .addParameter("id", _id)
        .executeAndFetchFirst(Person.class);
        return person;
      }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO persons(name) VALUES(:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object otherPerson){
    if(!(otherPerson instanceof Person)){
      return false;
    }else {
      Person newPerson = (Person) otherPerson;
      return this.getName().equals(newPerson.getName()) &&
        this.getId() == newPerson.getId();
    }
  }
}
