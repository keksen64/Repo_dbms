package dbms.main.dbobject;

import java.util.HashMap;

public class DataBase {
    private String name;
    private boolean alterLock;
    private HashMap<String, Schema> schemaMap = new HashMap<>();

    public DataBase(String name) {
        this.name = name;
    }

    public synchronized boolean isAlterLock() {
        return alterLock;
    }

    public synchronized boolean readAndSetAlterLockTrue() {
        if(alterLock==false){
            alterLock=true;
            return true;
        }
        else return false;
    }

    public synchronized void setAlterLockFalse() {
        alterLock=false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

//    public HashMap<String, Schema> getSchemasMap() {
//        return schemaMap;
//    }
    public Schema getSchemaByName(String key) {
        return schemaMap.get(key);
    }
    public void putSchemaByName(String key, Schema s) {
        schemaMap.put(key, s);
    }
    public Boolean isSchemaNotUnique(String key) { return schemaMap.containsKey(key);}
}
