package com.hrd.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    static final long ONE_MINUTE_IN_MILLIS = 60000;

    public static Date convertFromStringToDate(String dateFrom) throws Exception {
        SimpleDateFormat genericFormat = new SimpleDateFormat("yyyy-MM-dd");
        return genericFormat.parse(dateFrom);
    }

    public static LocalDateTime convertFromStringToLocalDateTime(String dateFrom) throws ParseException {
        SimpleDateFormat genericFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return genericFormat.parse(dateFrom).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime convertFromDateToLocalDateTime(Date dateFrom) throws ParseException {
        return dateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String convertFromLocalDateTimeToString(LocalDateTime dateFrom) {
        LocalDateTime dt = dateFrom;
        SimpleDateFormat genericFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return genericFormat.format(Date.from(dt.atZone(ZoneId.systemDefault()).toInstant()));
    }

    public static String convertFromLocalDateTimeToStringForReport(LocalDateTime dateFrom) {
        LocalDateTime dt = dateFrom;
        SimpleDateFormat formatDDMonYY = new SimpleDateFormat("dd-MMM-yy");
        return formatDDMonYY.format(Date.from(dt.atZone(ZoneId.systemDefault()).toInstant()));
    }

    public static LocalDateTime convertFromStringYYYYMMDDToLocalDateTime(String dateFrom) throws ParseException {
        SimpleDateFormat formatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
        return formatYYYYMMDD.parse(dateFrom).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String convertFromLocalDateTimeToStringYYYYMMDD(LocalDateTime dateFrom) throws ParseException {
        SimpleDateFormat formatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
        return formatYYYYMMDD.format(Date.from(dateFrom.atZone(ZoneId.systemDefault()).toInstant()));
    }

    public static String convertFromLocalDateTimeToStringDDMonYY(LocalDateTime dateFrom) throws ParseException {
        SimpleDateFormat formatDDMonYY = new SimpleDateFormat("dd-MMM-yy");
        return formatDDMonYY.format(Date.from(dateFrom.atZone(ZoneId.systemDefault()).toInstant()));
    }

    public static String convertFromLocalDateTimeToStringDDMonthYYYY(LocalDateTime dateFrom) throws ParseException {
        SimpleDateFormat formatDDMonYY = new SimpleDateFormat("dd MMMMM yyyy");
        return formatDDMonYY.format(Date.from(dateFrom.atZone(ZoneId.systemDefault()).toInstant()));
    }

    public static String convertFromLocalDateTimeToStringDDMonthYYYYHourMinute(LocalDateTime dateFrom)
            throws ParseException {
        SimpleDateFormat formatDDMonYY = new SimpleDateFormat("dd MMMMM yyyy, HH:mm");
        return formatDDMonYY.format(Date.from(dateFrom.atZone(ZoneId.systemDefault()).toInstant()));
    }

    public static String convertCurrentDateTimeToString(String format) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(Instant.now().atZone(ZoneId.systemDefault()));
    }

    public static LocalDate convertFromStringYYYYMMDDToLocalDate(String dateFrom) throws ParseException {
        DateTimeFormatter dtfYYYYMMDD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateFrom, dtfYYYYMMDD);
    }

    public static String convertFromLocalDateTimeToStringYYYYMM(LocalDateTime dateFrom) throws ParseException {
        SimpleDateFormat formatDDMonYY = new SimpleDateFormat("yyyyMM");
        return formatDDMonYY.format(Date.from(dateFrom.atZone(ZoneId.systemDefault()).toInstant()));
    }

    public static LocalDateTime convertFromStringYYYYMMToLocalDateTime(String dateFrom) throws ParseException {
        SimpleDateFormat formatYYYYMMDD = new SimpleDateFormat("yyyyMM");
        return formatYYYYMMDD.parse(dateFrom).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date addMinutesToDate(Date date, long additionalMinutes) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        long t = cal.getTimeInMillis();
        Date afterAddingAdditionalMinutes = new Date(t + (additionalMinutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingAdditionalMinutes;
    }

    public static boolean isSameDay(LocalDateTime date1, LocalDateTime date2) {
        return date1.toLocalDate().isEqual(date2.toLocalDate());
    }

    public static String getIndonesianDate(LocalDateTime date) {
        String[] month = new String[] { "", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus",
                "September", "Oktober", "November", "Desember" };

        return "" + month[date.getMonthValue()] + " " + date.getYear();
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of("GMT+7"));
    }


}
