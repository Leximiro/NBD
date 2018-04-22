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

        ArrayList<Period> periods = queries.getAllPeriods();
        ArrayList<Classroom> classrooms = getClassroomsByBuilding(buildings);
        HashMap<Classroom,ArrayList<Schedule>> classroomArrayListHashMap = initHashMapArrayList();
        laborantTables.clear();
        laborantTable.setItems(laborantTables);
        for (Period period:periods) {
            for (Classroom classroom: classrooms) {

                ArrayList<Schedule> schedules = sortByPeriod(period,classroomArrayListHashMap.get(classroom));
                LaborantTable studTable = new LaborantTable(classroom,period,schedules);
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
        ArrayList<Schedule> schedules = queries.getScheduleByBuildingAndClassroomTypeAndClassroomNumber(buildings,boards,computer,projectors,null);
        DirectoryChooser chooser = new DirectoryChooser();
        File showDialog = chooser.showDialog(new Stage());
        Week wee = queries.getWeekByNumber(1);
        String path = showDialog.getPath()+"/"+buildings+"schedule.xlsx";
        LabmanagerExport.export(wee,schedules,path);
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
