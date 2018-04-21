package parser;

import db.DBQueries;
import db.DBQueriesImpl;
import entity.*;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class LabmanagerExport {

    public static void export(Week week, ArrayList<Schedule> oldSchedules, String path){

        HashSet<Schedule> schedules = new HashSet<Schedule>();
        for (Schedule schedule : oldSchedules){
            schedules.add(schedule);
        }

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("Sheet 1");

        XSSFCellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);

        ArrayList<Period> periods = getPeriods(schedules);
        Collections.sort(periods, new PeriodSorter());
        ArrayList<Day> days = getDays(schedules);
        Collections.sort(days, new DaySorter());
        ArrayList<Classroom> classrooms = getClassrooms(schedules);
        Collections.sort(classrooms, new ClassroomSorter());

        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        row.setRowStyle(style);
        for (int t = 0; t < periods.size(); t++){
            row.createCell(t + 2).setCellValue(periods.get(t).getNumber());
            row.getCell(t + 2).setCellStyle(style);
        }
        for (int i = 0; i < classrooms.size(); i++){
            sheet.addMergedRegion(new CellRangeAddress(rowNum + 1,rowNum + days.size(),0,0));
            for (int j = 0; j < days.size(); j++){
                row = sheet.createRow(++rowNum);
                row.setRowStyle(style);
                row.createCell(0).setCellValue(classrooms.get(i).getBuilding() + "-" + classrooms.get(i).getNumber());
                row.getCell(0).setCellStyle(style);
                row.createCell(1).setCellValue(days.get(j).getName());
                row.getCell(1).setCellStyle(style);
                for (int k = 0; k < periods.size(); k++){
                    StringBuilder output = new StringBuilder();
                    for (Schedule schedule : schedules){
                        if (schedule.getClassroom().equals(classrooms.get(i)) && schedule.getDay().equals(days.get(j))
                                && schedule.getPeriod().equals(periods.get(k))){
                            output.append(schedule.getLecturer().getDegree() + " " + schedule.getLecturer().getName() + "\n");
                            output.append(schedule.getDiscipline().getName() + "\n");
                            output.append(schedule.getClassType().getName().equalsIgnoreCase
                                    ("лекція") ?  schedule.getClassType().getName() + "\n" : "Група " + schedule.getGroup() + "\n");
                            output.append(schedule.getSpecialization().getName() + "-" + schedule.getYear() + "\n\n");
                        }
                    }
                    row.createCell(k + 2).setCellValue(output.toString());
                    row.getCell(k + 2).setCellStyle(style);
                }
            }
        }

        for (int i = 0; i < periods.size() + 2; i++){
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream out = new FileOutputStream(new File(path))) {
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Period> getPeriods(HashSet<Schedule> schedules){
        ArrayList<Period> result = new ArrayList<Period>();
        for (Schedule schedule : schedules){
            if (!result.contains(schedule.getPeriod())){
                result.add(schedule.getPeriod());
            }
        }
        return result;
    }

    private static ArrayList<Day> getDays(HashSet<Schedule> schedules){
        ArrayList<Day> result = new ArrayList<Day>();
        for (Schedule schedule : schedules){
            if (!result.contains(schedule.getDay())){
                result.add(schedule.getDay());
            }
        }
        return result;
    }

    private static ArrayList<Classroom> getClassrooms(HashSet<Schedule> schedules){
        ArrayList<Classroom> result = new ArrayList<Classroom>();
        for (Schedule schedule : schedules){
            if (!result.contains(schedule.getClassroom())){
                result.add(schedule.getClassroom());
            }
        }
        return result;
    }

    public static void main(String[] args) {
        DBQueries queries = new DBQueriesImpl();
        ArrayList<Schedule> schedules = queries.getScheduleByLecturerAndWeekAndSpecAndCourseAndDiscipline
                (null, null, null, null, null);
        export(new Week(8, 8), schedules, "E:\\meport.xlsx");
    }

}

class ClassroomSorter implements Comparator<Classroom> {

    public int compare(Classroom one, Classroom another){
        return (one.getBuilding() + "-" + one.getNumber()).compareTo(another.getBuilding() + "-" + another.getNumber());
    }

}