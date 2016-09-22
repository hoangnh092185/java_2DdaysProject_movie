import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request,response) ->{
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("persons", Person.all());
      model.put("template","templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/person/new", (request, repsonse)->{
      Map<String, Object> model = new HashMap<String, Object>();
      Person person = new Person(request.queryParams("user-name"));
      person.save();
      model.put("person", person);
      model.put("template","templates/movie-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // post("/movie-form", (request, repsonse)->{
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   Person person = new Person(request.queryParams("user-name"));
    //   person.save();
    //   model.put("person", person);
    //   model.put("template","templates/movie-form.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());

    post("/person/:id", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    Person person = Person.find(Integer.parseInt(request.params(":id")));
    Movie movie = new Movie(request.queryParams("movie"), person.getId());
    movie.save();
    model.put("person",person);
    model.put("template","templates/movie-form.vtl");
    return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/person/:id", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    Person person = Person.find(Integer.parseInt(request.params(":id")));
    model.put("person",person);
    model.put("personMovies",person.getMovies());
    model.put("template","templates/movie-form.vtl");
    return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/person/:id/delete-success", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    Person person = Person.find(Integer.parseInt(request.params(":id")));
    model.put("person", person);
    person.deleteMovies();
    person.delete();
    model.put("template","templates/delete-success.vtl");
    return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/person/:person_id/movie/:id/delete-movie-success", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    Movie title = Movie.find(Integer.parseInt(request.params(":id")));
    model.put("titles", title);
    title.delete();
    model.put("template","templates/delete-movie-success.vtl");
    return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/person/:person_id/movie/:id", (request, response)->{
    Map<String, Object> model = new HashMap<String, Object>();
    Movie movie = Movie.find(Integer.parseInt(request.params(":id")));
    Person person = Person.find(Integer.parseInt(request.params(":person_id")));
    model.put("movie", movie);
    model.put("person", person);
    model.put("template", "templates/movie.vtl");
    return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/person/:id/info-success", (request, response)->{
    Map<String, Object> model = new HashMap<String, Object>();
    Person person = Person.find(Integer.parseInt(request.params(":id")));
    Movie movie = Movie.find(Integer.parseInt(request.queryParams("movie")));
    String description = request.queryParams("description");
    String stars = request.queryParams("stars");
    movie.setDescription(description);
    movie.setStars(stars);
    movie.updateDescription();
    movie.updateStars();
    model.put("movie", movie);
    model.put("person", person);
    model.put("template", "templates/movie-form.vtl");
    return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
