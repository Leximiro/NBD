package parser;

import db.DBQueries;
import db.DBQueriesImpl;
import entity.*;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class LecturerExport {

    public static void export(Lecturer lecturer, Week week, ArrayList<Schedule> schedules, String path){

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("Sheet 1");

        XSSFCellStyle style = workbook.createCellStyle();
        style.setWrapText(true);


        ArrayList<Period> periods = getPeriods(schedules);
        ArrayList<Day> days = getDays(schedules);

        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        row.setRowStyle(style);
        row.createCell(0).setCellValue(lecturer.getName());
        row.getCell(0).setCellStyle(style);
        row.createCell(1).setCellValue(week == null ? "" : "Week " + week.getNumber());
        row.getCell(1).setCellStyle(style);

        row = sheet.createRow(++rowNum);
        row.setRowStyle(style);
        for (int t = 0; t < periods.size(); t++){
            row.createCell(t + 1).setCellValue(periods.get(t).getNumber());
            row.getCell(t + 1).setCellStyle(style);
        }
        for (int i = 0; i < days.size(); i++){
            row = sheet.createRow(++rowNum);
            row.setRowStyle(style);
            row.createCell(0).setCellValue(days.get(i).getName());
            row.getCell(0).setCellStyle(style);
            for (int j = 0; j < periods.size(); j++){
                for (Schedule schedule : schedules){
                    if (schedule.getDay().equals(days.get(i)) && schedule.getPeriod().equals(periods.get(j))){
                        StringBuilder output = new StringBuilder();
                        output.append(schedule.getClassroom().getBuilding() + "-" + schedule.getClassroom().getNumber() + "\n");
                        output.append(schedule.getDiscipline().getName() + "\n");
                        output.append(schedule.getClassType().getName().equalsIgnoreCase
                                ("лекція") ?  schedule.getClassType().getName() + "\n" : "Група " + schedule.getGroup() + "\n");
                        output.append(schedule.getSpecialization().getName() + "-" + schedule.getYear() + "\n");
                        row.createCell(j + 1).setCellValue(output.toString());
                        row.getCell(j + 1).setCellStyle(style);
                    }
                }
            }
        }

        for (int i = 0; i <= periods.size(); i++){
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream out = new FileOutputStream(new File(path))) {
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Period> getPeriods(ArrayList<Schedule> schedules){
        ArrayList<Period> result = new ArrayList<Period>();
        for (Schedule schedule : schedules){
            if (!result.contains(schedule.getPeriod())){
                result.add(schedule.getPeriod());
            }
        }
        return result;
    }

    private static ArrayList<Day> getDays(ArrayList<Schedule> schedules){
        ArrayList<Day> result = new ArrayList<Day>();
        for (Schedule schedule : schedules){
            if (!result.contains(schedule.getDay())){
                result.add(schedule.getDay());
            }
        }
        return result;
    }

    public static void main(String[] args) {
        DBQueries queries = new DBQueriesImpl();
        ArrayList<Schedule> schedules = queries.getScheduleByLecturerAndWeekAndSpecAndCourseAndDiscipline
                (null, null, null, null, null);
        export(new Lecturer("Pupkin", "aspirant"), new Week(8, 8), schedules, "E:\\report.xlsx");
    }

}
