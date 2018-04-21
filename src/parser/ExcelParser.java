package parser;

import db.DBQueriesImpl;
import entity.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelParser {

    private static final int SPECIALIZATION_AND_COURSE_ROW = 6;
    private static final int SPECIALIZATION_AND_COURSE_COLUMN = 0;

    private static final int DATA_START_ROW = 10;
    private static final int DATA_START_COLUMN = 0;

    public static void parse(String path, DBQueriesImpl db) throws IOException, InvalidInputFileException{

        FileInputStream file = new FileInputStream(new File(path));

        XSSFWorkbook workbook = new XSSFWorkbook(file);

        XSSFSheet sheet = workbook.getSheetAt(0);

        String name = getSpecialization(getStringCellValue(sheet, SPECIALIZATION_AND_COURSE_ROW, SPECIALIZATION_AND_COURSE_COLUMN));
        Specialization specialization = db.getSpecializationByName(name);
        if (specialization == null) {
            specialization = new Specialization(name);
            db.addSpecialization(specialization);
        }

        int year = getYear(getStringCellValue(sheet, SPECIALIZATION_AND_COURSE_ROW, SPECIALIZATION_AND_COURSE_COLUMN));

        int i = DATA_START_ROW;
        int j = DATA_START_COLUMN;

        Day day = null;
        Period period = null;
        Discipline discipline;
        Lecturer lecturer;
        ClassType classType;
        ArrayList<Week> weeks = new ArrayList<Week>();
        Classroom classroom = null;

        try {
            main:
            while (true) {
                if (i == 150) {
                    break main;
                }

                String dayCell = getStringCellValue(sheet, i, j);
                if (!dayCell.equals("")) {
                    day = db.getDayByName(dayCell);
                    period = db.getPeriodByNumber(1);
                } else if (sheet.getRow(i).getCell(j + 1) == null){
                    i++;
                    continue main;
                } else if (!getStringCellValue(sheet, i, j + 1).equals("")){
                    period = db.getPeriodByNumber(period.getNumber() + 1);
                }

                if ((sheet.getRow(i).getCell(j + 2) == null) ||
                        getStringCellValue(sheet, i, j + 2).equals("")) {
                    i++;
                    continue main;
                }

                String disciplineCell = getStringCellValue(sheet, i, j  + 2);
                discipline = db.getDisciplineByName(disciplineCell);
                if (discipline == null) {
                    discipline = new Discipline(disciplineCell);
                    db.addDiscipline(discipline);
                }

                String lecturerCell = getStringCellValue(sheet, i, j  + 3).trim();
                lecturer = db.getLecturerByName(getLecturerName(lecturerCell));
                if (lecturer == null) {
                    lecturer = new Lecturer(getLecturerName(lecturerCell), getLecturerDegree(lecturerCell));
                    db.addLecturer(lecturer);
                }

                String groupCell = getStringCellValue(sheet, i, j  + 4);

                classType = db.getClassTypeByName(groupCell.equalsIgnoreCase("лекція") ? "лекція" : "практика");

                String weekCell = getStringCellValue(sheet, i, j  + 5);
                ArrayList<Integer> weeksNumbers = getWeeksNumbers(weekCell);
                for (int w = 0; w < weeksNumbers.size(); w++) {
                    Week week = db.getWeekByNumber(weeksNumbers.get(w));
                    weeks.add(week);
                }

                String classroomCell = getStringCellValue(sheet, i, j  + 6);
                if (!classroomCell.equals("")) {
                    classroom = db.getClassroomByBuildingAndNumber(Integer.parseInt(classroomCell.substring(0, classroomCell.indexOf("-")).trim()),
                            classroomCell.substring(classroomCell.indexOf("-") + 1, classroomCell.length()).trim());
                    if (classroom == null) {
                        classroom = new Classroom(Integer.parseInt(classroomCell.substring(0, classroomCell.indexOf("-")).trim()),
                                classroomCell.substring(classroomCell.indexOf("-") + 1, classroomCell.length()).trim(),
                                0, false, false, false);
                        db.addClassroom(classroom);
                    }
                }

                Schedule schedule = new Schedule(year, classType.getName().equalsIgnoreCase("лекція") ? null : groupCell,
                        specialization, discipline, lecturer, day, period, classroom, classType);

                db.addSchedule(schedule);

                for (int t = 0; t < weeks.size(); t++) {
                    ScheduleWeek scheduleWeek = new ScheduleWeek(schedule, weeks.get(t));
                    db.ScheduleWeek(scheduleWeek);
                }

                weeks.clear();

                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidInputFileException("", i + 1);
        }

        workbook.close();

    }

    private static String getStringCellValue(XSSFSheet sheet, int i, int j){
        DataFormatter formatter = new DataFormatter();
        Cell cell = sheet.getRow(i).getCell(j);
        return formatter.formatCellValue(cell);
    }

    private static String getSpecialization(String str) throws InvalidInputFileException {
        if (str.indexOf("\"") == -1 || (str.lastIndexOf("\"") - str.indexOf("\"")) == 0) {
            throw new InvalidInputFileException("Має бути спеціальність в лапках", SPECIALIZATION_AND_COURSE_ROW);
        }
        return str.contains("МП") ? "МП " + str.substring(str.indexOf("\"") + 1, str.lastIndexOf("\"")) :
                "БП " + str.substring(str.indexOf("\"") + 1, str.lastIndexOf("\""));
    }

    private static int getYear(String str) throws InvalidInputFileException {
        for (int i = str.length() - 1; i >= 0; i--) {
            if (Character.isDigit(str.charAt(i))) {
                return Character.getNumericValue(str.charAt(i));
            }
        }
        throw new InvalidInputFileException("Має бути рік навчання", SPECIALIZATION_AND_COURSE_ROW);
    }

    private static String getLecturerName(String str) {
        int i = 0;
        while (str.charAt(i) != 'Ю' && str.charAt(i) != 'Я' && str.charAt(i) != 'Є' && str.charAt(i) != 'Ї' &&
                str.charAt(i) != 'І' && str.charAt(i) != 'і' && (str.charAt(i) < 'А' || str.charAt(i) > 'Щ')){
            i++;
        }
        if (str.contains(".")){
            return str.substring(str.lastIndexOf('.') + 1).trim() + " " + str.substring(i, str.lastIndexOf('.') + 1).trim();
        } else {
            return str.substring(i, str.length());
        }
    }

    private static String getLecturerDegree(String str) {
        int i = 0;
        while (str.charAt(i) != 'Ю' && str.charAt(i) != 'Я' && str.charAt(i) != 'Є' && str.charAt(i) != 'Ї' &&
                str.charAt(i) != 'І' && str.charAt(i) != 'і' && (str.charAt(i) < 'А' || str.charAt(i) > 'Щ')){
            i++;
        }
        return i == 0 ? "" : str.substring(0, i).trim();
    }

    private static ArrayList<Integer> getWeeksNumbers(String str){
        ArrayList<Integer> result = new ArrayList<Integer>();
        String[] arr = str.replace(".", ",").split(",");
        for (int i = 0; i < arr.length; i++) {
            String[] subArr = arr[i].split("-");
            if (subArr.length == 1) {
                result.add(Integer.parseInt(subArr[0].trim()));
            } else {
                for (int j = Integer.parseInt(subArr[0].trim()); j <= Integer.parseInt(subArr[1].trim()); j++) {
                    if (j != 8) {
                        result.add(j);
                    }
                }
            }

        }
        return result;
    }

}