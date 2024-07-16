package dbms.main;

import dbms.main.data.dtImport.FromCSVM1;
import dbms.main.dbobject.RowM1;
import dbms.main.ddl.*;
import dbms.main.dbobject.DBMS;
import dbms.main.data.dtImport.FromCSV;
import dbms.main.dbobject.DBMSM1;
import dbms.main.logicaloperation.DirectTableAccess;
import dbms.main.requestobject.Condition;

import java.util.HashMap;
import java.util.Map;


public class MainClass {
    public static DBMS dbms = new DBMS();
    public static DBMSM1 dbmsm1 = new DBMSM1();
    public static void main(String[] args) {
//        String[][] meta = {
//                {"StringField1","StringField2"},
//                {"IntField1", "intField2"},
//                {},
//                {}
//        };
//        DataBase.create("testDB");
//        Schema.create(dbms.getDataBaseByName("testDB"), "testSchema" );
//        String s =Table.create(dbms.getDataBaseByName("testDB").getSchemaByName("testSchema"), "testTable", meta );
//        System.out.println(s);
//        long startTime = System.nanoTime();
//        FromCSV.insert(dbms.getDataBaseByName("testDB").getSchemaByName("testSchema").getTableByName("testTable"));
//        long endTime = System.nanoTime();
//       // System.out.println(dbms.getDataBaseByName("testDB").getSchemaByName("testSchema").getTableByName("testTable").toString());
//        long duration = endTime - startTime;
//        // Печать времени выполнения в наносекундах
//        System.out.println("Время выполнения в наносекундах: " + duration);
//        System.out.println("Время выполнения в миллисекундах: " + (duration / 1_000_000.0));


        DataBaseM1.create("testDB");
        String[] metaName = {"StringField1","StringField2","IntField1", "intField2"};
        int[] metaType = {0,0,1,1};
        SchemaM1.create(dbmsm1.getDataBaseByName("testDB"), "testSchema" );
        String s = TableM1.create(dbmsm1.getDataBaseByName("testDB").getSchemaByName("testSchema"), "testTable", metaName, metaType );
        System.out.println(s);
        Map<String, String> map = new HashMap<>();
        map.put("StringField2", "11123www");
        Condition cd = new Condition(dbmsm1.getDataBaseByName("testDB").getSchemaByName("testSchema").getTableByName("testTable"), map);

        FromCSVM1.insert(dbmsm1.getDataBaseByName("testDB").getSchemaByName("testSchema").getTableByName("testTable"));

        //System.out.println(dbmsm1.getDataBaseByName("testDB").getSchemaByName("testSchema").getTableByName("testTable").toString());




        long startTimeAccess = System.nanoTime();
        RowM1[] r = DirectTableAccess.getRowsByCondition(cd);
        long endTimeAccess = System.nanoTime();
        long durationAccess = endTimeAccess - startTimeAccess;
        System.out.println(r.length);
        System.out.println(dbmsm1.getDataBaseByName("testDB").getSchemaByName("testSchema").getTableByName("testTable").getRowCount());
        System.out.println("Время выполнения селкта в наносекундах: " + durationAccess);
        System.out.println("Время выполнения селекта в миллисекундах: " + (durationAccess / 1_000_000.0));


    }
}
