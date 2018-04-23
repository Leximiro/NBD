package test;

import db.DBQueriesImpl;
import entity.Classroom;

public class Test {

    public static void main(String[] args){

//		try {
//			ExcelParser.parse("");
//		} catch (IOException | InvalidInputFileException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParserAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

        // DB queries tests
        DBQueriesImpl db = new DBQueriesImpl();
//        db.cleanDB();
//		db.getScheduleErrors();
//		System.out.println(db.getScheduleByLecturerAndWeekAndSpecAndCourseAndDiscipline(null,null,null,null,null));
//		System.out.println(db.getScheduleByBuildingAndClassroomTypeAndClassroomNumber(1,false,false,false,null));
//		System.out.println(db.getAllBuildings());

        //System.out.println(db.getScheduleByBuildingAndClassroomTypeAndClassroomNumber(10,false,true,true,null));
        //System.out.println(db.getLecturerByName("В.А. Руссєв"));
    }
}

class Encoding {
    public static void encode() {
        String s = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c > 31 && c < 127)
                System.out.print(String.valueOf(c));
            else
                System.out.print(String.format("\\u%04x", (int)c));
        }
    }
}
