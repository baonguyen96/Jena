package constraints;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class DateTimeMutation {

    private static ArrayList<String> dateFormats = new ArrayList<>(
            Arrays.asList(
                    "yyyy.MM.dd G 'at' HH:mm:ss z",
                    "EEE, MMM d, ''yy",
                    "h:mm a",
                    "hh 'o''clock' a, zzzz",
                    "K:mm a, z",
                    "yyyyy.MMMMM.dd GGG hh:mm aaa",
                    "EEE, d MMM yyyy HH:mm:ss Z",
                    "yyMMddHHmmssZ",
                    "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
                    "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                    "YYYY-'W'ww-u",
                    "yyyy-MM-dd hh:mm:ss",
                    "yyyy-MM-dd",
                    "d/M/yy",
                    "MM/dd/yy"
            )
    );


    enum Options {
        CreateTodayDate, CreatePastOrFutureDate, CreateAlwaysInvalidDates,
        CreateDateInAllFormatsExcept, CreateDateInFormat, CreateLeapDates
    }


    public static String mutate(Options option, String... data) {
        String mutatedDate = "";


        switch (option) {
            case CreateTodayDate:
                mutatedDate = createTodayDate();
                break;
            case CreatePastOrFutureDate:
                mutatedDate = createPastOrFutureDate(data[0], Integer.parseInt(data[1]));
                break;
            case CreateAlwaysInvalidDates:
                mutatedDate = createAlwaysInvalidDates();
                break;
            case CreateDateInAllFormatsExcept:
                mutatedDate = createDateInAllFormatsExcept(data[0]);
                break;
            case CreateDateInFormat:
                mutatedDate = createDateInFormat(data[0]);
                break;
            case CreateLeapDates:
                mutatedDate = createLeapDate();
                break;
            default:
                break;
        }

        return mutatedDate;
    }


    public static String createTodayDate() {
        return new Date().toString();
    }


    public static String createPastOrFutureDate(String anchorDate, int daysVariance) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(anchorDate));
        calendar.add(Calendar.DATE, daysVariance);
        return calendar.getTime().toString();
    }


    public static String createAlwaysInvalidDates() {
        String[] invalidDates = {
                "00/00/0000",
                "01/32/2017",
                "02/00/2018",
                "02/30/2019",
                "02/29/2017",
                "04/31/2018",
                "06/31/2018",
                "09/31/2018",
                "11/31/2018"
        };
        int randomDateIndex = (int)(Math.random() * invalidDates.length);
        Date date = new Date(invalidDates[randomDateIndex]);
        return date.toString();
    }


    public static String createDateInAllFormatsExcept(String formatToExclude) {
        int randomIndexForFormatters = 0;
        int indexOfFormatToExclude = dateFormats.indexOf(formatToExclude);

        do {
            randomIndexForFormatters = (int)(Math.random() * dateFormats.size());
        } while (randomIndexForFormatters == indexOfFormatToExclude);

        return createDateInFormat(dateFormats.get(randomIndexForFormatters));
    }


    public static String createDateInFormat(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return simpleDateFormat.format(date);
    }


    private static String createLeapDate() {
        String[] leapDates = {
                "02/29/1804",
                "02/29/1920",
                "02/29/2000",
                "02/29/2004",
                "02/29/2008",
                "02/29/2016",
                "02/29/2024",
                "02/29/2028",
        };
        int randomDateIndex = (int)(Math.random() * leapDates.length);
        Date date = new Date(leapDates[randomDateIndex]);
        return date.toString();
    }


}
