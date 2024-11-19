package dev.lpa.todolist;

import dev.lpa.todolist.datamodel.TodoData;
import dev.lpa.todolist.datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {

  @FXML
  private TextField shortDescriptionField;

  @FXML
  private TextArea detailsArea;

  @FXML
  public DatePicker deadlinePicker;

  public void processResults() {
    String shortDescription = shortDescriptionField.getText().trim();
    String details = detailsArea.getText().trim();
    LocalDate deadlineValue = deadlinePicker.getValue();

    TodoData.getInstance().addTodoItem(new TodoItem(shortDescription, details, deadlineValue));
  }

}
