package controller;

import db.DBQueriesImpl;
import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import parser.ExcelParser;
import table.UploadTable;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class UploadController {
    private ObservableList<UploadTable> scheduleErrors = FXCollections.observableArrayList();
    private DBQueriesImpl queries ;
    private ExcelParser parser;


    @FXML
    private Button uploadButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button checkButton;

    @FXML
    private TableView<UploadTable> tableErrors;

    @FXML
    private TableColumn<Schedule, Integer> idError;

    @FXML
    private TableColumn<Schedule, Integer> yearError;

    @FXML
    private TableColumn<Schedule, String> groupError;

    @FXML
    private TableColumn<Schedule, String> specError;

    @FXML
    private TableColumn<Schedule, String> disciplineError;

    @FXML
    private TableColumn<Schedule, String> lecturerError;

    @FXML
    private TableColumn<Schedule, String> dayError;

    @FXML
    private TableColumn<Schedule, String> periodError;

    @FXML
    private TableColumn<Schedule, String> classroomError;

    @FXML
    private TableColumn<Schedule, String> classTypeError;

    @FXML
    private TableColumn<Schedule, Integer> weekNumberErrors;



    @FXML
    private void initialize() {
        initData();

        idError.setCellValueFactory(new PropertyValueFactory<Schedule, Integer>("id"));
        yearError.setCellValueFactory(new PropertyValueFactory<Schedule, Integer>("year"));
        groupError.setCellValueFactory(new PropertyValueFactory<Schedule, String>("group"));
        specError.setCellValueFactory(new PropertyValueFactory<Schedule, String>("specialization"));
        disciplineError.setCellValueFactory(new PropertyValueFactory<Schedule, String>("discipline"));
        lecturerError.setCellValueFactory(new PropertyValueFactory<Schedule, String>("lecturer"));
        dayError.setCellValueFactory(new PropertyValueFactory<Schedule, String>("day"));
        periodError.setCellValueFactory(new PropertyValueFactory<Schedule, String>("period"));
        classroomError.setCellValueFactory(new PropertyValueFactory<Schedule, String>("classroom"));
        classTypeError.setCellValueFactory(new PropertyValueFactory<Schedule, String>("classType"));
        weekNumberErrors.setCellValueFactory(new PropertyValueFactory<Schedule, Integer>("weekNumber"));

        tableErrors.setItems(scheduleErrors);
    }

    private void initData() {
         try {
             ArrayList<Schedule> schedules = queries.getScheduleErrors();
             for (Schedule sced : schedules) {
                 UploadTable tableUpload = new UploadTable(sced);

                 scheduleErrors.add(tableUpload);
             }
         }
         catch(NullPointerException e)
         {
             System.out.print("NullPointerException caught");
         }
    }



    public void uploadSchedule(javafx.event.ActionEvent actionEvent) {
        //parser.parse(String path);
    }

    public void deleteSchedule(javafx.event.ActionEvent actionEvent) {
        queries.cleanDB();
    }

    public void checkSchedule(javafx.event.ActionEvent actionEvent) {
        scheduleErrors.removeAll();
        ArrayList<Schedule> schedules = queries.getScheduleErrors();
        for (Schedule sced: schedules) {
            UploadTable tableUpload = new UploadTable(sced);
            scheduleErrors.add(tableUpload);
        }
        tableErrors.refresh();
    }
}
