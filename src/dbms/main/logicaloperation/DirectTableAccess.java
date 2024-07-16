package dbms.main.logicaloperation;

import dbms.main.dbobject.*;
import dbms.main.dbobject.Row;
import dbms.main.requestobject.Condition;
import dbms.main.requestobject.Request;

import java.util.Map;

public class DirectTableAccess {
    public static Row[] getAllRows(Table t){
        return t.getAllRows();
    }
    public static RowM1[] getAllRows(TableM1 t){
        return t.getAllRows();
    }

    public static RowM1[] getRowsByCondition(Condition c){
        if(c.getTable().isAlterLock()){
            System.out.println("Table in alter-lock mode");
        }
        RowM1[] resultRow = new RowM1[c.getTable().getRowCount()] ;
       int ct=0;
       int iterCt = 0;
       int maxCt = c.getTable().getRowCount();
        for (RowM1 row : c.getTable().getAllRows()) {
            if(iterCt>=maxCt){
                break;
            }
            iterCt++;
            boolean ok = true;
            if(row.getVisibility()==false){
                continue;
            }
            for (Map.Entry<Integer, Object> condition : c.getEqualityConditionIdx().entrySet()) {
                int index = condition.getKey();
                Object conditionObject = condition.getValue();
                Object rowObject = row.getField(index);
                if(conditionObject.equals(rowObject)==false){
                    ok = false;
                    break;
                }
            }
            if(ok){
                resultRow[ct]= row;
                ct++;
            }
        }

        RowM1[] resultRowCut = new RowM1[ct];
        System.arraycopy(resultRow, 0, resultRowCut, 0, ct);
        return resultRowCut;
    }
}


