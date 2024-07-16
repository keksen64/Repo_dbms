package dbms.main.dbobject;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Table {
    private String name;

    public String[][] getMeta() {
        return meta;
    }

    public Table(String name, String[][] meta) {
        this.name = name;
        this.meta = meta;
    }


    private String[][] meta;
    private Row[] rows = new Row[0];
    private boolean readLock = true;
    private boolean writeLock = true;
    private boolean alterLock = true;
    private int metaStringFieldCount;
    private int metaIntFieldCount;
    private int metaLongFieldCount;
    private int metaDoubleFieldCount;
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

    public void setmetaStringFieldCount(int metaStringFieldCount) {
        this.metaStringFieldCount = metaStringFieldCount;
    }

    public void setmetaIntFieldCount(int metaIntFieldCount) {
        this.metaIntFieldCount = metaIntFieldCount;
    }

    public void setmetaLongFieldCount(int metaLongFieldCount) {
        this.metaLongFieldCount = metaLongFieldCount;
    }

    public void setmetaDoubleFieldCount(int metaDoubleFieldCount) {
        this.metaDoubleFieldCount = metaDoubleFieldCount;
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

    public Row[] getAllRows(){
        return rows;
    }

    @Override
    public String toString() {
        return "Table{" +
                "rows=\n" + Arrays.toString(rows) +
                '}';
    }

    public int getMetaStringFieldCount() {
        return meta[0].length;
    }

    public int getMetaIntFieldCount() {
        return meta[1].length;
    }

    public int getMetaLongFieldCount() {
        return meta[2].length;
    }

    public int getMetaDoubleFieldCount() {
        return meta[3].length;
    }

    public void addOneRow(Row r){

        if(r.getAs().length==metaStringFieldCount&&r.getAi().length==metaIntFieldCount&&r.getAl().length==metaLongFieldCount&&r.getAd().length==metaDoubleFieldCount){

            if(freeSpaceCount>0){
                rows[rowCount] = r;
                rowCount++;
                freeSpaceCount--;
            }else{
                Row[] nRows = new Row[arrayRowSize+extendRows];
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

    public Row getRowByID(int id){
        return rows[id];
    }
    public void updateRowCount(){rowCount++;}
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
