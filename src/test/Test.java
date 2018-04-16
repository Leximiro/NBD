package test;

import java.io.IOException;
import java.util.ArrayList;

import db.DBQueriesImpl;
import entity.Schedule;
import entity.Specialization;
import parser.ExcelParser;
import parser.InvalidInputFileException;
import parser.ParserAlgorithmException;

public class Test {

	public static void main(String[] args){
		
//		try {
//			ExcelParser.parse("D:\\06_programming\\workspace\\eclipse\\NBD-master\\�� �� -1 ����� 17-18�.�.Microsoft Office Excel.xlsx");
//		} catch (IOException | InvalidInputFileException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParserAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
// DB queries tests
		DBQueriesImpl db = new DBQueriesImpl();
		db.getScheduleErrors();
		System.out.println(db.getScheduleByLecturerAndWeekAndSpecAndCourseAndDiscipline(null,null,null,null,null));
		System.out.println(db.getScheduleByBuildingAndClassroomTypeAndClassroomNumber(null,null,null,null,null));
	}

}
