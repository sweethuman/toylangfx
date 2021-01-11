package tech.sweethuman.fx;

import javafx.beans.property.SimpleStringProperty;

public class TableRow {
    public final SimpleStringProperty firstColumn = new SimpleStringProperty("");
    public final SimpleStringProperty secondColumn = new SimpleStringProperty("");

    public TableRow() {
        this("", "");
    }

    public TableRow(String firstColumn, String secondColumn) {
        setFirstColumn(firstColumn);
        setSecondColumn(secondColumn);
    }

    public void setFirstColumn(String firstColumn) {
        this.firstColumn.set(firstColumn);
    }

    public void setSecondColumn(String secondColumn) {
        this.secondColumn.set(secondColumn);
    }

    public String getFirstColumn() {
        return firstColumn.get();
    }

    public String getSecondColumn() {
        return secondColumn.get();
    }
}
