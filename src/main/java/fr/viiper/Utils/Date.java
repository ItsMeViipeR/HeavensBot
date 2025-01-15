package fr.viiper.Utils;

import java.time.format.DateTimeFormatter;

public class Date {
    public static DateTimeFormatter format() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    }
}