package dbms.main.ddl;

public class Schema {
    public static String create(dbms.main.dbobject.DataBase d, String name){

        if(d.readAndSetAlterLockTrue()==false){
            return "DataBase is locked";
        }
        if (d.isSchemaNotUnique(name)) {
            d.setAlterLockFalse();
            return "Schema name already exist";
        }
        dbms.main.dbobject.Schema s = new dbms.main.dbobject.Schema(name);
        d.putSchemaByName(name, s);
        d.setAlterLockFalse();
        return "Schema created";

    }
}
