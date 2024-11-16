package dev.lpa.todolist.datamodel;

import javafx.collections.FXCollections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TodoData {
  private static  TodoData instance = new TodoData();
  private static String filename = "TodoListItems.txt";

  private List<TodoItem> todoItems;
  private DateTimeFormatter formatter;

  public static TodoData getInstance() {
    return instance;
  }

  private TodoData() {
    formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  }

  public List<TodoItem> getTodoItems() {
    return todoItems;
  }

  public void setTodoItems(List<TodoItem> todoItems) {
    this.todoItems = todoItems;
  }

  public void loadTodoItems() throws IOException {

    todoItems = FXCollections.observableArrayList();
    Path path = Paths.get(filename);

    try (BufferedReader br = Files.newBufferedReader(path)) {

      String input;
      while((input = br.readLine()) != null) {
        String[] itemPieces = input.split("\t");

        String shortDescription = itemPieces[0];
        String details = itemPieces[1];
        String dateString = itemPieces[2];

        LocalDate date = LocalDate.parse(dateString, formatter);
        TodoItem todoItem = new TodoItem(shortDescription, details, date);
        todoItems.add(todoItem);
      }
    }
  }

  public void storeTodoItems() throws IOException {

    Path path = Paths.get(filename);
    try (BufferedWriter bw = Files.newBufferedWriter(path)) {

      for(var todoItem : todoItems) {
        String line = String.join("\t",
          todoItem.getShortDescription(),
          todoItem.getDetails(),
          todoItem.getDeadline().format(formatter));

        bw.write(line);
        bw.newLine();
      }
    }

  }
}
