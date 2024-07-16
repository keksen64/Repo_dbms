package dbms.main.dbobject;

import java.util.HashMap;

public class SchemaM1 {
    private String name;
    private boolean alterLock;
    private HashMap<String, TableM1> tablesMap = new HashMap<>();

    public SchemaM1(String name) {
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

    //public HashMap<String, Table> getTablesMap() {
//        return tablesMap;
//    }

    public TableM1 getTableByName(String key) {
        return tablesMap.get(key);
    }
    public void putTableByName(String key, TableM1 t) {
        tablesMap.put(key, t);
    }
    public Boolean isTableNotUnique(String key) { return tablesMap.containsKey(key);}

}
