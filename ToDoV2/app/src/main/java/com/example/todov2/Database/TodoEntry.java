package com.example.todov2.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.util.Date;

@Entity(tableName = "todo")
public class TodoEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String description;
    private int priority;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    public TodoEntry(int id, String description, int priority, Date updatedAt){
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.updatedAt = updatedAt;
    }

    @Ignore
    public TodoEntry(String description, int priority, Date updatedAt){
        this.description = description;
        this.priority = priority;
        this.updatedAt = updatedAt;
    }

    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public String getDescription(){return description;}
    public void setId(String description){this.description = description;}

    public int getPriority(){return priority;}
    public void setPriority(int priority){this.priority = priority;}

    public Date getUpdatedAt(){return updatedAt;}
    public void setUpdatedAt(Date updatedAt){this.updatedAt = updatedAt;}
}