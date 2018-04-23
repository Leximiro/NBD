package controller;

import db.DBQueriesImpl;
import entity.Schedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import parser.InvalidInputFileException;
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
    private Controller main;



    @FXML
    private Button uploadButton;

    @FXML
    private Text bdcounter;
    @FXML
    Label wait;

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




    private void initData() {

        try {
            queries = new DBQueriesImpl();
            ArrayList<Schedule> schedules = queries.getScheduleErrors();
            //ArrayList<Schedule> schedules = queries.getScheduleByLecturerAndWeekAndSpecAndCourseAndDiscipline(null, null, null, null, null);
            for (Schedule sced : schedules) {
                UploadTable tableUpload = new UploadTable(sced);
                scheduleErrors.add(tableUpload);
            }
        }
        catch(NullPointerException e)
        {
            System.out.print("NullPointerException caught");
        }
        bdcounter.setText("\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u0456\u0432: "+queries.getNumberOfClasses());
    }



    public void uploadSchedule(javafx.event.ActionEvent actionEvent) throws IOException, InvalidInputFileException {
        System.out.println("upload");
        wait.setVisible(true);

        queries = new DBQueriesImpl();
        DirectoryChooser chooser = new DirectoryChooser();
        File showDialog = chooser.showDialog(new Stage());
        String path = "";
        try {
            path = showDialog.getPath();
        }catch (NullPointerException e){
            return;
        }
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
            try{
                ExcelParser.parse(curFilePath, queries);
            }catch (InvalidInputFileException e){
                String message = "\u0423 \u0444\u0430\u0439\u043b\u0456 "+fileName+" ";
                Alert alert = new Alert(Alert.AlertType.NONE,message+ e.getMessage(), ButtonType.OK);
                alert.setTitle("\u041f\u043e\u043c\u0438\u043b\u043a\u0438 \u043f\u0440\u0438 \u0437\u0430\u0432\u0430\u043d\u0442\u0430\u0436\u0435\u043d\u043d\u0456");
                alert.showAndWait();
                e.getMessage();
            }

        }
//        lecturerController.choiceBoxesFill();
//        studentController.choiceBoxesFill();
//        laborantController.choiceBoxFill();
        scheduleErrors.clear();
        tableErrors.setItems(scheduleErrors);
        initData();
        tableErrors.setItems(scheduleErrors);
        bdcounter.setText("\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u0456\u0432: "+queries.getNumberOfClasses());
        main.refreshCheckBoxes();
        wait.setVisible(false);


    }

    public void deleteSchedule(javafx.event.ActionEvent actionEvent) {
        queries.cleanDB();
        scheduleErrors.clear();
        tableErrors.setItems(scheduleErrors);
        initData();
        tableErrors.setItems(scheduleErrors);
        bdcounter.setText("\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u0456\u0432: "+queries.getNumberOfClasses());
        main.refreshCheckBoxes();
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

    public void init(Controller controller) {
        main = controller;
        initData();
        checkButton.setVisible(false);
        wait.setVisible(false);

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
        tableErrors.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

        tableErrors.setItems(scheduleErrors);
    }
}