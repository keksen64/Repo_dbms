package dbms.main.ddl;

import dbms.main.MainClass;

public class DataBase {
    public static String create(String name){

        if(MainClass.dbms.readAndSetAlterLockTrue()==false){
            return "DBMS is locked";
        }
        if (MainClass.dbms.isDataBaseNotUnique(name)) {
            MainClass.dbms.setAlterLockFalse();
            return "Database name already exist";
        }
        dbms.main.dbobject.DataBase d = new dbms.main.dbobject.DataBase(name);
        MainClass.dbms.putDataBaseByName(name, d);
        MainClass.dbms.setAlterLockFalse();
        return "Database created";

    }
}
