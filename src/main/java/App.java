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

    post("/movie-form", (request, repsonse)->{
      Map<String, Object> model = new HashMap<String, Object>();
      Person person = new Person(request.queryParams("user-name"));
      person.save();
      model.put("person", person);
      model.put("template","templates/movie-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/person/:id", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    Person person = Person.find(Integer.parseInt(request.params(":id")));
    Movie movie1 = new Movie(request.queryParams("movie1"), person.getId());
    movie1.save();
    Movie movie2 = new Movie(request.queryParams("movie2"), person.getId());
    movie2.save();
    Movie movie3 = new Movie(request.queryParams("movie3"), person.getId());
    movie3.save();
    model.put("person",person);
    model.put("personMovies",person.getMovies());
    model.put("template","templates/person.vtl");
    return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/person/:id", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    Person person = Person.find(Integer.parseInt(request.params(":id")));
    model.put("person",person);
    model.put("personMovies",person.getMovies());
    model.put("template","templates/person.vtl");
    return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}





//     get("/", (request, response) -> {
//       Map<String, Object> model = new HashMap<String, Object>();
//       model.put("template", "templates/index.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     get("tasks/new", (request, response) -> {
//       Map<String, Object> model = new HashMap<String, Object>();
//       model.put("template", "templates/task-form.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     get("/tasks", (request, response) -> {
//       Map<String, Object> model = new HashMap<String, Object>();
//       model.put("tasks", Task.all());
//       model.put("template", "templates/tasks.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     post("/tasks", (request, response) -> {
//       Map<String, Object> model = new HashMap<String, Object>();
//
//       Category category = Category.find(Integer.parseInt(request.queryParams("categoryId")));
//
//       String description = request.queryParams("description");
//       Task newTask = new Task(description);
//
//       category.addTask(newTask);
//
//       model.put("category", category);
//       model.put("template", "templates/category-tasks-success.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     get("/tasks/:id", (request, response) -> {
//       HashMap<String, Object> model = new HashMap<String, Object>();
//       Task task = Task.find(Integer.parseInt(request.params(":id")));
//       model.put("task", task);
//       model.put("template", "templates/task.vtl");
//       return new ModelAndView(model, layout);
//     },new VelocityTemplateEngine());
//
//     get("/categories/new", (request, response) -> {
//       Map<String, Object> model = new HashMap<String, Object>();
//       model.put("template", "templates/category-form.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     post("/categories", (request, response) -> {
//       Map<String, Object> model = new HashMap<String, Object>();
//       String name = request.queryParams("name");
//       Category newCategory = new Category(name);
//       model.put("template", "templates/category-success.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     get("/categories", (request, response) -> {
//       Map<String, Object> model = new HashMap<String, Object>();
//       model.put("categories", Category.all());
//       model.put("template", "templates/categories.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     get("/categories/:id", (request, response) -> {
//       Map<String, Object> model = new HashMap<String, Object>();
//       Category category = Category.find(Integer.parseInt(request.params(":id")));
//       model.put("category", category);
//       model.put("template", "templates/category.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     get("/categories/:id/tasks/new", (request, response) -> {
//       Map<String, Object> model = new HashMap<String, Object>();
//       Category category = Category.find(Integer.parseInt(request.params(":id")));
//       model.put("category", category);
//       model.put("template", "templates/category-tasks-form.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//     post("/categories/:category_id/tasks/:id/delete", (request, response) -> {
//       HashMap<String, Object> model = new HashMap<String, Object>();
//       Task task = Task.find(Integer.parseInt(request.params("id")));
//       Category category = Category.find(task.getCategoryId());
//       task.delete();
//       model.put("category", category);
//       model.put("template", "templates/category.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//   }
// }
