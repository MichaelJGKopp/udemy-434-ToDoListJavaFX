module dev.lpa.udemy434todolist {
  requires javafx.controls;
  requires javafx.fxml;
  requires atlantafx.base;

  opens dev.lpa.todolist to javafx.fxml;
  exports dev.lpa.todolist;
}