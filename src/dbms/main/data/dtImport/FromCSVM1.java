package dbms.main.data.dtImport;

import dbms.main.core.TxSequence;
import dbms.main.dbobject.RowM1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class FromCSVM1 {
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

        public static void insert(dbms.main.dbobject.TableM1 destTable) {
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
                    mapping[j][0] = dbms.main.tools.Array.checkIndexMetaNameM1(destTable.getMetaName(), data[0][j]);
                    mapping[j][1] = dbms.main.tools.Array.checkTypeFieldByIndexM1(destTable.getMetaType(), mapping[j][0] );
                    if(mapping[j][0]==-1){
                        System.out.println("\""+data[0][j] + "\" column was not found in the target table.");
                        return;
                    }
                }

                System.out.println(Arrays.deepToString(mapping));
                RowM1[] rr = new RowM1[data.length - 1];
                int sss = destTable.getMetaFieldCount();
                for (int i = 0; i < numColumns; i++) { // столбцы
                    if(mapping[i][1]==0){
                        for (int j = 1; j < data.length; j++) { // строки
                            if(i==0){
                                rr[j-1] = new RowM1(tx,sss,true);
                            }
                            rr[j-1].setField(mapping[i][0],data[j][i]);
                        }
                    }
                    if(mapping[i][1]==1){
                        for (int j = 1; j < data.length; j++) { // строки
                            if(i==0){
                                rr[j-1] = new RowM1(tx,sss,true);
                            }
                            rr[j-1].setField(mapping[i][0],Integer.parseInt(data[j][i]));
                        }
                    }
                    if(mapping[i][1]==2){
                        for (int j = 1; j < data.length; j++) { // строки
                            if(i==0){
                                rr[j-1] = new RowM1(tx,sss,true);
                            }
                            rr[j-1].setField(mapping[i][0],Long.getLong(data[j][i]));
                        }
                    }
                    if(mapping[i][1]==3){
                        for (int j = 1; j < data.length; j++) { // строки
                            if(i==0){
                                rr[j-1] = new RowM1(tx,sss,true);
                            }
                            rr[j-1].setField(mapping[i][0],Double.parseDouble(data[j][i]));
                        }
                    }

                }
                long prepareRowTime = System.nanoTime();
                for (int i = 0; i < data.length - 1; i++){
                    destTable.addOneRow(rr[i]);
                    //destTable.updateRowCount();
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



