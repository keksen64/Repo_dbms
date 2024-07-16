package dbms.main.ddl;

import dbms.main.MainClass;

public class DataBaseM1 {
    public static String create(String name){

        if(MainClass.dbmsm1.readAndSetAlterLockTrue()==false){
            return "DBMS is locked";
        }
        if (MainClass.dbmsm1.isDataBaseNotUnique(name)) {
            MainClass.dbmsm1.setAlterLockFalse();
            return "Database name already exist";
        }
        dbms.main.dbobject.DataBaseM1 d = new dbms.main.dbobject.DataBaseM1(name);
        MainClass.dbmsm1.putDataBaseByName(name, d);
        MainClass.dbmsm1.setAlterLockFalse();
        return "Database created";

    }
}
