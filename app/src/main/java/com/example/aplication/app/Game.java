package com.example.aplication.app;

/**
 * Created by User on 24.02.2017.
 */
public class Game {
    private int id;
    private String name;
    private String descr;
    private int version;
    private String shortDescr;

    public void setId( int id){
        this.id = id;
    }
    public void setVersion( int version){
        this.version = version;
    }
    public void setName( String name){
        this.name = name;
    }
    public void setDescr( String descr){
        this.descr = descr;
    }
    public void setShortDescr( String shortDescr){
        this.shortDescr = shortDescr;
    }
    public String getName(){
        return name;
    }
    public String getDescr(){
        return descr;
    }
    public String getShortDescr(){
        return shortDescr;
    }
    public int getId(){
        return id;
    }
    public int getVersion(){
        return version;
    }
}
