package dev.lpa.todolist;

import dev.lpa.todolist.datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Controller {

  private List<TodoItem> todoItems;

  @FXML
  private ListView<TodoItem> todoListView;

  @FXML
  private TextArea itemDetailsTextArea;

  @FXML
  private Label deadlineLabel;

  public void initialize() {
    TodoItem item1 = new TodoItem(
      "Mail birthday card",
      "Buy a 30th birthday card for John",
      LocalDate.of(2024, Month.APRIL, 25));
    TodoItem item2 = new TodoItem(
      "Doctor's Appointment",
      "See Dr. Smith at 123 Main Street. Bring paperwork",
      LocalDate.of(2024, Month.NOVEMBER, 23));
    TodoItem item3 = new TodoItem(
      "Finish design proposal for client",
      "I promised Mike I'd email website mockups by Friday 22nd April",
      LocalDate.of(2024, Month.APRIL, 22));
    TodoItem item4 = new TodoItem(
      "Pickup Doug at the train station",
      "Doug's arriving March 23 on the 5:00 train",
      LocalDate.of(2024, Month.MARCH, 23));
    TodoItem item5 = new TodoItem(
      "Pick up dry cleaning",
      "The clothes should be ready by Wednesday",
      LocalDate.of(2024, Month.APRIL, 20));

    todoItems = new ArrayList<>(List.of(item1, item2, item3, item4, item5));
    todoListView.getItems().setAll(todoItems);
    todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    todoListView.getSelectionModel().selectFirst(); // select first to do list item
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