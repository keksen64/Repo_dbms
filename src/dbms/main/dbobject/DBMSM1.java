package dbms.main.dbobject;

import java.util.HashMap;

public class DBMSM1 {
    private boolean alterLock;
    private HashMap<String, DataBaseM1> DataBasesMap = new HashMap<>();

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

//    public HashMap<String, DataBase> getDataBasesMap() {
//        return DataBasesMap;
//    }
    public DataBaseM1 getDataBaseByName(String key) {
        return DataBasesMap.get(key);
    }
    public void putDataBaseByName(String key, DataBaseM1 d) {
        DataBasesMap.put(key, d);
    }
    public Boolean isDataBaseNotUnique(String key) { return DataBasesMap.containsKey(key);
    }
}
