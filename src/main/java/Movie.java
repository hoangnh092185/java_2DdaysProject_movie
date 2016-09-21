import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Movie {
  private String title;
  private int id;
  private int personId;
  private String description;

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

  public static List<Movie> all() {
  String sql = "SELECT id, title, personId FROM movies";
  try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql).executeAndFetch(Movie.class);
    }
  }

  public void save() {
    try(Connection con= DB.sql2o.open()){
      String sql = "INSERT INTO movies(title, personId) VALUES (:title, :personId)";
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
