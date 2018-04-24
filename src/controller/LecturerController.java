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
import parser.LecturerExport;
import table.LecturerTable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class LecturerController {

    private ObservableList<LecturerTable> lecturerTables = FXCollections.observableArrayList();
    private DBQueriesImpl queries ;
    private Controller main;




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
    private Button downloadLect;

    @FXML
    private TableView<LecturerTable> lecturerTable;

    @FXML
    private TableColumn<LecturerTable, Integer> period;

    @FXML
    private TableColumn<LecturerTable, String> monday;

    @FXML
    private TableColumn<LecturerTable, String> tuesday;

    @FXML
    private TableColumn<LecturerTable, String> wednesday;

    @FXML
    private TableColumn<LecturerTable, String> thursday;

    @FXML
    private TableColumn<LecturerTable, String> friday;

    @FXML
    private TableColumn<LecturerTable, String> saturday;





    public void choiceBoxesFill() {
        lecturerNameChoice.getItems().clear();
        weeksChoice.getItems().clear();
        specChoice.getItems().clear();
        courseChoice.getItems().clear();
        scheduleChoice.getItems().clear();
        queries = new DBQueriesImpl();
        ArrayList<Lecturer> lecturers = queries.getAllLecturers();
        ObservableList<String> lecturersFX = FXCollections.observableArrayList();
        for (Lecturer lecturer : lecturers) {
            lecturersFX.add(lecturer.getName());
        }

        lecturerNameChoice.setItems(lecturersFX);
        ArrayList<Week> weeks = queries.getAllWeeks();
        ObservableList<Integer> weeksFX = FXCollections.observableArrayList();
        weeksFX.add(null);
        for (Week week : weeks) {
            weeksFX.add(week.getNumber());
        }
        weeksChoice.setItems(weeksFX);
        ArrayList<Specialization> specs = queries.getAllSpecializations();
        ObservableList<String> specFX = FXCollections.observableArrayList();
        specFX.add(null);
        for (Specialization spec : specs) {
            specFX.add(spec.getName());
        }
        specChoice.setItems(specFX);

        ArrayList<Integer> courses = queries.getAllCourses();
        ObservableList<Integer> coursesFX = FXCollections.observableArrayList();
        coursesFX.add(null);
        for (Integer course : courses) {
            coursesFX.add(course);
        }
        courseChoice.setItems(coursesFX);

        ArrayList<Discipline> disciplines = queries.getAllDisciplines();
        ObservableList<String> disciplinesFX = FXCollections.observableArrayList();
        disciplinesFX.add(null);
        for (Discipline discipline : disciplines) {
            disciplinesFX.add(discipline.getName());
        }
        scheduleChoice.setItems(disciplinesFX);



    }





    @FXML
    public void showButtonCLicked(ActionEvent event){
        ArrayList<Period> periods = queries.getAllPeriods();
        HashMap<Period,ArrayList<Schedule>> dayArrayListHashMap = initHashMap();
        if(lecturerNameChoice.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.NONE,"\u041d\u0435\u043e\u0431\u0445\u0456\u0434\u043d\u043e \u0432\u0438\u0431\u0440\u0430\u0442\u0438 \u0432\u0438\u043a\u043b\u0430\u0434\u0430\u0447\u0430", ButtonType.OK);
            alert.setTitle("\u041e\u0431\u0435\u0440\u0456\u0442\u044c \u0432\u0438\u043a\u043b\u0430\u0434\u0430\u0447\u0430");
            alert.showAndWait();
            return;
        }

        lecturerTables.clear();


        lecturerTable.setItems(lecturerTables);
        for (Period period: periods) {
            System.out.println(dayArrayListHashMap.get(period));
            LecturerTable lectTable = new LecturerTable(period, dayArrayListHashMap.get(period));
            lecturerTables.add(lectTable);
        }
        System.out.println(lecturerTables);
        lecturerTable.setItems(lecturerTables);


    }


    private HashMap<Period,ArrayList<Schedule>> initHashMap(){

        Lecturer lect = queries.getLecturerByName(lecturerNameChoice.getSelectionModel().getSelectedItem());
        Week wee = null;
        Specialization spec = null;
        Discipline disp = null;
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
        ArrayList<Period> periods = queries.getAllPeriods();
        HashMap<Period,ArrayList<Schedule>> dayScheduleHashMap = new HashMap<>();
        for (Period period: periods) {
            dayScheduleHashMap.put(period, new ArrayList<Schedule>());
        }

        for (Schedule schedule: scedule) {
            Period period = schedule.getPeriod();

            dayScheduleHashMap.get(period).add(schedule);
        }

        return dayScheduleHashMap;
    }

    public void downloadLecturer(ActionEvent actionEvent) {
        Lecturer lect = queries.getLecturerByName(lecturerNameChoice.getSelectionModel().getSelectedItem());
        Week wee = null;
        try {
            wee = queries.getWeekByNumber(weeksChoice.getSelectionModel().getSelectedItem());
        }catch (NullPointerException e){

        }
        ArrayList<Schedule> schedules = queries.getScheduleByLecturerAndWeekAndSpecAndCourseAndDiscipline(lect,wee,null,null,null);
        ArrayList<Integer> errors = queries.getScheduleIdsWithErrors();
        DirectoryChooser chooser = new DirectoryChooser();
        File showDialog = chooser.showDialog(new Stage());
        String path ="";

        try {
            path = showDialog.getPath()+"/"+lect.getName()+".xlsx";
        }catch (NullPointerException e){
            return;
        }
        LecturerExport.export(lect,wee,schedules,errors,path);

    }

    public void init(Controller controller) {
        main = controller;

        choiceBoxesFill();

        period.setCellValueFactory(new PropertyValueFactory<LecturerTable, Integer>("period"));
        monday.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("monday"));
        tuesday.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("tuesday"));
        wednesday.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("wednesday"));
        thursday.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("thursday"));
        friday.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("friday"));
        saturday.setCellValueFactory(new PropertyValueFactory<LecturerTable, String>("saturday"));
        lecturerTable.setId("table-row-cell");
        lecturerTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        lecturerTable.setItems(lecturerTables);

    }
}
