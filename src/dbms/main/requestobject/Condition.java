package dbms.main.requestobject;

import dbms.main.dbobject.Table;
import dbms.main.dbobject.TableM1;

import java.util.HashMap;
import java.util.Map;

public class Condition {

    public Condition(TableM1 t, Map<String, String> equalityCondition) {
        this.t = t;
        this.equalityCondition = equalityCondition;
        for (Map.Entry<String, String> entry : equalityCondition.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            int index = t.getIndexFieldByName(key); // Метод для получения индекса
            indexEqualityCondition.put(index, value);
        }
    }

    TableM1 t;
    Map<String, String> equalityCondition = new HashMap<>();
    Map<Integer, Object> indexEqualityCondition = new HashMap<>();


    public TableM1 getTable() {
        return t;
    }
    public Map<String, String> getEqualityCondition() {
        return equalityCondition;
    }
    public Map<Integer, Object> getEqualityConditionIdx() {
        return indexEqualityCondition;
    }
}
