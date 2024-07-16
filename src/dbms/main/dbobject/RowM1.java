package dbms.main.dbobject;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RowM1 {



    public RowM1(long txNum, int fieldCount) {
        this.txNum = txNum;
        this.fieldCount = fieldCount;
        af = new Object[this.fieldCount];
    }
    public RowM1(long txNum, int fieldCount, boolean visibility) {
        this.txNum = txNum;
        this.fieldCount = fieldCount;
        af = new Object[this.fieldCount];
        this.visibility = visibility;
    }

    private int fieldCount;
    private Object[] af;
    private boolean readLock = false;
    private boolean writeLock = false;
    private boolean isLatestVersion = true;
    private boolean visibility = false;
    private final Lock groupReadLockLock = new ReentrantLock();
    private final Lock groupWriteLockLock = new ReentrantLock();
    private final Lock groupVersionLockLock = new ReentrantLock();
    private final long txNum;
    private long updateTxNum;

    public int getFieldCount() {
        return fieldCount;
    }

    public void setFieldCount(int fieldCount) {
        this.fieldCount = fieldCount;
    }

    public void setAf(Object[] af) {
        this.af = af;
    }

    public Object[] getAf() {
        return af;
    }

    public void setField(int position, Object value){
        af[position] = value;
    }

    public Object getField(int position){
        return af[position];
    }

    public boolean getVisibility(){
        return visibility;
    }

    @Override
    public String toString() {
        return "Row{" +
                "af=" + Arrays.toString(af) +
                "}\n";
    }

}
