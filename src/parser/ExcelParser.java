package parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import db.DBQueriesImpl;
//import db.DBImitation;
import entity.ClassType;
import entity.Classroom;
import entity.Day;
import entity.Discipline;
import entity.Lecturer;
import entity.Period;
import entity.Schedule;
import entity.ScheduleWeek;
import entity.Specialization;
import entity.Week;

public class ExcelParser {
	
	private static final int SPECIALIZATION_AND_COURSE_ROW = 6;
	private static final int SPECIALIZATION_AND_COURSE_COLUMN = 0;

	private static final int DATA_START_ROW = 10;
	private static final int DATA_START_COLUMN = 0;

	private static Random random = new Random();;
	
	public static void parse(String path, DBQueriesImpl db) throws IOException, InvalidInputFileException, ParserAlgorithmException {
		
		FileInputStream file = new FileInputStream(new File(path));
		             
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		 
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		String name = getSpecialization(sheet.getRow(SPECIALIZATION_AND_COURSE_ROW)
				.getCell(SPECIALIZATION_AND_COURSE_COLUMN).getStringCellValue());
		Specialization specialization = db.getSpecializationByName(name);
		if (specialization == null) {
			specialization = new Specialization(random.nextInt(10000), name);
			db.addSpecialization(specialization);
			//DB Insert
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
				period = new Period(1, 1);
			} else if (day == null) {
				throw new ParserAlgorithmException();
			}

			if (period == null) {
				break main;
				//throw new ParserAlgorithmException();
			} else if (!sheet.getRow(i).getCell(j + 1).getStringCellValue().equals("")){
				period = new Period(period.getNumber() + 1, period.getNumber() + 1);
			}
			
			if ((sheet.getRow(i).getCell(j + 2) == null) || 
					sheet.getRow(i).getCell(j + 2).getStringCellValue().equals("")) {
				i++;
				continue main;
			}
			String disciplineCell = sheet.getRow(i).getCell(j + 2).getStringCellValue();
			discipline = db.getDisciplineByName(disciplineCell);
			if (discipline == null) {
				discipline = new Discipline(random.nextInt(10000), disciplineCell);
				db.addDiscipline(discipline);
				//DB Insert
			}
			
			String lecturerCell = sheet.getRow(i).getCell(j + 3).getStringCellValue();
			lecturer = db.getLecturerByName(lecturerCell);
			if (lecturer == null) {
				lecturer = new Lecturer(random.nextInt(10000), 
						getLecturerName(lecturerCell), getLecturerDegree(lecturerCell));
				db.addLecturer(lecturer);
				//DB Insert
			}
			
			String group = "";
			
			DataFormatter formatter = new DataFormatter(); 
			Cell cell = sheet.getRow(i).getCell(j + 4);
			String groupCell = formatter.formatCellValue(cell);
			
			String weekCell = sheet.getRow(i).getCell(j + 5).getStringCellValue();
			ArrayList<Integer> weeksNumbers = getWeeksNumbers(weekCell);
			for (int w = 0; w < weeksNumbers.size(); w++) {
				weeks.add(db.getWeekByNumber(w));
			}
			
			String classroomCell = sheet.getRow(i).getCell(j + 6).getStringCellValue();
			if (!classroomCell.equals("")) {
				classroom = db.getClassroomByNumber(getClassroomNumber(classroomCell));
			}
			
			Schedule schedule = new Schedule(random.nextInt(10000), year, 
					groupCell.equalsIgnoreCase("лекція") ? null : groupCell, specialization, discipline,
					lecturer, day, period, classroom, classType);
			db.addSchedule(schedule);
			
			for (int t = 0; t < weeks.size(); t++) {
				ScheduleWeek scheduleWeek = new ScheduleWeek(random.nextInt(10000), schedule, weeks.get(t));
				db.ScheduleWeek(scheduleWeek);
			}
			
			i++;
		}
		
		//DBImitation.showSchedules();
		
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
		while ((str.charAt(i) < 'А' || str.charAt(i) > 'Я')) {
			i++;
		}
		return str.substring(i, str.length());
	}
	
	private static String getLecturerDegree(String str) {
		int i = 0;
		while ((str.charAt(i) < 'А' || str.charAt(i) > 'Я')) {
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
	
	private static String getClassroomNumber(String str) {
		String[] arr = str.split("-");
		return arr[1].trim();
	}

}
