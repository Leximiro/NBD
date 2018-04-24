package parser;

import entity.Classroom;
import entity.Day;
import entity.Period;
import entity.Week;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Utils {

    public static String getWeeks(ArrayList<Week> weeks){
        Collections.sort(weeks, new WeekSorter());
        StringBuilder result = new StringBuilder();

        int start = 0;
        int end = 0;
        for (Week w : weeks){
            if (start == 0){
                start = w.getNumber();
            } else if (end == 0 && w.getNumber() - start > 1){
                result.append(start + " ");
                start = w.getNumber();
            } else if (end == 0 && w.getNumber() - start == 1){
                end = w.getNumber();
            } else if (w.getNumber() - end == 1){
                end = w.getNumber();
            } else if (w.getNumber() - end > 1){
                result.append(start + "-" + end + " ");
                start = w.getNumber();
                end= 0;
            }
        }
        if (end != 0){
            result.append(start + "-" + end);
        } else if (start != 0){
            result.append(start);
        }

        return result.toString().replace("-7 9-", "-").replace("7 9-", "7-")
                .replace("-7 9", "-9").replace(" ", ", ");
    }

}

class ClassroomSorter implements Comparator<Classroom> {

    public int compare(Classroom one, Classroom another){
        return (one.getBuilding() + "-" + one.getNumber()).compareTo(another.getBuilding() + "-" + another.getNumber());
    }

}

class WeekSorter implements Comparator<Week> {

    public int compare(Week one, Week another){
        return one.getNumber() - another.getNumber();
    }

}

class DaySorter implements Comparator<Day> {

    public int compare(Day one, Day another){
        return one.getId() - another.getId();
    }

}

class PeriodSorter implements Comparator<Period> {

    public int compare(Period one, Period another){
        return one.getId() - another.getId();
    }

}