package controller;

import db.DBQueriesImpl;
import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import table.LaborantTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LaborantController {
    private ObservableList<LaborantTable> laborantTables = FXCollections.observableArrayList();
    private DBQueriesImpl queries ;

    @FXML
    ChoiceBox<Integer> buildingChoice;
    @FXML
    CheckBox computers;
    @FXML
    CheckBox projector;
    @FXML
    CheckBox board;



    @FXML
    private Button classroomShowBtn;

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



    @FXML
    private void initialize() {
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
        laborantTable.setItems(laborantTables);

    }

    private void choiceBoxFill() {

        queries = new DBQueriesImpl();
        ArrayList<Integer> buildings = queries.getAllBuildings();
        ObservableList<Integer> buildingsFX = FXCollections.observableArrayList();
        buildingsFX.add(null);
        for (Integer building : buildings) {
            buildingsFX.add(building);
        }
        buildingChoice.setItems(buildingsFX);


    }

    public void showTableLaborant(ActionEvent actionEvent) {

        Integer buildings = buildingChoice.getSelectionModel().getSelectedItem();

        ArrayList<Day> days = queries.getAllDays();
        ArrayList<Classroom> classrooms = getClassroomsByBuilding(buildings);
        HashMap<Classroom,ArrayList<Schedule>> classroomArrayListHashMap = initHashMapArrayList();
        laborantTables.clear();
        laborantTable.setItems(laborantTables);
        System.out.println(classroomArrayListHashMap);

        for (Classroom classroom: classrooms) {
            for (Day day:days) {


                ArrayList<Schedule> schedules = sortByDay(day,classroomArrayListHashMap.get(classroom));
                System.out.println("pair" + schedules);



                LaborantTable studTable = new LaborantTable(classroom,day,schedules);
                laborantTables.add(studTable);
            }
        }
        System.out.println(laborantTables);

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
        System.out.println(res);

        return res;
    }

    private HashMap<Classroom,ArrayList<Schedule>> initHashMapArrayList() {

        HashMap<Classroom,ArrayList<Schedule>> dayScheduleHashMap = new HashMap<>();
        boolean computer = computers.isSelected() ;
        boolean projectors = projector.isSelected();
        boolean boards = board.isSelected();
        Integer buildings = buildingChoice.getSelectionModel().getSelectedItem();
        System.out.println(""+computer+projectors+boards+buildings);


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
}
