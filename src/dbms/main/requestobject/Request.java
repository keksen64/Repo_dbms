package dbms.main.requestobject;

import dbms.main.dbobject.Table;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private String[] sourceTables;
    private String[] alias;
    private String[] fields;
    Map<Table, Condition> equalityCondition = new HashMap<>();
}
