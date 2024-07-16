package dbms.main.ddl;

public class SchemaM1 {
    public static String create(dbms.main.dbobject.DataBaseM1 d, String name){

        if(d.readAndSetAlterLockTrue()==false){
            return "DataBase is locked";
        }
        if (d.isSchemaNotUnique(name)) {
            d.setAlterLockFalse();
            return "Schema name already exist";
        }
        dbms.main.dbobject.SchemaM1 s = new dbms.main.dbobject.SchemaM1(name);
        d.putSchemaByName(name, s);
        d.setAlterLockFalse();
        return "Schema created";

    }
}
