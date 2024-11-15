module dev.lpa.udemy434todolist {
  requires javafx.controls;
  requires javafx.fxml;


  opens dev.lpa.todolist to javafx.fxml;
  exports dev.lpa.todolist;
}