package dbms.main.data.dtImport;

import dbms.main.dbobject.Row;
import dbms.main.core.TxSequence;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class FromCSV {
        public static String[][] readCSV(String csvFile, String delimiter) {
            // Сначала считаем количество строк и столбцов
            int rowCount = 0;
            int colCount = 0;

            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    rowCount++;
                    if (rowCount == 1) {
                        colCount = line.split(delimiter).length;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Создание двумерного массива для хранения данных
            String[][] data = new String[rowCount][colCount];

            // Считывание данных в массив
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                String line;
                int rowIndex = 0;
                while ((line = br.readLine()) != null) {
                    String[] rowData = line.split(delimiter);
                    data[rowIndex] = rowData;
                    rowIndex++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        public static void insert(dbms.main.dbobject.Table destTable) {
            String csvFile = "data.csv";
            String delimiter = ",";
            Long tx = TxSequence.get();
            long startTime = System.nanoTime();
            String[][] data = readCSV(csvFile, delimiter);
            long IOTime = System.nanoTime();
            if (data.length > 0) {
                int numColumns = data[0].length;

                // Создание массивов для каждого столбца
                //String[][] columns = new String[numColumns][data.length - 1]; // минус 1 для исключения заголовка
                int[][] mapping = new int[numColumns][2];
                //для каждого названия колонки из ксв ищем адрес этого поля в мета модели

                if (!dbms.main.tools.Array.checkUniqueValues(data[0])){
                    System.out.println("Column not Unique");
                    return;
                }
                for (int j = 0; j < numColumns; j++) {
                    mapping[j] =dbms.main.tools.Array.checkIndexMetaValue(destTable.getMeta(), data[0][j]);
                    if(mapping[j]==null){
                        System.out.println("\""+data[0][j] + "\" column was not found in the target table.");
                        return;
                    }
                }

                System.out.println(Arrays.deepToString(mapping));
                Row[] rr = new Row[data.length - 1];
                int sss = destTable.getMetaStringFieldCount();
                int iii = destTable.getMetaIntFieldCount();
                int lll = destTable.getMetaLongFieldCount();
                int ddd = destTable.getMetaDoubleFieldCount();
                for (int i = 0; i < numColumns; i++) { // столбцы
                    if(mapping[i][0]==0){
                        for (int j = 1; j < data.length; j++) { // строки
                            if(i==0){
                                rr[j-1] = new Row(tx,sss,iii,lll,ddd,true);
                            }
                            rr[j-1].setStringField(mapping[i][1],data[j][i]);
                        }
                    }
                    if(mapping[i][0]==1){
                        for (int j = 1; j < data.length; j++) { // строки
                            if(i==0){
                                rr[j-1] = new Row(tx,sss,iii,lll,ddd,true);
                            }
                            rr[j-1].setIntField(mapping[i][1],Integer.parseInt(data[j][i]));
                        }
                    }
                    if(mapping[i][0]==2){
                        for (int j = 1; j < data.length; j++) { // строки
                            if(i==0){
                                rr[j-1] = new Row(tx,sss,iii,lll,ddd,true);
                            }
                            rr[j-1].setLongField(mapping[i][1],Long.getLong(data[j][i]));
                        }
                    }
                    if(mapping[i][0]==3){
                        for (int j = 1; j < data.length; j++) { // строки
                            if(i==0){
                                rr[j-1] = new Row(tx,sss,iii,lll,ddd,true);
                            }
                            rr[j-1].setDoubleField(mapping[i][1],Double.parseDouble(data[j][i]));
                        }
                    }

                }
                long prepareRowTime = System.nanoTime();
                for (int i = 0; i < data.length - 1; i++){
                    destTable.addOneRow(rr[i]);
                    destTable.updateRowCount();
                }
                long totalTime = System.nanoTime();
                long tot = totalTime - startTime;
                long io = IOTime - startTime;
                long prep = prepareRowTime - IOTime;
                long ins = totalTime - prepareRowTime;
                System.out.println("Время выполнения в наносекундах total:  " + tot + " IO: " + io + " prepare row: " +prep + " insert: " + ins);
                System.out.println("Время выполнения в миллисекундах total:  " + (tot  / 1_000_000.0) + " IO: " + (io / 1_000_000.0) + " prepare row: " +(prep / 1_000_000.0) + " insert: " + (ins / 1_000_000.0));
            }
        }
    }



