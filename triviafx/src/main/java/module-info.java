module triviafx.triviafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires QuestionModel;
    opens triviafx.triviafx to javafx.fxml;

    exports triviafx.triviafx;
}