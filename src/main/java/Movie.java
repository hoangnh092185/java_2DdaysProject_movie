import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Movie {
  private String title;
  private int id;
  private int personId;
  private String description;
  private String stars;

  public Movie(String _title, int _personId){
    title = _title;
    personId = _personId;
  }

  public String getTitle(){
    return title;
  }

  public int getId() {
    return id;
  }

  public int getPersonId() {
    return personId;
  }

  public void setDescription(String _description) {
    this.description = _description;
  }

  public void setStars(String _stars) {
    this.stars = _stars;
  }

  public String getDescription() {
    return description;
  }

  public String getStars() {
    return stars;
  }


  public static List<Movie> all() {
  String sql = "SELECT id, title, personId FROM movies";
  try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql).executeAndFetch(Movie.class);
    }
  }

  public void save() {
    try(Connection con= DB.sql2o.open()){
      String sql = "INSERT INTO movies(title, personId, description, stars) VALUES (:title, :personId,'', 'No')";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", this.title)
        .addParameter("personId", this.personId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Movie find(int _id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM movies WHERE id=:id";
      Movie movie = con.createQuery(sql)
        .addParameter("id", _id)
        .executeAndFetchFirst(Movie.class);
      return movie;
    }
  }

  public void delete(){
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM movies WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void updateDescription() {
    try(Connection con= DB.sql2o.open()){
      String sql = "UPDATE movies SET description=:description WHERE id=:id ";
      con.createQuery(sql)
        .addParameter("description", this.description)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void updateStars() {
    try(Connection con= DB.sql2o.open()){
      String sql = "UPDATE movies SET stars=:stars WHERE id=:id ";
      con.createQuery(sql)
        .addParameter("stars", this.stars)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }


  @Override
  public boolean equals(Object otherMovie){
    if(!(otherMovie instanceof Movie)){
      return false;
    }else {
      Movie newMovie = (Movie) otherMovie;
      return this.getTitle().equals(newMovie.getTitle()) &&
              this.getPersonId() == newMovie.getPersonId();
    }
  }


}
