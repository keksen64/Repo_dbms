package dbms.connect.query;

import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.Alias;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
    public static void main(String[] args) {
        String sql = "SELECT col1, col2 FROM my_table WHERE col1 = 'value' GROUP BY col1 ORDER BY col2 DESC";

        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            if (statement instanceof Insert) {
                Insert insertStatement = (Insert) statement;

                // Получение списка вставляемых значений и имен колонок
                List<String> columnNames = insertStatement.getColumns().stream().map(Column::getColumnName).collect(Collectors.toList());
                //List<Expression> expressions = insertStatement.getItemsList().getExpressions();
            }
            if (statement instanceof Select) {
                Select selectStatement = (Select) statement;
                SelectBody selectBody = selectStatement.getSelectBody();

                if (selectBody instanceof PlainSelect) {
                    PlainSelect plainSelect = (PlainSelect) selectBody;

                    // Получаем колонки
                    List<SelectItem> selectItems = plainSelect.getSelectItems();
                    System.out.println("Columns:");
                    for (SelectItem item : selectItems) {
                        System.out.println(item);
                    }

                    // Получаем таблицу
                    FromItem fromItem = plainSelect.getFromItem();
                    if (fromItem instanceof Table) {
                        Table table = (Table) fromItem;
                        System.out.println("Table: " + table.getName());
                    }

                    // Получаем условия WHERE
                    System.out.println("Where: " + plainSelect.getWhere());

                    // Получаем JOIN
                    List<Join> joins = plainSelect.getJoins();
                    if (joins != null) {
                        System.out.println("Joins:");
                        for (Join join : joins) {
                            System.out.println(join);
                        }
                    }

                    // Получаем GROUP BY
                    GroupByElement groupBy = (GroupByElement) plainSelect.getGroupBy();
                    if (groupBy != null) {
                        System.out.println("Group By:");
                        List<Expression> groupByExpressions = groupBy.getGroupByExpressions();
                        for (Expression groupByExpr : groupByExpressions) {
                            System.out.println(groupByExpr);
                        }
                    }

                    // Получаем ORDER BY
                    List<OrderByElement> orderByElements = plainSelect.getOrderByElements();
                    if (orderByElements != null) {
                        System.out.println("Order By:");
                        for (OrderByElement orderBy : orderByElements) {
                            System.out.println(orderBy);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}