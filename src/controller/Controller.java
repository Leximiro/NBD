package controller;

import db.DBQueriesImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import entity.Schedule;
import javafx.stage.FileChooser;


import java.io.File;
import java.util.ArrayList;


public class Controller {
    @FXML
    LaborantController laborantController;
    @FXML
    StudentController studentController;
    @FXML
    LecturerController lecturerController;
    @FXML
    UploadController uploadController;


    @FXML
    private void initialize(){
        laborantController.init(this);
        studentController.init(this);
        lecturerController.init(this);
        uploadController.init(this);
    }

}
