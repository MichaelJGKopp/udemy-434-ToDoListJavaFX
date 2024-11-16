package dev.lpa.todolist;

import dev.lpa.todolist.datamodel.TodoData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

  @Override
  public void init() throws Exception {
    try {
      TodoData.getInstance().loadTodoItems();

    } catch (IOException e) {
      System.err.println("Error loading items from file: " + e.getMessage());
    }
  }

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainwindow.fxml"));
    stage.setTitle("Todo List");
    Scene scene = new Scene(fxmlLoader.load(), 900, 500);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void stop() throws Exception {
    try {
      TodoData.getInstance().storeTodoItems();

    } catch (IOException e) {
      System.out.println("Error saving: " + e.getMessage());
    }
  }
}