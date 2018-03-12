package console;

import java.util.Date;


public class ConsoleTest {

    public static void main(String[] args) {
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
        System.out.println(date.toString());
    }

}
