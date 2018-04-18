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

    public static void parse(String path, DBQueriesImpl db) throws IOException, InvalidInputFileException, ParserAlgorithmException {

        FileInputStream file = new FileInputStream(new File(path));

        XSSFWorkbook workbook = new XSSFWorkbook(file);

        XSSFSheet sheet = workbook.getSheetAt(0);

        String name = getSpecialization(sheet.getRow(SPECIALIZATION_AND_COURSE_ROW)
                .getCell(SPECIALIZATION_AND_COURSE_COLUMN).getStringCellValue());
        Specialization specialization = db.getSpecializationByName(name);
        if (specialization == null) {
            specialization = new Specialization(name);
            db.addSpecialization(specialization);
        }

        int year = getYear(sheet.getRow(SPECIALIZATION_AND_COURSE_ROW)
                .getCell(SPECIALIZATION_AND_COURSE_COLUMN).getStringCellValue());

        int i = DATA_START_ROW;
        int j = DATA_START_COLUMN;

        Day day = null;
        Period period = null;
        Discipline discipline = null;
        Lecturer lecturer = null;
        ClassType classType = null;
        ArrayList<Week> weeks = new ArrayList<Week>();
        Classroom classroom = null;

        main:
        while (true) {
            if (i == 150) {
                break main;
            }

            String dayCell = sheet.getRow(i).getCell(j).getStringCellValue();
            if (!dayCell.equals("")) {
                day = db.getDayByName(dayCell);
                period = db.getPeriodByNumber(1);
            } else if (!sheet.getRow(i).getCell(j + 1).getStringCellValue().equals("")){
                period = db.getPeriodByNumber(period.getNumber() + 1);
            }

            if ((sheet.getRow(i).getCell(j + 2) == null) ||
                    sheet.getRow(i).getCell(j + 2).getStringCellValue().equals("")) {
                i++;
                continue main;
            }
            String disciplineCell = sheet.getRow(i).getCell(j + 2).getStringCellValue();
            discipline = db.getDisciplineByName(disciplineCell);
            if (discipline == null) {
                discipline = new Discipline(disciplineCell);
                db.addDiscipline(discipline);
            }

            String lecturerCell = sheet.getRow(i).getCell(j + 3).getStringCellValue().trim();
            lecturer = db.getLecturerByName(getLecturerName(lecturerCell));
            if (lecturer == null) {
                lecturer = new Lecturer(getLecturerName(lecturerCell), getLecturerDegree(lecturerCell));
                db.addLecturer(lecturer);
            }

            DataFormatter formatter = new DataFormatter();
            Cell cell = sheet.getRow(i).getCell(j + 4);
            String groupCell = formatter.formatCellValue(cell);

            classType = db.getClassTypeByName(groupCell.equalsIgnoreCase("лекція") ? "лекція" : "практика");

            String weekCell = sheet.getRow(i).getCell(j + 5).getStringCellValue();
            ArrayList<Integer> weeksNumbers = getWeeksNumbers(weekCell);
            for (int w = 0; w < weeksNumbers.size(); w++) {
                Week week = db.getWeekByNumber(weeksNumbers.get(w));
                weeks.add(week);
            }

            String classroomCell = sheet.getRow(i).getCell(j + 6).getStringCellValue();
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

            i++;
        }

        workbook.close();

    }

    private static String getSpecialization(String str) throws InvalidInputFileException {
        if (str.indexOf("\"") == -1 || (str.lastIndexOf("\"") - str.indexOf("\"")) == 0) {
            throw new InvalidInputFileException();
        }
        return str.substring(str.indexOf("\"") + 1, str.lastIndexOf("\""));
    }

    private static int getYear(String str) throws InvalidInputFileException {
        for (int i = str.length() - 1; i >= 0; i--) {
            if (Character.isDigit(str.charAt(i))) {
                return Character.getNumericValue(str.charAt(i));
            }
        }
        throw new InvalidInputFileException();
    }

    private static String getLecturerName(String str) {
        int i = 0;
        while (!String.valueOf(str.charAt(i)).equals(String.valueOf(str.charAt(i)).toUpperCase())){
            i++;
        }
        /*while ((str.charAt(i) < 128 || str.charAt(i) > 153) && str.charAt(i) != 158 && str.charAt(i) != 159
                && str.charAt(i) != 242 && str.charAt(i) != 244) {
            i++;
        }*/
        return str.substring(i, str.length());
    }

    private static String getLecturerDegree(String str) {
        int i = 0;
        while (!String.valueOf(str.charAt(i)).equals(String.valueOf(str.charAt(i)).toUpperCase())){
            i++;
        }
        return str.substring(0, i - 1);
    }

    private static ArrayList<Integer> getWeeksNumbers(String str){
        ArrayList<Integer> result = new ArrayList<Integer>();
        String[] arr = str.split(",");
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
