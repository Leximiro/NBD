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
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import parser.StudentExport;
import table.StudentTable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class StudentController {

    private ObservableList<StudentTable> studentTables = FXCollections.observableArrayList();
    private DBQueriesImpl queries ;
    private Controller main;




    @FXML
    ChoiceBox<String> specChoiceStudent;
    @FXML
    ChoiceBox<Integer> courseChoiceStudent;
    @FXML
    ChoiceBox<String> lessonChoiceStudent;


    @FXML
    private Button showChoiceStudent;
    @FXML
    private Button downloadStudent;


    @FXML
    private TableView<StudentTable> studentTable;

    @FXML
    private TableColumn<StudentTable, String> lessonStudent;

    @FXML
    private TableColumn<StudentTable, String> lesson1Student;

    @FXML
    private TableColumn<StudentTable, String> lesson2Student;

    @FXML
    private TableColumn<StudentTable, String> lesson3Student;

    @FXML
    private TableColumn<StudentTable, String> lesson4Student;

    @FXML
    private TableColumn<StudentTable, String> lesson5Student;

    @FXML
    private TableColumn<StudentTable, String> lesson6Student;

    @FXML
    private TableColumn<StudentTable, String> lesson7Student;



    public void choiceBoxesFill() {
        specChoiceStudent.getItems().clear();
        courseChoiceStudent.getItems().clear();
        lessonChoiceStudent.getItems().clear();
        queries = new DBQueriesImpl();
        ArrayList<Specialization> specs = queries.getAllSpecializations();
        ObservableList<String> specFX = FXCollections.observableArrayList();
        specFX.add(null);
        for (Specialization spec : specs) {
            specFX.add(spec.getName());
        }
        specChoiceStudent.setItems(specFX);

        ArrayList<Integer> courses = queries.getAllCourses();
        ObservableList<Integer> coursesFX = FXCollections.observableArrayList();
        coursesFX.add(null);
        for (Integer course : courses) {
            coursesFX.add(course);
        }
        courseChoiceStudent.setItems(coursesFX);

        ArrayList<Discipline> disciplines = queries.getAllDisciplines();
        ObservableList<String> disciplinesFX = FXCollections.observableArrayList();
        disciplinesFX.add(null);
        for (Discipline discipline : disciplines) {
            disciplinesFX.add(discipline.getName());
        }
        lessonChoiceStudent.setItems(disciplinesFX);



    }





    public void showTableStudent(ActionEvent actionEvent) {
        queries = new DBQueriesImpl();
        ArrayList<Day> days = queries.getAllDays();
        HashMap<Day,ArrayList<Schedule>> dayArrayListHashMap = initHashMap();
        System.out.println(studentTables);
        studentTables.clear();
        System.out.println(studentTables);

        studentTable.setItems(studentTables);
        for (Day day: days) {
            StudentTable studTable = new StudentTable(day, dayArrayListHashMap.get(day));
            studentTables.add(studTable);
        }
        System.out.println(studentTables);
        studentTable.setItems(studentTables);
    }

    private HashMap<Day,ArrayList<Schedule>> initHashMap() {
        queries = new DBQueriesImpl();
        HashMap<Day,ArrayList<Schedule>> dayScheduleHashMap = new HashMap<>();
        Specialization spec = null;
        Discipline disp = null;
        Integer cours = null;

        try{
            spec = queries.getSpecializationByName(specChoiceStudent.getSelectionModel().getSelectedItem());
        }
        catch (NullPointerException e) {

        }try{
            cours =  courseChoiceStudent.getSelectionModel().getSelectedItem();
        }
        catch (NullPointerException e) {


        }try{
            disp = queries.getDisciplineByName(lessonChoiceStudent.getSelectionModel().getSelectedItem());
        }
        catch (NullPointerException e) {
            ;
        }

        ArrayList<Schedule> scedule = queries.getScheduleByLecturerAndWeekAndSpecAndCourseAndDiscipline(null,null,spec,cours,disp);
        System.out.println(scedule);
        ArrayList<Day> days = queries.getAllDays();
        for (Day day : days) {
            dayScheduleHashMap.put(day,new ArrayList<Schedule>());
        }
        for (Schedule schedule: scedule) {
            Day day = schedule.getDay();
            dayScheduleHashMap.get(day).add(schedule);
        }



        return dayScheduleHashMap;

    }


    public void downloadStudentBtn(ActionEvent actionEvent) {

        queries = new DBQueriesImpl();
        Specialization spec = null;
        Discipline disp = null;
        Integer cours = null;

        try{
            spec = queries.getSpecializationByName(specChoiceStudent.getSelectionModel().getSelectedItem());
        }
        catch (NullPointerException e) {

        }try{
            cours =  courseChoiceStudent.getSelectionModel().getSelectedItem();
        }
        catch (NullPointerException e) {


        }try{
            disp = queries.getDisciplineByName(lessonChoiceStudent.getSelectionModel().getSelectedItem());
        }
        catch (NullPointerException e) {
            ;
        }

        ArrayList<Schedule> scedule = queries.getScheduleByLecturerAndWeekAndSpecAndCourseAndDiscipline(null,null,spec,cours,disp);
        DirectoryChooser chooser = new DirectoryChooser();
        File showDialog = chooser.showDialog(new Stage());
        String path = showDialog.getPath()+"/"+spec.getName()+".xlsx";
        if(scedule.size()>0)
            StudentExport.export(spec,cours,scedule.get(1).getWeeks().get(0),scedule,path);


    }

    public void init(Controller controller) {


        choiceBoxesFill();
        lessonStudent.setCellValueFactory(new PropertyValueFactory<StudentTable, String>("lesson"));
        lesson1Student.setCellValueFactory(new PropertyValueFactory<StudentTable, String>("lesson1"));
        lesson2Student.setCellValueFactory(new PropertyValueFactory<StudentTable, String>("lesson2"));
        lesson3Student.setCellValueFactory(new PropertyValueFactory<StudentTable, String>("lesson3"));
        lesson4Student.setCellValueFactory(new PropertyValueFactory<StudentTable, String>("lesson4"));
        lesson5Student.setCellValueFactory(new PropertyValueFactory<StudentTable, String>("lesson5"));
        lesson6Student.setCellValueFactory(new PropertyValueFactory<StudentTable, String>("lesson6"));
        lesson7Student.setCellValueFactory(new PropertyValueFactory<StudentTable, String>("lesson7"));
        studentTable.setId("table-row-cell");
        studentTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        studentTable.setItems(studentTables);
    }
}
