package dev.lpa.todolist;

import dev.lpa.todolist.datamodel.TodoItem;
import javafx.fxml.FXML;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Controller {

  private List<TodoItem> todoItems;

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
  }
}