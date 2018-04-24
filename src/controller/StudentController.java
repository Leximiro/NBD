package controller;

import db.DBQueriesImpl;
import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    ChoiceBox<Integer> weeksStudent;


    @FXML
    private Button showChoiceStudent;
    @FXML
    private Button downloadStudent;


    @FXML
    private TableView<StudentTable> studentTable;

    @FXML
    private TableColumn<StudentTable, Integer> periodStudent;

    @FXML
    private TableColumn<StudentTable, String> mondayStudent;

    @FXML
    private TableColumn<StudentTable, String> tuesdayStudent;

    @FXML
    private TableColumn<StudentTable, String> wednesdayStudent;

    @FXML
    private TableColumn<StudentTable, String> thursdayStudent;

    @FXML
    private TableColumn<StudentTable, String> fridayStudent;

    @FXML
    private TableColumn<StudentTable, String> saturdayStudent;



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
        ArrayList<Week> weeks = queries.getAllWeeks();
        ObservableList<Integer> weeksFX = FXCollections.observableArrayList();
        weeksFX.add(null);
        for (Week week : weeks) {
            weeksFX.add(week.getNumber());
        }
        weeksStudent.setItems(weeksFX);



    }





    public void showTableStudent(ActionEvent actionEvent) {
        queries = new DBQueriesImpl();
        ArrayList<Period> periods = queries.getAllPeriods();
        HashMap<Period,ArrayList<Schedule>> dayArrayListHashMap = initHashMap();
        System.out.println(studentTables);
        studentTables.clear();
        System.out.println(studentTables);

        studentTable.setItems(studentTables);
        for (Period period: periods) {
            StudentTable studTable = new StudentTable(period, dayArrayListHashMap.get(period));
            studentTables.add(studTable);
        }
        System.out.println(studentTables);
        studentTable.setItems(studentTables);
    }

    private HashMap<Period,ArrayList<Schedule>> initHashMap() {
        queries = new DBQueriesImpl();
        HashMap<Period,ArrayList<Schedule>> dayScheduleHashMap = new HashMap<>();
        Specialization spec = null;
        Discipline disp = null;
        Integer cours = null;
        Week wee = null;

        try{
            wee = queries.getWeekByNumber(weeksStudent.getSelectionModel().getSelectedItem());
        }
        catch (NullPointerException e) {

        }

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

        ArrayList<Schedule> scedule = queries.getScheduleByLecturerAndWeekAndSpecAndCourseAndDiscipline(null,wee,spec,cours,disp);
        System.out.println(scedule);
        ArrayList<Period> periods = queries.getAllPeriods();
        for (Period period : periods) {
            dayScheduleHashMap.put(period,new ArrayList<Schedule>());
        }
        for (Schedule schedule: scedule) {
            Period period = schedule.getPeriod();
            dayScheduleHashMap.get(period).add(schedule);
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
        ArrayList<Integer> errors = queries.getScheduleIdsWithErrors();
        DirectoryChooser chooser = new DirectoryChooser();
        File showDialog = chooser.showDialog(new Stage());
        String path ="";

        try {
            path = showDialog.getPath()+"/"+spec.getName()+".xlsx";
        }catch (NullPointerException e){
            return;
        }
        if(scedule.size()>0)
            StudentExport.export(spec,cours,scedule.get(1).getWeeks().get(0),scedule,errors,path);


    }


    public void init(Controller controller) {


        choiceBoxesFill();
        periodStudent.setCellValueFactory(new PropertyValueFactory<StudentTable, Integer>("period"));
        mondayStudent.setCellValueFactory(new PropertyValueFactory<StudentTable, String>("monday"));
        tuesdayStudent.setCellValueFactory(new PropertyValueFactory<StudentTable, String>("tuesday"));
        wednesdayStudent.setCellValueFactory(new PropertyValueFactory<StudentTable, String>("wednesday"));
        thursdayStudent.setCellValueFactory(new PropertyValueFactory<StudentTable, String>("thursday"));
        fridayStudent.setCellValueFactory(new PropertyValueFactory<StudentTable, String>("friday"));
        saturdayStudent.setCellValueFactory(new PropertyValueFactory<StudentTable, String>("saturday"));
        studentTable.setId("table-row-cell");
        studentTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        studentTable.setItems(studentTables);
    }
}
