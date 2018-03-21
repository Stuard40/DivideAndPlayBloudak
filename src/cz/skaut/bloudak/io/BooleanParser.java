package cz.skaut.bloudak.io;

public class BooleanParser {

    public static boolean parse(String value){
        switch (value.toUpperCase()) {
            case "ANO": return true;
            case "NE": return false;
            case "YES": return true;
            case "NO": return false;
            default: return false;
        }
    }
}
