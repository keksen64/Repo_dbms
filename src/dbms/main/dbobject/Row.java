package dbms.main.dbobject;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Row {

    public Row(long txNum, int strCount, int intCount, int longCount, int doubleCount) {
        this.txNum = txNum;
        this.strCount = strCount;
        this.intCount = intCount;
        this.longCount = longCount;
        this.doubleCount = doubleCount;
        as = new String[this.strCount];
        ai = new int[this.intCount];
        al = new long[this.longCount];
        ad = new double[this.doubleCount];
    }
    public Row(long txNum, int strCount, int intCount, int longCount, int doubleCount, boolean visibility) {
        this.txNum = txNum;
        this.strCount = strCount;
        this.intCount = intCount;
        this.longCount = longCount;
        this.doubleCount = doubleCount;
        this.visibility = visibility;
        as = new String[this.strCount];
        ai = new int[this.intCount];
        al = new long[this.longCount];
        ad = new double[this.doubleCount];
    }

    private String[] as;
    private int[] ai;
    private long[] al;
    private double[] ad;
    private boolean readLock = false;
    private boolean writeLock = false;
    private boolean isLatestVersion = true;
    private boolean visibility = false;
    private final Lock groupReadLockLock = new ReentrantLock();
    private final Lock groupWriteLockLock = new ReentrantLock();
    private final Lock groupVersionLockLock = new ReentrantLock();
    private final long txNum;
    private long updateTxNum;
    private int strCount;
    private int intCount;
    private int longCount;
    private int doubleCount;

    public int getStrCount() {
        return strCount;
    }

    public void setStrCount(int strCount) {
        this.strCount = strCount;
    }

    public int getIntCount() {
        return intCount;
    }

    public void setIntCount(int intCount) {
        this.intCount = intCount;
    }

    public int getLongCount() {
        return longCount;
    }

    public void setLongCount(int longCount) {
        this.longCount = longCount;
    }

    public int getDoubleCount() {
        return doubleCount;
    }

    public void setDoubleCount(int doubleCount) {
        this.doubleCount = doubleCount;
    }

    public void setAs(String[] as) {
        this.as = as;
    }

    public void setAi(int[] ai) {
        this.ai = ai;
    }

    public void setAl(long[] al) {
        this.al = al;
    }

    public void setAd(double[] ad) {
        this.ad = ad;
    }

    public String[] getAs() {
        return as;
    }

    public int[] getAi() {
        return ai;
    }

    public long[] getAl() {
        return al;
    }

    public double[] getAd() {
        return ad;
    }

    public void setStringField(int position, String value){
        as[position] = value;
    }
    public void setIntField(int position, int value){
        ai[position] = value;
    }
    public void setLongField(int position, long value){
        al[position] = value;
    }
    public void setDoubleField(int position, double value){
        ad[position] = value;
    }

    public String getStringField(int position){
        return as[position];
    }
    public int getIntField(int position){
        return ai[position];
    }
    public long getLongField(int position){
        return al[position];
    }
    public double getDoubleField(int position){
        return ad[position];
    }

    @Override
    public String toString() {
        return "Row{" +
                "as=" + Arrays.toString(as) +
                ", ai=" + Arrays.toString(ai) +
                ", al=" + Arrays.toString(al) +
                ", ad=" + Arrays.toString(ad) +
                "}\n";
    }

    public String getRowValueString(int arrayPosition){
        return as[arrayPosition];
    }

    public int getRowValueInt(int arrayPosition){
        return ai[arrayPosition];
    }

    public long getRowValueLong(int arrayPosition){
        return al[arrayPosition];
    }

    public Double getRowValueDouble(int arrayPosition){
        return ad[arrayPosition];
    }

    public void setRowValueString(int arrayPosition, String value){
        as[arrayPosition] = value;
    }

    public void setRowValueInt(int arrayPosition, int value){
        ai[arrayPosition] = value;
    }

    public void setRowValueLong(int arrayPosition, long value){
        al[arrayPosition] = value;
    }

    public void setRowValueDouble(int arrayPosition, double value){
        ad[arrayPosition] = value;
    }
}
