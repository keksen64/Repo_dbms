package dbms.main.dbobject;

import java.util.HashMap;

public class DataBaseM1 {
    private String name;
    private boolean alterLock;
    private HashMap<String, SchemaM1> schemaMap = new HashMap<>();

    public DataBaseM1(String name) {
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
    public SchemaM1 getSchemaByName(String key) {
        return schemaMap.get(key);
    }
    public void putSchemaByName(String key, SchemaM1 s) {
        schemaMap.put(key, s);
    }
    public Boolean isSchemaNotUnique(String key) { return schemaMap.containsKey(key);}
}
