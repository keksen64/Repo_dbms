package dbms.main.dbobject;

import java.util.HashMap;

public class Schema {
    private String name;
    private boolean alterLock;
    private HashMap<String, Table> tablesMap = new HashMap<>();

    public Schema(String name) {
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

    public Table getTableByName(String key) {
        return tablesMap.get(key);
    }
    public void putTableByName(String key, Table t) {
        tablesMap.put(key, t);
    }
    public Boolean isTableNotUnique(String key) { return tablesMap.containsKey(key);}

}
