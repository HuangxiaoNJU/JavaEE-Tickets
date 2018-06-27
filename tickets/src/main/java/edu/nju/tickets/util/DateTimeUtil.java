package edu.nju.tickets.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class DateTimeUtil {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String convertTimestampToString(Timestamp timestamp) {
        return DATE_FORMAT.format(timestamp);
    }

//    public static Timestamp convertStringToTimestamp(String dateTime) {
//        return Timestamp.valueOf(dateTime);
//    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp convertStringToTimestamp(String string) {
        String[] dateInfo = string.split("-|:|\\s+");
        LocalDateTime localDateTime = LocalDateTime.of(
                Integer.valueOf(dateInfo[0]),
                Integer.valueOf(dateInfo[1]),
                Integer.valueOf(dateInfo[2]),
                dateInfo.length > 3 ? Integer.valueOf(dateInfo[3]) : 0,
                dateInfo.length > 4 ? Integer.valueOf(dateInfo[4]) : 0,
                dateInfo.length > 5 ? Integer.valueOf(dateInfo[5]) : 0
        );
        return Timestamp.valueOf(localDateTime);
    }

//    public static int getYear(Timestamp timestamp) {
//        return Integer.valueOf(convertTimestampToString(timestamp).substring(0, 4));
//    }
//
//    public static int getMonth(Timestamp timestamp) {
//        return Integer.valueOf(convertTimestampToString(timestamp).substring(5, 2));
//    }
//
//    public static int getDay(Timestamp timestamp) {
//        return Integer.valueOf(convertTimestampToString(timestamp).substring(8, 2));
//    }

    public static void main(String[] args) {
        System.out.println(DATE_FORMAT.format(convertStringToTimestamp("2018-1-1")));
    }

}
