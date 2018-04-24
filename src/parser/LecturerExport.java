package parser;

import db.DBQueriesImpl;
import entity.*;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class LecturerExport {

    public static void export(Lecturer lecturer, Week week, ArrayList<Schedule> oldSchedules,  ArrayList<Integer> errors, String path){

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

        XSSFCellStyle errorStyle = workbook.createCellStyle();
        errorStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        errorStyle.setAlignment(HorizontalAlignment.CENTER);
        errorStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        errorStyle.setFont(font);
        errorStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        errorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        ArrayList<Period> periods = getPeriods(schedules);
        Collections.sort(periods, new PeriodSorter());
        ArrayList<Day> days = getDays(schedules);
        Collections.sort(days, new DaySorter());

        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        row.setRowStyle(style);
        row.createCell(0).setCellValue(lecturer == null ? "" : lecturer.getName());
        row.getCell(0).setCellStyle(style);
        row.createCell(1).setCellValue(week == null ? "" : "Week " + week.getNumber());
        row.getCell(1).setCellStyle(style);

        row = sheet.createRow(++rowNum);
        row.setRowStyle(style);
        for (int t = 0; t < days.size(); t++){
            row.createCell(t + 1).setCellValue(days.get(t).getName());
            row.getCell(t + 1).setCellStyle(style);
        }
        for (int i = 0; i < periods.size(); i++){
            row = sheet.createRow(++rowNum);
            row.setRowStyle(style);
            row.createCell(0).setCellValue(periods.get(i).getNumber());
            row.getCell(0).setCellStyle(style);
            for (int j = 0; j < days.size(); j++){
                StringBuilder output = new StringBuilder();
                boolean error = false;
                for (Schedule schedule : schedules){
                    if (schedule.getDay().equals(days.get(j)) && schedule.getPeriod().equals(periods.get(i))){
                        if (errors.contains(schedule.getId())){
                            error = true;
                        }
                        output.append(schedule.getClassroom().getBuilding() + "-" + schedule.getClassroom().getNumber() + "\n");
                        output.append(schedule.getDiscipline().getName() + "\n");
                        output.append(schedule.getClassType().getName().equalsIgnoreCase
                                ("\u043b\u0435\u043a\u0446\u0456\u044f") ?  schedule.getClassType().getName() + "\n" : "\u0413\u0440\u0443\u043f\u0430 " + schedule.getGroup() + "\n");
                        output.append(schedule.getSpecialization().getName() + "-" + schedule.getYear() + "\n");
                        if (week == null){
                            output.append("(" + Utils.getWeeks(schedule.getWeeks()) + ")" + "\n");
                        }
                        output.append("\n");
                    }
                }
                row.createCell(j + 1).setCellValue(output.toString());
                row.getCell(j + 1).setCellStyle(error ? errorStyle : style);
            }
        }

        for (int i = 0; i < days.size() + 1; i++){
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

}