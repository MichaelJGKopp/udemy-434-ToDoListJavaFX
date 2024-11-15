module dev.lpa.udemy434todolist {
  requires javafx.controls;
  requires javafx.fxml;


  opens dev.lpa.udemy434todolist to javafx.fxml;
  exports dev.lpa.udemy434todolist;
}