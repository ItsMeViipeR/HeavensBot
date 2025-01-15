package fr.viiper.Utils;

public class UptimeFormatter {
    public static String formatUptime(long ms) {
        if (ms < 1000) {
            return ms + " ms";
        } else if (ms < 60_000) {
            return (ms / 1000) + " s";
        } else if (ms < 3_600_000) {
            long minutes = ms / 60_000;
            long seconds = (ms % 60_000) / 1000;
            return minutes + " min " + seconds + " s";
        } else if (ms < 86_400_000) { // moins de 24h
            long hours = ms / 3_600_000;
            long minutes = (ms % 3_600_000) / 60_000;
            long seconds = (ms % 60_000) / 1000;
            return hours + " h " + minutes + " min " + seconds + " s";
        } else {
            long days = ms / 86_400_000; // 24 * 60 * 60 * 1000 = 86 400 000
            long hours = (ms % 86_400_000) / 3_600_000;
            long minutes = (ms % 3_600_000) / 60_000;
            long seconds = (ms % 60_000) / 1000;
            return days + " d " + hours + " h " + minutes + " m " + seconds + " s";
        }
    }
}