package com.example.demo.repository;

import lombok.Getter;

@Getter
public enum Tables {
    USER_TABLE("SPRING_USER", new String[]{"USER_ID", "USER_NAME"}),
    OWNER_TABLE("OWNERS", new String[]{""});

    private final String tableName;
    private final String[] columns;

    Tables(String tableName, String[] columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    public String getColumn(int columnIndex) {
        return this.columns[columnIndex];
    }

    public String getIndexColumn() {
        return this.columns[0];
    }
}
