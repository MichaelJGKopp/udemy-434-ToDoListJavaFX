package dev.lpa.todolist;

import dev.lpa.todolist.datamodel.TodoData;
import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

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

    // Get the list of screens
    List<Screen> screens = Screen.getScreens();

    // Check if a secondary screen is available
    if (screens.size() > 1) {
      // Get the bounds of the secondary screen
      Rectangle2D secondaryScreenBounds = screens.get(1).getVisualBounds();

      // Set the stage position and size to the secondary screen bounds
      Rectangle2D ssb = secondaryScreenBounds;
      stage.setX(ssb.getWidth()/4 + ssb.getMinX());
      stage.setY(ssb.getHeight()/4 + ssb.getMinY());
//      stage.setWidth(secondaryScreenBounds.getWidth());
//      stage.setHeight(secondaryScreenBounds.getHeight());
    }

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