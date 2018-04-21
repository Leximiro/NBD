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
//        initClassrooms(db);
//        db.cleanDB();
//		db.getScheduleErrors();
//		System.out.println(db.getScheduleByLecturerAndWeekAndSpecAndCourseAndDiscipline(null,null,null,null,null));
//		System.out.println(db.getScheduleByBuildingAndClassroomTypeAndClassroomNumber(1,false,false,false,null));
//		System.out.println(db.getAllBuildings());

        //System.out.println(db.getScheduleByBuildingAndClassroomTypeAndClassroomNumber(10,false,true,true,null));
        //System.out.println(db.getLecturerByName("В.А. Руссєв"));
    }

    static void initClassrooms(DBQueriesImpl db) {
        db.addClassroom(new Classroom(1, 1, "223", 60, false, true, true));
        db.addClassroom(new Classroom(2, 1, "225", 74, false, false, true));
        db.addClassroom(new Classroom(3, 1, "310", 40, true, true, true));
        db.addClassroom(new Classroom(4, 1, "313", 108, false, false, true));
        db.addClassroom(new Classroom(5, 1, "108", 15, true, true, true));
        db.addClassroom(new Classroom(6, 1, "112", 15, true, false, true));
        db.addClassroom(new Classroom(7, 1, "206", 15, true, false, true));
        db.addClassroom(new Classroom(8, 1, "208", 15, true, false, true));
        db.addClassroom(new Classroom(9, 1, "306", 15, true, false, true));
        db.addClassroom(new Classroom(10, 1, "307", 15, true, false, true));
        db.addClassroom(new Classroom(11, 1, "309", 15, true, true, true));
        db.addClassroom(new Classroom(12, 1, "331", 15, true, true, true));
        db.addClassroom(new Classroom(13, 3, "220a", 30, false, false, true));
        db.addClassroom(new Classroom(14, 3, "302", 52, false, false, true));
        db.addClassroom(new Classroom(15, 10, "1", 15, true, true, false));
        db.addClassroom(new Classroom(16, 10, "2", 15, true, true, false));
        db.addClassroom(new Classroom(17, 10, "3", 15, true, true, false));
        db.addClassroom(new Classroom(18, 10, "4", 15, true, true, false));
    }

}