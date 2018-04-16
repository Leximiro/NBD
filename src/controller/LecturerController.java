package controller;

import db.DBQueriesImpl;
import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import table.LecturerTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class LecturerController {

    private ObservableList<LecturerTable> lecturerTables = FXCollections.observableArrayList();
    private DBQueriesImpl queries ;
    ArrayList<Lecturer> lecturers = queries.getAllLecturers();

    ArrayList<Week> weeks = queries.getAllWeeks();

    ArrayList<Specialization> specs = queries.getAllSpecializations();

    ArrayList<Integer> courses = queries.getAllCourses();

    ArrayList<Discipline> disciplines = queries.getAllDisciplines();


    @FXML
    ChoiceBox<String> lecturerNameChoice;
    @FXML
    ChoiceBox<Integer> weeksChoice;
    @FXML
    ChoiceBox<String> specChoice;
    @FXML
    ChoiceBox<Integer> courseChoice;
    @FXML
    ChoiceBox<String> scheduleChoice;




    @FXML
    private Button showButton;

    @FXML
    private TableView<LecturerTable> lecturerTable;

    @FXML
    private TableColumn<LecturerTable, Integer> day;

    @FXML
    private TableColumn<LecturerTable, String> lesson1;

    @FXML
    private TableColumn<LecturerTable, String> lesson2;

    @FXML
    private TableColumn<LecturerTable, String> lesson3;

    @FXML
    private TableColumn<LecturerTable, String> lesson4;

    @FXML
    private TableColumn<LecturerTable, String> lesson5;

    @FXML
    private TableColumn<LecturerTable, String> lesson6;

    @FXML
    private TableColumn<LecturerTable, String> lesson7;

    @FXML
    private void initialize() {
        choiceBoxesFill();

    }



    private void choiceBoxesFill(){
        ObservableList<String> lecturersFX = FXCollections.observableArrayList();
        for (Lecturer lecturer: lecturers) {
            lecturersFX.add(lecturer.getName());
        }
        lecturerNameChoice.setItems(lecturersFX);
        ObservableList<Integer> weeksFX = FXCollections.observableArrayList();
        for (Week week: weeks) {
            weeksFX.add(week.getNumber());
        }
        weeksChoice.setItems(weeksFX);
        ObservableList<String> specFX = FXCollections.observableArrayList();
        for (Specialization spec: specs) {
            specFX.add(spec.getName());
        }
        specChoice.setItems(specFX);
        ObservableList<Integer> coursesFX = FXCollections.observableArrayList();
        for (Integer course: courses) {
            coursesFX.add(course);
        }
        courseChoice.setItems(coursesFX);
        ObservableList<String> disciplinesFX = FXCollections.observableArrayList();
        for (Discipline discipline: disciplines) {
            disciplinesFX.add(discipline.getName());
        }
        scheduleChoice.setItems(disciplinesFX);
    }





    @FXML
    public void showButtonCLicked(ActionEvent event){
       Lecturer lect = queries.getLecturerByName(lecturerNameChoice.getSelectionModel().getSelectedItem());
       Week wee = queries.getWeekByNumber(weeksChoice.getSelectionModel().getSelectedItem());
       Specialization spec = queries.getSpecializationByName(specChoice.getSelectionModel().getSelectedItem());
       int cours =  courseChoice.getSelectionModel().getSelectedItem();
       Discipline disp = queries.getDisciplineByName(scheduleChoice.getSelectionModel().getSelectedItem());
       ArrayList<Schedule> scedule = queries.getScheduleByLecturerAndWeekAndSpecAndCourseAndDiscipline(lect,wee,spec,cours,disp);
       ArrayList<Day> days = queries.getAllDays();
       for (Day day : days) {
           //TODO
       }


        day.setCellValueFactory(new PropertyValueFactory<LecturerTable, Integer>("day"));
        lesson1.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("lesson1"));
        lesson2.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("lesson2"));
        lesson3.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("lesson3"));
        lesson4.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("lesson4"));
        lesson5.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("lesson5"));
        lesson6.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("lesson6"));
        lesson7.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("lesson7"));

        lecturerTable.setItems(lecturerTables);



    }
}
