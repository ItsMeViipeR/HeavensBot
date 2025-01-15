package fr.viiper.Utils;

public class RandomColor {
    public static int generate() {
        return (int) (Math.random() * 0x1000000);
    }
}