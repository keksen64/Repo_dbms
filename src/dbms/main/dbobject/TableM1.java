package dbms.main.dbobject;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TableM1 {
    private String name;

    public String[] getMetaName() {
        return metaName;
    }
    public int[] getMetaType() {
        return metaType;
    }

    public TableM1(String name, String[] metaName, int[] metaType) {
        this.name = name;
        this.metaName = metaName;
        this.metaType = metaType;
    }


    private String[] metaName;
    private int[] metaType;
    private RowM1[] rows = new RowM1[0];
    private boolean readLock = true;
    private boolean writeLock = true;
    private boolean alterLock = true;
    private int metaFieldCount;
    private int arrayRowSize = 0;
    private int rowCount;
    private int freeSpaceCount;
    private int extendRows = 1000000;
    private final Lock groupReadLockLock = new ReentrantLock();
    private final Lock groupWriteLockLock = new ReentrantLock();
    private final Lock groupAlterLockLock = new ReentrantLock();


    public void setReadLockFalse() {
        groupReadLockLock.lock();
        try {
            this.readLock = false;
        }finally {
            groupReadLockLock.unlock();
        }
    }

    public void setWriteLockFalse() {
        groupWriteLockLock.lock();
        try {
            this.writeLock = false;
        }finally {
            groupWriteLockLock.unlock();
        }
    }

    public void setAlterLockFalse() {
        groupAlterLockLock.lock();
        try {
            this.alterLock = false;
        }finally {
            groupAlterLockLock.unlock();
        }
    }

    public void setMetaFieldCount(int metaFieldCount) {
        this.metaFieldCount = metaFieldCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public void setFreeSpaceCount(int freeSpaceCount) {
        this.freeSpaceCount = freeSpaceCount;
    }

    public boolean isReadLock() {
        groupReadLockLock.lock();
        try {
            return readLock;
        }finally {
            groupReadLockLock.unlock();
        }
    }

    public boolean isWriteLock() {
        groupWriteLockLock.lock();
        try {
            return writeLock;
        }finally {
            groupWriteLockLock.unlock();
        }
    }

    public boolean isAlterLock() {
        groupAlterLockLock.lock();
        try {
            return alterLock;
        }finally {
            groupAlterLockLock.unlock();
        }
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getFreeSpaceCount() {
        return freeSpaceCount;
    }

    public RowM1[] getAllRows(){
        return rows;
    }

    @Override
    public String toString() {
        return "Table{" +
                "rows=\n" + Arrays.toString(rows) +
                '}';
    }

    public int getMetaFieldCount() {
        return metaName.length;
    }

    public void addOneRow(RowM1 r){

        if(r.getAf().length==metaFieldCount){
           // System.out.println("fs = " + freeSpaceCount + " rc = "+ rowCount + " len = " + rows.length + " arRowSize " + arrayRowSize );
            if(freeSpaceCount>0){
                rows[rowCount] = r;
                rowCount++;
                freeSpaceCount--;
            }else{
                RowM1[] nRows = new RowM1[arrayRowSize+extendRows];
                System.arraycopy(rows, 0, nRows, 0, arrayRowSize);
                rows = nRows;
                arrayRowSize = arrayRowSize + extendRows;
                freeSpaceCount = freeSpaceCount + extendRows;
                rows[rowCount] = r;
                rowCount++;
                freeSpaceCount--;
            }
        }
    }

    public RowM1 getRowByID(int id){
        return rows[id];
    }
    public void updateRowCount(){rowCount++;}
    public int getIndexFieldByName( String name) {
        for (int j = 0; j < metaName.length; j++) {
            if (metaName[j].toLowerCase().equals(name.toLowerCase())) {
                return j;
            }
        }
        return -1;
    }

    // Получение индекса столбца по его имени
//    public int getColumnIndex(String columnName) {
//        for (int i = 0; i < meta.length; i++) {
//            for (int j = 0; j < meta[i].length; j++) {
//                if (meta[i][j].equals(columnName)) {
//                    return j;
//                }
//            }
//        }
//        return -1; // Если столбец с заданным именем не найден
//    }
//
//    public String getColumnType(String columnName) {
//        for (int i = 0; i < meta.length; i++) {
//            for (int j = 0; j < meta[i].length; j++) {
//                if (meta[i][j].equals(columnName)) {
//                    // В зависимости от индекса массива метаданных возвращаем соответствующий тип столбца
//                    switch (i) {
//                        case 0:
//                            return "STRING";
//                        case 1:
//                            return "INT";
//                        case 2:
//                            return "DOUBLE";
//                        default:
//                            return "UNKNOWN"; // Если тип столбца неизвестен
//                    }
//                }
//            }
//        }
//        return "UNKNOWN"; // Если столбец с заданным именем не найден
//    }
}
