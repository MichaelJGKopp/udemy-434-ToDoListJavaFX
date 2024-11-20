package dev.lpa.todolist;

import dev.lpa.todolist.datamodel.TodoData;
import dev.lpa.todolist.datamodel.TodoItem;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class Controller {

//  private List<TodoItem> todoItems;

  @FXML
  private ListView<TodoItem> todoListView;

  @FXML
  private TextArea itemDetailsTextArea;

  @FXML
  private Label deadlineLabel;

  @FXML
  private BorderPane mainBorderPane;

  @FXML
  private ContextMenu listContextMenu;

  public void initialize() {
//    TodoItem item1 = new TodoItem(
//      "Mail birthday card",
//      "Buy a 30th birthday card for John",
//      LocalDate.of(2024, Month.APRIL, 25));
//    TodoItem item2 = new TodoItem(
//      "Doctor's Appointment",
//      "See Dr. Smith at 123 Main Street. Bring paperwork",
//      LocalDate.of(2024, Month.NOVEMBER, 23));
//    TodoItem item3 = new TodoItem(
//      "Finish design proposal for client",
//      "I promised Mike I'd email website mockups by Friday 22nd April",
//      LocalDate.of(2024, Month.APRIL, 22));
//    TodoItem item4 = new TodoItem(
//      "Pickup Doug at the train station",
//      "Doug's arriving March 23 on the 5:00 train",
//      LocalDate.of(2024, Month.MARCH, 23));
//    TodoItem item5 = new TodoItem(
//      "Pick up dry cleaning",
//      "The clothes should be ready by Wednesday",
//      LocalDate.of(2024, Month.APRIL, 20));

//    todoItems = new ArrayList<>(List.of(item1, item2, item3, item4, item5));
//    TodoData.getInstance().setTodoItems(todoItems);

    listContextMenu = new ContextMenu();
    MenuItem deleteMenuItem = new MenuItem("Delete");
    deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent actionEvent) {
        TodoItem item = todoListView.getSelectionModel().getSelectedItem();
        deleteItem(item);
      }
    });
    listContextMenu.getItems().add(deleteMenuItem);

    todoListView.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<TodoItem>() {
          @Override
          public void changed(ObservableValue<? extends TodoItem> observableValue,
              TodoItem oldValue, TodoItem newValue) {
            if (newValue != null) {
              TodoItem item = todoListView.getSelectionModel().getSelectedItem();
              itemDetailsTextArea.setText(item.getDetails());
              DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d, yyyy");
              deadlineLabel.setText(item.getDeadline().format(dtf));
            }
          }
        });

    todoListView.setItems(TodoData.getInstance().getTodoItems()); // data binding
    todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    todoListView.getSelectionModel().selectFirst(); // select first to do list item

    // paint every cell depending on the todoItem e.g. red, update cell text
    todoListView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
      @Override
      public ListCell<TodoItem> call(ListView<TodoItem> param) {
        ListCell<TodoItem> cell = new ListCell<>() {
          @Override
          protected void updateItem(TodoItem item, boolean empty) {
            super.updateItem(item, empty);  // default appearance by parent class
            if (empty) {
              setText(null);
            } else {
              setText(item.getShortDescription());
              if (item.getDeadline().isBefore(LocalDate.now())) {
                setTextFill(Color.RED);
              } else if (item.getDeadline().equals(LocalDate.now().plusDays(1))) {
                setTextFill(Color.BROWN);
              }
            }
          }
        };

        cell.emptyProperty().addListener(
            (observableValue, wasEmpty, isNowEmpty) -> {
              if (isNowEmpty) {
                cell.setContextMenu(null);
              } else {
                cell.setContextMenu(listContextMenu);
              }
            }
        );

        return cell;
      }
    });
  }

  @FXML
  public void showNewItemDialog() {
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.initOwner(mainBorderPane.getScene().getWindow());  // parent disabled while dialog
    dialog.setTitle("Add New Todo Item");
    dialog.setHeaderText("Use this dialog to create a new todo item");
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
    try {
      dialog.getDialogPane().setContent(fxmlLoader.load());

    } catch (IOException e) {
      System.out.println("Couldn't load the dialog");
      e.printStackTrace();
      return;
    }

    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
    dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

    Optional<ButtonType> result = dialog.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      DialogController controller = fxmlLoader.getController();
      TodoItem newItem = controller.processResults();  // add item to todoList from dialog fields
//      todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());  // update visual list
      todoListView.getSelectionModel().select(newItem); // select the just added Item
//      System.out.println("OK pressed");
//    } else {
//      System.out.println("Cancel pressed");
    }
  }

  @FXML
  public void handleClickListView() {
    TodoItem item = todoListView.getSelectionModel().getSelectedItem();
//    System.out.println("The selected item is " +  item);
//    StringBuilder sb = new StringBuilder(item.getDetails());
//    sb.append("\n\n\n\n")
//      .append("Due: ")
//      .append(item.getDeadline().toString());
    itemDetailsTextArea.setText(item.getDetails());
    deadlineLabel.setText(item.getDeadline().toString());
  }

  public void deleteItem(TodoItem item) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Delete Todo Item");
    alert.setHeaderText("Delete item: " + item.getShortDescription());
    alert.setContentText("Are you sure? Press OK to confirm, or cancel to back out.");
    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent() && (result.get() == ButtonType.OK)) {
      TodoData.getInstance().deleteTodoItem(item);
    }
  }
}