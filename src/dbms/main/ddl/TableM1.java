package dbms.main.ddl;

import dbms.main.dbobject.SchemaM1;

public class TableM1 {
//String - int - long - double
    public static String create(SchemaM1 s, String name, String[] metaName, int[] metaType){
        if(metaName.length==0){
            return "Column names not declared";
        }
        if(metaName.length!=metaType.length){
            return "Column names and not Column types not matched";
        }
        if(dbms.main.tools.Array.checkUniqueValues(metaName)==false){
            return "Column names not unique";
        }
        if(dbms.main.tools.Array.checkForNonZeroValue(metaName)==false){
            return "No column values are specified";
        }
        if(dbms.main.tools.Array.checkForZeroValue(metaName)==false){
            return "Null column values found in metaName";
        }
        if(s.readAndSetAlterLockTrue()==false){
            return "Schema is locked";
        }
        if (s.isTableNotUnique(name)) {
            s.setAlterLockFalse();
            return "Table names already exist";
        }
        dbms.main.dbobject.TableM1 t = new dbms.main.dbobject.TableM1(name, metaName, metaType);
        t.setMetaFieldCount(metaName.length);
        t.setFreeSpaceCount(0);
        t.setAlterLockFalse();
        t.setReadLockFalse();
        t.setWriteLockFalse();
        s.putTableByName(name, t);
        s.setAlterLockFalse();
        return "Table created";

    }


}
