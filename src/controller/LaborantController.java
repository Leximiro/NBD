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
    private Button classroomShowBtn;
    @FXML
    private Button downloadLab;

    @FXML
    private TableView<LaborantTable> laborantTable;

    @FXML
    private TableColumn<LaborantTable, String> classroomLab;

    @FXML
    private TableColumn<LaborantTable, String> dayLab;

    @FXML
    private TableColumn<LaborantTable, String> lesson1Lab;

    @FXML
    private TableColumn<LaborantTable, String> lesson2Lab;

    @FXML
    private TableColumn<LaborantTable, String> lesson3Lab;

    @FXML
    private TableColumn<LaborantTable, String> lesson4Lab;

    @FXML
    private TableColumn<LaborantTable, String> lesson5Lab;

    @FXML
    private TableColumn<LaborantTable, String> lesson6Lab;

    @FXML
    private TableColumn<LaborantTable, String> lesson7Lab;



    public void choiceBoxFill() {
        buildingChoice.getItems().clear();

        queries = new DBQueriesImpl();
        ArrayList<Integer> buildings = queries.getAllBuildings();
        ObservableList<Integer> buildingsFX = FXCollections.observableArrayList();
        buildingsFX.add(null);
        for (Integer building : buildings) {
            buildingsFX.add(building);
        }
        buildingChoice.setItems(buildingsFX);
        ObservableList<String> computer = FXCollections.observableArrayList();
        computer.add("Будь-які");
        computer.add("Є");
        computer.add("Немає");
        ObservableList<String> projectors = FXCollections.observableArrayList();
        projectors.add("Будь-які");
        projectors.add("Є");
        projectors.add("Немає");
        ObservableList<String> boards = FXCollections.observableArrayList();
        boards.add("Будь-які");
        boards.add("Є");
        boards.add("Немає");
        computers.setItems(computer);
        board.setItems(boards);
        projector.setItems(projectors);


    }

    public void showTableLaborant(ActionEvent actionEvent) {

        Integer buildings = buildingChoice.getSelectionModel().getSelectedItem();

        ArrayList<Day> days = queries.getAllDays();
        ArrayList<Classroom> classrooms = getClassroomsByBuilding(buildings);
        HashMap<Classroom,ArrayList<Schedule>> classroomArrayListHashMap = initHashMapArrayList();
        laborantTables.clear();
        laborantTable.setItems(laborantTables);
        for (Classroom classroom: classrooms) {
            for (Day day:days) {

                ArrayList<Schedule> schedules = sortByDay(day,classroomArrayListHashMap.get(classroom));
          LaborantTable studTable = new LaborantTable(classroom,day,schedules);
                laborantTables.add(studTable);
            }
        }
        laborantTable.setItems(laborantTables);
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


    private ArrayList<Schedule> sortByDay(Day day, ArrayList<Schedule> schedules){
        ArrayList<Schedule> res = new ArrayList<>();
        for (Schedule schedule: schedules) {

            if( schedule.getDay().getId()==day.getId()){

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
            if (computers.getSelectionModel().getSelectedItem().equals("Є")) {
                computer = true;

            }
            if(computers.getSelectionModel().getSelectedItem().equals("Немає")){
                computer = false;
            }
        }catch (NullPointerException e){

        }
        try {
            if (projector.getSelectionModel().getSelectedItem().equals("Є")) {
                projectors = true;

            }
            if (projector.getSelectionModel().getSelectedItem().equals("Немає")) {
                projectors = false;
            }
        } catch (NullPointerException e){

        }

        try {
            if (board.getSelectionModel().getSelectedItem().equals("Є")) {
                boards = true;

            }

            if (board.getSelectionModel().getSelectedItem().equals("Немає")) {
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
        if(computers.getSelectionModel().getSelectedItem().equals("Є")){
            computer = true;

        }
        if(computers.getSelectionModel().getSelectedItem().equals("Немає")){
            computer = false;
        }
        Boolean projectors = null;
        if(projector.getSelectionModel().getSelectedItem().equals("Є")){
            projectors = true;

        }
        if(projector.getSelectionModel().getSelectedItem().equals("Немає")){
            projectors = false;
        }
        Boolean boards = null ;
        if(board.getSelectionModel().getSelectedItem().equals("Є")){
            boards = true;

        }
        if(board.getSelectionModel().getSelectedItem().equals("Немає")){
            boards = false;
        }
        Integer buildings = buildingChoice.getSelectionModel().getSelectedItem();
        ArrayList<Schedule> schedules = queries.getScheduleByBuildingAndClassroomTypeAndClassroomNumber(buildings,boards,computer,projectors,null);
        DirectoryChooser chooser = new DirectoryChooser();
        File showDialog = chooser.showDialog(new Stage());
        Week wee = queries.getWeekByNumber(1);
        String path = showDialog.getPath()+"/"+buildings+".xlsx";
        LabmanagerExport.export(wee,schedules,path);
    }

    public void init(Controller controller) {
        main = controller;

        choiceBoxFill();
        classroomLab.setCellValueFactory(new PropertyValueFactory<LaborantTable,String>("classroom"));
        dayLab.setCellValueFactory(new PropertyValueFactory<LaborantTable, String>("day"));
        lesson1Lab.setCellValueFactory(new PropertyValueFactory<LaborantTable, String>("lesson1"));
        lesson2Lab.setCellValueFactory(new PropertyValueFactory<LaborantTable, String>("lesson2"));
        lesson3Lab.setCellValueFactory(new PropertyValueFactory<LaborantTable, String>("lesson3"));
        lesson4Lab.setCellValueFactory(new PropertyValueFactory<LaborantTable, String>("lesson4"));
        lesson5Lab.setCellValueFactory(new PropertyValueFactory<LaborantTable, String>("lesson5"));
        lesson6Lab.setCellValueFactory(new PropertyValueFactory<LaborantTable, String>("lesson6"));
        lesson7Lab.setCellValueFactory(new PropertyValueFactory<LaborantTable, String>("lesson7"));
        laborantTable.setId("table-row-cell");
        laborantTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        laborantTable.setItems(laborantTables);

    }
}
