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
import parser.LabmanagerExport;
import parser.LecturerExport;
import table.LaborantTable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LaborantController {
    private ObservableList<LaborantTable> laborantTables = FXCollections.observableArrayList();
    private DBQueriesImpl queries ;
    private Controller main;


    @FXML
    ChoiceBox<Integer> buildingChoice;
    @FXML
    ChoiceBox<String> computers;
    @FXML
    ChoiceBox<String> projector;
    @FXML
    ChoiceBox<String> board;
    @FXML
    ChoiceBox<Integer> weeksLab;



    @FXML
    private Button classroomShowBtn;
    @FXML
    private Button downloadLab;

    @FXML
    private TableView<LaborantTable> laborantTable;

    @FXML
    private TableColumn<LaborantTable, String> classroomLab;

    @FXML
    private TableColumn<LaborantTable, Integer> periodLab;

    @FXML
    private TableColumn<LaborantTable, String> mondayLab;

    @FXML
    private TableColumn<LaborantTable, String> tuesdayLab;

    @FXML
    private TableColumn<LaborantTable, String> wednesdayLab;

    @FXML
    private TableColumn<LaborantTable, String> thursdayLab;

    @FXML
    private TableColumn<LaborantTable, String> fridayLab;

    @FXML
    private TableColumn<LaborantTable, String> saturdayLab;




    public void choiceBoxFill() {
        buildingChoice.getItems().clear();


        queries = new DBQueriesImpl();
        ArrayList<Week> weeks = queries.getAllWeeks();
        ObservableList<Integer> weeksFX = FXCollections.observableArrayList();
        weeksFX.add(null);
        for (Week week : weeks) {
            weeksFX.add(week.getNumber());
        }
        weeksLab.setItems(weeksFX);
        ArrayList<Integer> buildings = queries.getAllBuildings();
        ObservableList<Integer> buildingsFX = FXCollections.observableArrayList();
        buildingsFX.add(null);
        for (Integer building : buildings) {
            buildingsFX.add(building);
        }
        buildingChoice.setItems(buildingsFX);
        ObservableList<String> computer = FXCollections.observableArrayList();
        computer.add("\u0412\u0441\u0456");
        computer.add("\u0404");
        computer.add("\u041d\u0435\u043c\u0430\u0454");
        ObservableList<String> projectors = FXCollections.observableArrayList();
        projectors.add("\u0412\u0441\u0456");
        projectors.add("\u0404");
        projectors.add("\u041d\u0435\u043c\u0430\u0454");
        ObservableList<String> boards = FXCollections.observableArrayList();
        boards.add("\u0412\u0441\u0456");
        boards.add("\u0404");
        boards.add("\u041d\u0435\u043c\u0430\u0454");
        computers.setItems(computer);
        board.setItems(boards);
        projector.setItems(projectors);


    }

    public void showTableLaborant(ActionEvent actionEvent) {

        Integer buildings = buildingChoice.getSelectionModel().getSelectedItem();
        Integer weeks = weeksLab.getSelectionModel().getSelectedItem();

        ArrayList<Period> periods = queries.getAllPeriods();
        ArrayList<Classroom> classrooms = getClassroomsByBuilding(buildings);
        HashMap<Classroom,ArrayList<Schedule>> classroomArrayListHashMap = initHashMapArrayList();
        laborantTables.clear();
        laborantTable.setItems(laborantTables);
        for (Period period:periods) {
            for (Classroom classroom: classrooms) {

                ArrayList<Schedule> schedules = sortByPeriod(period,classroomArrayListHashMap.get(classroom));
                if(weeks!=null){
                    schedules = weekFilter(weeks,schedules);
                }
                LaborantTable studTable = new LaborantTable(classroom,period,schedules);
                laborantTables.add(studTable);
            }
        }
        laborantTable.setItems(laborantTables);
    }

    private ArrayList<Schedule> weekFilter(Integer weeks,ArrayList<Schedule> schedules){
        ArrayList<Schedule> sced = new ArrayList<>();
        Week week = queries.getWeekByNumber(weeks);
        for (Schedule schedule: schedules) {
            if(schedule.getWeeks().contains(week)){
                sced.add(schedule);
            }
        }

        return sced;
    }
    private ArrayList<Classroom> getClassroomsByBuilding(Integer buildings) {
        ArrayList<Schedule> schedule = queries.getScheduleByBuildingAndClassroomTypeAndClassroomNumber(buildings,null,null,null,null);
        HashSet<Classroom> classrooms = new HashSet<>();
        for (Schedule sced: schedule) {

                classrooms.add(sced.getClassroom());


        }
        ArrayList<Classroom> classrooms1 = new ArrayList<>(classrooms);
        return classrooms1;

    }


    private ArrayList<Schedule> sortByPeriod(Period period, ArrayList<Schedule> schedules){
        ArrayList<Schedule> res = new ArrayList<>();
        for (Schedule schedule: schedules) {

            if( schedule.getPeriod().getId()==period.getId()){

                res.add(schedule);
            }
        }


        return res;
    }

    private HashMap<Classroom,ArrayList<Schedule>> initHashMapArrayList() {

        HashMap<Classroom,ArrayList<Schedule>> dayScheduleHashMap = new HashMap<>();
        Boolean computer = null;
        Boolean projectors = null;
        Boolean boards = null ;
        try {
            if (computers.getSelectionModel().getSelectedItem().equals("\u0404")) {
                computer = true;

            }
            if(computers.getSelectionModel().getSelectedItem().equals("\u041d\u0435\u043c\u0430\u0454")){
                computer = false;
            }
        }catch (NullPointerException e){

        }
        try {
            if (projector.getSelectionModel().getSelectedItem().equals("\u0404")) {
                projectors = true;

            }
            if (projector.getSelectionModel().getSelectedItem().equals("\u041d\u0435\u043c\u0430\u0454")) {
                projectors = false;
            }
        } catch (NullPointerException e){

        }

        try {
            if (board.getSelectionModel().getSelectedItem().equals("\u0404")) {
                boards = true;

            }

            if (board.getSelectionModel().getSelectedItem().equals("\u041d\u0435\u043c\u0430\u0454")) {
                boards = false;
            }
        } catch (NullPointerException e){

        }

        Integer buildings = buildingChoice.getSelectionModel().getSelectedItem();

        ArrayList<Schedule> scedule = queries.getScheduleByBuildingAndClassroomTypeAndClassroomNumber(buildings,boards,computer,projectors,null);

        ArrayList<Classroom> classrooms = queries.getAllClassrooms();
        for (Classroom classroom : classrooms) {
            dayScheduleHashMap.put(classroom,new ArrayList<Schedule>());
        }
        for (Schedule schedule: scedule) {
            Classroom classroom = schedule.getClassroom();
            dayScheduleHashMap.get(classroom).add(schedule);
        }



        return dayScheduleHashMap;
    }

    public void downloadLabBtn(ActionEvent actionEvent) {

        Boolean computer = null;
        Boolean projectors = null;
        Boolean boards = null ;
        Integer week = weeksLab.getSelectionModel().getSelectedItem();;
        try {
            if (computers.getSelectionModel().getSelectedItem().equals("\u0404")) {
                computer = true;

            }
            if(computers.getSelectionModel().getSelectedItem().equals("\u041d\u0435\u043c\u0430\u0454")){
                computer = false;
            }
        }catch (NullPointerException e){

        }
        try {
            if (projector.getSelectionModel().getSelectedItem().equals("\u0404")) {
                projectors = true;

            }
            if (projector.getSelectionModel().getSelectedItem().equals("\u041d\u0435\u043c\u0430\u0454")) {
                projectors = false;
            }
        } catch (NullPointerException e){

        }

        try {
            if (board.getSelectionModel().getSelectedItem().equals("\u0404")) {
                boards = true;

            }

            if (board.getSelectionModel().getSelectedItem().equals("\u041d\u0435\u043c\u0430\u0454")) {
                boards = false;
            }
        } catch (NullPointerException e){

        }
        Integer buildings = buildingChoice.getSelectionModel().getSelectedItem();
        ArrayList<Schedule> schedules = queries.getScheduleByBuildingAndClassroomTypeAndClassroomNumber(buildings,boards,computer,projectors,null);
        ArrayList<Integer> errors = queries.getScheduleIdsWithErrors();
        DirectoryChooser chooser = new DirectoryChooser();
        File showDialog = chooser.showDialog(new Stage());
        Week wee = null;
        if(week!=null){
            wee = queries.getWeekByNumber(week);
            schedules = weekFilter(week,schedules);
        }
        String path ="";

        try {
            path = showDialog.getPath()+"/"+buildings+"schedule.xlsx";
        }catch (NullPointerException e){
            return;
        }
        LabmanagerExport.export(wee,schedules,errors,path);
    }

    public void init(Controller controller) {
        main = controller;

        choiceBoxFill();
        classroomLab.setCellValueFactory(new PropertyValueFactory<LaborantTable,String>("classroom"));
        periodLab.setCellValueFactory(new PropertyValueFactory<LaborantTable, Integer>("period"));
        mondayLab.setCellValueFactory(new PropertyValueFactory<LaborantTable, String>("monday"));
        tuesdayLab.setCellValueFactory(new PropertyValueFactory<LaborantTable, String>("tuesday"));
        wednesdayLab.setCellValueFactory(new PropertyValueFactory<LaborantTable, String>("wednesday"));
        thursdayLab.setCellValueFactory(new PropertyValueFactory<LaborantTable, String>("thursday"));
        fridayLab.setCellValueFactory(new PropertyValueFactory<LaborantTable, String>("friday"));
        saturdayLab.setCellValueFactory(new PropertyValueFactory<LaborantTable, String>("saturday"));
        laborantTable.setId("table-row-cell");
        laborantTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        laborantTable.setItems(laborantTables);

    }
}
