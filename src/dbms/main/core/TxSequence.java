package dbms.main.core;

public class TxSequence {
    private static long c = 0L;
    public static synchronized long get(){
        c++;
        return c;
    }
}
