package controller;

import db.DBQueries;
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
import java.util.HashMap;
import java.util.stream.Collectors;

public class LecturerController {

    private ObservableList<LecturerTable> lecturerTables = FXCollections.observableArrayList();
    private DBQueriesImpl queries ;




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
    private TableColumn<LecturerTable, String> day;

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

        day.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("day"));
        lesson1.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("lesson1"));



        lesson2.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("lesson2"));

        lesson3.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("lesson3"));
        lesson4.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("lesson4"));
        lesson5.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("lesson5"));
        lesson6.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("lesson6"));
        lesson7.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("lesson7"));
        lecturerTable.setId("table-row-cell");
        lecturerTable.setItems(lecturerTables);

    }



    private void choiceBoxesFill() {
        queries = new DBQueriesImpl();
        ArrayList<Lecturer> lecturers = queries.getAllLecturers();
        ObservableList<String> lecturersFX = FXCollections.observableArrayList();
        for (Lecturer lecturer : lecturers) {
            lecturersFX.add(lecturer.getName());
        }
        lecturerNameChoice.setItems(lecturersFX);
        ArrayList<Week> weeks = queries.getAllWeeks();
        ObservableList<Integer> weeksFX = FXCollections.observableArrayList();
        for (Week week : weeks) {
            weeksFX.add(week.getNumber());
        }
        weeksChoice.setItems(weeksFX);
        ArrayList<Specialization> specs = queries.getAllSpecializations();
        ObservableList<String> specFX = FXCollections.observableArrayList();
        for (Specialization spec : specs) {
            specFX.add(spec.getName());
        }
        specChoice.setItems(specFX);

        ArrayList<Integer> courses = queries.getAllCourses();
        ObservableList<Integer> coursesFX = FXCollections.observableArrayList();
        for (Integer course : courses) {
            coursesFX.add(course);
        }
        courseChoice.setItems(coursesFX);

        ArrayList<Discipline> disciplines = queries.getAllDisciplines();
        ObservableList<String> disciplinesFX = FXCollections.observableArrayList();
        for (Discipline discipline : disciplines) {
            disciplinesFX.add(discipline.getName());
        }
        scheduleChoice.setItems(disciplinesFX);



    }





    @FXML
    public void showButtonCLicked(ActionEvent event){
        ArrayList<Day> days = queries.getAllDays();
        HashMap<Day,ArrayList<Schedule>> dayArrayListHashMap = initHashMap();
        System.out.println(lecturerTables);
        lecturerTables.clear();
        System.out.println(lecturerTables);

        lecturerTable.setItems(lecturerTables);
        for (Day day: days) {
            LecturerTable lectTable = new LecturerTable(day, dayArrayListHashMap.get(day));
            lecturerTables.add(lectTable);
        }
        System.out.println(lecturerTables);
        lecturerTable.setItems(lecturerTables);


    }

    private HashMap<Day,ArrayList<Schedule>> initHashMap(){
        queries = new DBQueriesImpl();
        Lecturer lect = queries.getLecturerByName(lecturerNameChoice.getSelectionModel().getSelectedItem());
        Week wee = new Week();
        Specialization spec = new Specialization();
        Discipline disp = new Discipline();
        Integer cours = null;

        try{
            wee = queries.getWeekByNumber(weeksChoice.getSelectionModel().getSelectedItem());
        }
        catch (NullPointerException e) {

        }
        try{
            spec = queries.getSpecializationByName(specChoice.getSelectionModel().getSelectedItem());
        }
        catch (NullPointerException e) {

        }try{
            cours =  courseChoice.getSelectionModel().getSelectedItem();
        }
        catch (NullPointerException e) {


        }try{
            disp = queries.getDisciplineByName(scheduleChoice.getSelectionModel().getSelectedItem());
        }
        catch (NullPointerException e) {
            ;
        }

        ArrayList<Schedule> scedule = queries.getScheduleByLecturerAndWeekAndSpecAndCourseAndDiscipline(lect,wee,spec,cours,disp);
        System.out.println(scedule);
        ArrayList<Day> days = queries.getAllDays();
        HashMap<Day,ArrayList<Schedule>> dayScheduleHashMap = new HashMap<>();
        for (Day day : days) {
            dayScheduleHashMap.put(day,new ArrayList<Schedule>());
        }
        for (Schedule schedule: scedule) {
            Day day = schedule.getDay();
            dayScheduleHashMap.get(day).add(schedule);
        }

        return dayScheduleHashMap;
    }
}
