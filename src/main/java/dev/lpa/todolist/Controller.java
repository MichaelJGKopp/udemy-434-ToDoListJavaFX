package dev.lpa.todolist;

import dev.lpa.todolist.datamodel.TodoData;
import dev.lpa.todolist.datamodel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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

    todoListView.getSelectionModel().selectedItemProperty()
      .addListener(new ChangeListener<TodoItem>() {
        @Override
        public void changed(ObservableValue<? extends TodoItem> observableValue,
                            TodoItem oldValue, TodoItem newValue) {
          if(newValue != null) {
            TodoItem item = todoListView.getSelectionModel().getSelectedItem();
            itemDetailsTextArea.setText(item.getDetails());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d, yyyy");
            deadlineLabel.setText(item.getDeadline().format(dtf));
          }
        }
      });

    todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
    todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    todoListView.getSelectionModel().selectFirst(); // select first to do list item
  }

  @FXML
  public void showNewItemDialog() {
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.initOwner(mainBorderPane.getScene().getWindow());  // parent disabled while dialog
    try {
      Parent root = FXMLLoader.load(getClass().getResource("todoItemDialog.fxml"));
      dialog.getDialogPane().setContent(root);
    } catch(IOException e) {
      System.out.println("Couldn't load the dialog");
      e.printStackTrace();
      return;
    }

    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
    dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

    Optional<ButtonType> result = dialog.showAndWait();
    if(result.isPresent() && result.get() == ButtonType.OK) {
      System.out.println("OK pressed");
    } else {
      System.out.println("Cancel pressed");
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
}