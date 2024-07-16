//import net.sf.jsqlparser.expression.Expression;
//import net.sf.jsqlparser.parser.CCJSqlParserUtil;
//import net.sf.jsqlparser.schema.Column;
//import net.sf.jsqlparser.statement.Statement;
//import net.sf.jsqlparser.statement.insert.Insert;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class Row {
//    private String[] stringFields;
//    private int[] intFields;
//    private double[] doubleFields;
//
//    public Row(int stringFieldCount, int intFieldCount, int doubleFieldCount) {
//        stringFields = new String[stringFieldCount];
//        intFields = new int[intFieldCount];
//        doubleFields = new double[doubleFieldCount];
//    }
//
//    public void setStringField(int index, String value) {
//        stringFields[index] = value;
//    }
//
//    public void setIntField(int index, int value) {
//        intFields[index] = value;
//    }
//
//    public void setDoubleField(int index, double value) {
//        doubleFields[index] = value;
//    }
//
//    // Добавьте геттеры и другие методы, если нужно
//}
//
//public class SQLParserExample {
//    public static void main(String[] args) {
//        String insertQuery = "INSERT INTO my_table (col1, col2, col3) VALUES ('value1', 123, 3.14)";
//
//        try {
//            // Парсинг INSERT запроса
//            Statement statement = CCJSqlParserUtil.parse(insertQuery);
//
//            // Проверка, что это INSERT запрос
//            if (statement instanceof Insert) {
//                Insert insertStatement = (Insert) statement;
//
//                // Получение списка вставляемых значений и имен колонок
//                List<String> columnNames = insertStatement.getColumns().stream().map(Column::getColumnName).collect(Collectors.toList());
//                List<Expression> expressions = insertStatement.getItemsList().getExpressions();
//
//                // Получение метаданных о типах столбцов из объекта Table (предполагается, что это объект table)
//                int stringFieldCount = table.getStringColumnCount();
//                int intFieldCount = table.getIntColumnCount();
//                int doubleFieldCount = table.getDoubleColumnCount();
//
//                // Создание объекта Row и заполнение его данными из INSERT запроса
//                Row newRow = new Row(stringFieldCount, intFieldCount, doubleFieldCount);
//
//                for (int i = 0; i < columnNames.size(); i++) {
//                    String columnName = columnNames.get(i);
//                    int columnIndex = table.getColumnIndex(columnName);
//                    Expression expression = expressions.get(i);
//
//                    if (table.getColumnType(columnName) == ColumnType.STRING) {
//                        newRow.setStringField(columnIndex, ((StringValue) expression).getValue());
//                    } else if (table.getColumnType(columnName) == ColumnType.INT) {
//                        newRow.setIntField(columnIndex, (int) ((LongValue) expression).getValue());
//                    } else if (table.getColumnType(columnName) == ColumnType.DOUBLE) {
//                        newRow.setDoubleField(columnIndex, Double.parseDouble(expression.toString()));
//                    }
//                }
//
//                // Теперь у вас есть объект Row с данными из INSERT запроса, разделенными по типам столбцов и учитывающими их порядок
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}