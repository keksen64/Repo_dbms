package dbms.main.tools;

import java.util.HashSet;
import java.util.Locale;

public class Array {

    public static boolean checkUniqueValues(String[][] array) {
        HashSet<String> uniqueValues = new HashSet<>();
        for (String[] innerArray : array) {
            for (String value : innerArray) {
                // Проверка, удалось ли добавить значение в HashSet
                if (!uniqueValues.add(value.toLowerCase())) {
                    return false; // Найдено дублирующее значение
                }
            }
        }
        return true; // Все значения уникальны
    }

    public static boolean checkUniqueValues(String[] array) {
        HashSet<String> uniqueValues = new HashSet<>();

            for (String value : array) {
                // Проверка, удалось ли добавить значение в HashSet
                if (!uniqueValues.add(value.toLowerCase())) {
                    return false; // Найдено дублирующее значение
                }
            }

        return true; // Все значения уникальны
    }

    public static boolean checkForNonZeroValue(String[][] array) {
        if (array == null) {
            return false;
        }
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] != null) {
                    return true; // Найдено ненулевое значение
                }
            }
        }
        return false; // Все значения равны нулю
    }
    public static boolean checkForNonZeroValue(String[] array) {
        if (array == null) {
            return false;
        }

            for (int j = 0; j < array.length; j++) {
                if (array[j] != null) {
                    return true; // Найдено ненулевое значение
                }
            }

        return false; // Все значения равны нулю
    }

    public static boolean checkForZeroValue(String[][] array) {
        if (array == null) {
            return false;
        }
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] == null) {
                    return false; // Найдено нулевое значение
                }
            }
        }
        return true; // Все значения равны нулю
    }

    public static boolean checkForZeroValue(String[] array) {
        if (array == null) {
            return false;
        }

            for (int j = 0; j < array.length; j++) {
                if (array[j] == null) {
                    return false; // Найдено нулевое значение
                }
            }

        return true; // Все значения равны нулю
    }


    public static int[] checkIndexMetaValue(String[][] meta, String value) {
        for (int i = 0; i < meta.length; i++) {
            for (int j = 0; j < meta[i].length; j++) {
                if (meta[i][j].toLowerCase().equals(value.toLowerCase())) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public static int checkIndexMetaNameM1(String[] metaName, String value) {
            for (int j = 0; j < metaName.length; j++) {
                if (metaName[j].toLowerCase().equals(value.toLowerCase())) {
                    return j;
                }
            }
        return -1;
    }
    public static int checkTypeFieldByIndexM1(int[] metaType, int index) {
        return metaType[index];
    }

}