package dbms.connect.query;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.schema.Table;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Validator {
    public static void main(String[] args) {
        String sql = "SELECT col12, COUNT(col2) FROM my_table WHERE col1 = 'value' GROUP BY col1 ORDER BY COUNT(col2) DESC";

        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            if (statement instanceof Select) {
                Select selectStatement = (Select) statement;
                SelectBody selectBody = selectStatement.getSelectBody();

                if (selectBody instanceof PlainSelect) {
                    PlainSelect plainSelect = (PlainSelect) selectBody;

                    // Получаем колонки
                    List<SelectItem> selectItems = plainSelect.getSelectItems();
                    List<String> columns = new ArrayList<>();
                    for (SelectItem item : selectItems) {
                        if (item instanceof SelectExpressionItem) {
                            SelectExpressionItem expressionItem = (SelectExpressionItem) item;
                            Expression expression = expressionItem.getExpression();
                            if (expression instanceof Column) {
                                columns.add(((Column) expression).getColumnName());
                            } else if (expression instanceof Function) {
                                Function function = (Function) expression;
                                // Обработка агрегатных функций
                                if (!function.isAllColumns() && function.getParameters() != null) {
                                    for (Expression expr : function.getParameters().getExpressions()) {
                                        if (expr instanceof Column) {
                                            columns.add(((Column) expr).getColumnName());
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Получаем GROUP BY
                    GroupByElement groupBy = plainSelect.getGroupBy();
                    List<String> groupByColumns = new ArrayList<>();
                    if (groupBy != null) {
                        for (Expression groupByExpr : groupBy.getGroupByExpressions()) {
                            if (groupByExpr instanceof Column) {
                                groupByColumns.add(((Column) groupByExpr).getColumnName());
                            }
                        }
                    }

                    // Проверка валидности
                    boolean isValid = true;
                    for (SelectItem item : selectItems) {
                        if (item instanceof SelectExpressionItem) {
                            SelectExpressionItem expressionItem = (SelectExpressionItem) item;
                            Expression expression = expressionItem.getExpression();
                            if (expression instanceof Column) {
                                String columnName = ((Column) expression).getColumnName();
                                if (!groupByColumns.contains(columnName)) {
                                    System.out.println("Column " + columnName + " is not in GROUP BY and is not an aggregate function.");
                                    isValid = false;
                                }
                            }
                        }
                    }

                    if (isValid) {
                        System.out.println("Query is valid.");
                    } else {
                        System.out.println("Query is invalid.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Проверка, является ли выражение агрегатной функцией
    private static boolean isAggregateFunction(Expression expression) {
        if (expression instanceof Function) {
            Function function = (Function) expression;
            String functionName = function.getName().toUpperCase();
            // Список известных агрегатных функций
            List<String> aggregateFunctions = List.of("COUNT", "SUM", "AVG", "MIN", "MAX");
            return aggregateFunctions.contains(functionName);
        }
        return false;
    }
}
