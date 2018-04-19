package controller;

import db.DBQueriesImpl;
import entity.Schedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import parser.InvalidInputFileException;
import parser.ParserAlgorithmException;
import table.UploadTable;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import parser.ExcelParser;

public class UploadController {
    private ObservableList<UploadTable> scheduleErrors = FXCollections.observableArrayList();
    private DBQueriesImpl queries ;
    private ExcelParser parser;
    private LecturerController lecturerController;
    private StudentController studentController;
    private LaborantController laborantController;


    @FXML
    private Button uploadButton;

    @FXML
    private Text bdcounter;

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
    private TableColumn<Schedule, Integer> periodError;

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
        periodError.setCellValueFactory(new PropertyValueFactory<Schedule, Integer>("period"));
        classroomError.setCellValueFactory(new PropertyValueFactory<Schedule, String>("classroom"));
        classTypeError.setCellValueFactory(new PropertyValueFactory<Schedule, String>("classType"));
        weekNumberErrors.setCellValueFactory(new PropertyValueFactory<Schedule, Integer>("weekNumber"));

        tableErrors.setItems(scheduleErrors);
    }

    private void initData() {
        try {
            queries = new DBQueriesImpl();
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
        bdcounter.setText("Кількість записів: "+queries.getNumberOfClasses());
    }



    public void uploadSchedule(javafx.event.ActionEvent actionEvent) throws ParserAlgorithmException, IOException, InvalidInputFileException {
        System.out.println("upload");
        queries = new DBQueriesImpl();
        DirectoryChooser chooser = new DirectoryChooser();
        File showDialog = chooser.showDialog(new Stage());
        String path = showDialog.getPath();
        System.out.println(path);
        File folder = new File(path);
        final String[] mask = {".xlsx"};
        String[] fileNames = folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File folder, String name) {
                for (String s : mask)
                    if (name.toLowerCase().endsWith(s)) return true;
                return false;
            }
        });

        for(String fileName : fileNames){
            System.out.println("File: "+fileName + " loaded;");
            File curFile  = new File (path + "\\" + fileName);
            String curFilePath = path + "\\" + fileName;
            ExcelParser.parse(curFilePath, queries);
        }
        lecturerController.choiceBoxesFill();
        studentController.choiceBoxesFill();
        laborantController.choiceBoxFill();

    }

    public void deleteSchedule(javafx.event.ActionEvent actionEvent) {
        queries.cleanDB();
    }

    public void checkSchedule(javafx.event.ActionEvent actionEvent) {
        queries = new DBQueriesImpl();
        scheduleErrors.removeAll();
        ArrayList<Schedule> schedules = queries.getScheduleErrors();
        for (Schedule sced: schedules) {
            UploadTable tableUpload = new UploadTable(sced);
            scheduleErrors.add(tableUpload);
        }
        tableErrors.setItems(scheduleErrors);
    }
}