package com.hrd.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class BasicUtils {

    private static List<Map<String, Object>> createListOfMapFromArray(List<Object[]> list, String datePattern,
                                                                      String decimalPattern, String[] columnNames) throws Exception {
        String pattern = datePattern;
        DateFormat df = new SimpleDateFormat(pattern);
        DecimalFormat decimalFormat = new DecimalFormat(decimalPattern);

        if (list == null) {
            return new ArrayList<>();
        }

        if (list.size() > 0) {
            if (list.get(0).length > columnNames.length) {
                throw new Exception("Invalid Argument");
            }
        }

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Object[] item : list) {
            Map<String, Object> temp = new HashMap<>();
            for (int i = 0; i < columnNames.length; i++) {
                if (item[i] instanceof Timestamp) {
                    temp.put(columnNames[i],
                            DateUtil.convertFromLocalDateTimeToString(((Timestamp) item[i]).toLocalDateTime()));
                } else if (item[i] instanceof Date) {
                    temp.put(columnNames[i], df.format(item[i]));
                } else if (item[i] instanceof Double) {
                    temp.put(columnNames[i], decimalFormat.format(item[i]));
                } else if (item[i] instanceof LocalDate) {
                    temp.put(columnNames[i], df.format(item[i]));
                }	else {
                    temp.put(columnNames[i], item[i]);
                }
            }
            result.add(temp);
        }

        return result;
    }

    public static List<Map<String, Object>> createListOfMapFromArray(List<Object[]> list, String... columnNames)
            throws Exception {
        return createListOfMapFromArray(list, "dd MMM yyyy", "#.##########", columnNames);
    }

    public static List<Map<String, Object>> createListOfMapFromArrayWithPattern(List<Object[]> list, String datePattern,
                                                                                String decimalPattern, String... columnNames) throws Exception {
        return createListOfMapFromArray(list, datePattern, decimalPattern, columnNames);
    }

    public static boolean validateCustomId(List<String> keys, Map<String, Object> customId) {
        boolean isAuthorized = true;
        for (String key : keys) {
            if (customId.get(key) == null)
                isAuthorized = false;
        }
        return isAuthorized;
    }

    public static String rupiahFormatter(String price) {
        price = price.split("\\.")[0];
        String result = "";
        String[] parts = price.split("");

        for (int i = 0; i < parts.length; i++) {
            result = parts[(parts.length - 1) - i] + result;
            if (i % 3 == 2 && i != parts.length - 1)
                result = "." + result;
        }

        return "Rp" + result;
    }


}
