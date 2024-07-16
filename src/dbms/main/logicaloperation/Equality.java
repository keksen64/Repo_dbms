package dbms.main.logicaloperation;

import dbms.main.dbobject.Row;

public class Equality {

    public static boolean equalString(Row r, int position, String strToEqual){
        if(r.getAs()[position].equals(strToEqual)){
            return true;
        }
        return false;
    }

    public static boolean equalInt(Row r, int position, int intToEqual){
        if(r.getAi()[position]==intToEqual){
            return true;
        }
        return false;
    }

    public static boolean equalLong(Row r, int position, long longToEqual){
        if(r.getAl()[position]==longToEqual){
            return true;
        }
        return false;
    }

    public static boolean equalDouble(Row r, int position, double doubleToEqual){
        final double EPSILON = 1e-9;
        return Math.abs(r.getAd()[position] - doubleToEqual) < EPSILON;
    }

}
