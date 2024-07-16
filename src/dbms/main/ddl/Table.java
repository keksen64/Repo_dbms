package dbms.main.ddl;

import dbms.main.dbobject.Schema;

public class Table {
//String - int - long - double
    public static String create(Schema s, String name, String[][] meta){
        if(dbms.main.tools.Array.checkUniqueValues(meta)==false){
            return "Column names not unique";
        }
        if(dbms.main.tools.Array.checkForNonZeroValue(meta)==false){
            return "No column values are specified";
        }
        if(dbms.main.tools.Array.checkForZeroValue(meta)==false){
            return "Null column values found in meta";
        }
        if(s.readAndSetAlterLockTrue()==false){
            return "Schema is locked";
        }
        if (s.isTableNotUnique(name)) {
            s.setAlterLockFalse();
            return "Table names already exist";
        }
        dbms.main.dbobject.Table t = new dbms.main.dbobject.Table(name, meta);
        t.setmetaStringFieldCount(meta[0].length);
        t.setmetaIntFieldCount(meta[1].length);
        t.setmetaLongFieldCount(meta[2].length);
        t.setmetaDoubleFieldCount(meta[3].length);
        t.setFreeSpaceCount(0);
        t.setAlterLockFalse();
        t.setReadLockFalse();
        t.setWriteLockFalse();
        s.putTableByName(name, t);
        s.setAlterLockFalse();
        return "Table created";

    }


}
