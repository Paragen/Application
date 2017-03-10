package ru.ifmo.ctddev.application.database;


public class Game {
    private int id;
    private String name;
    private String descr;
    private int version;
    private String shortDescr;
    private Class aClass;

    public Game() {
    }

    public Game(String name, int id, Class aClass) {
        this.name = name;
        this.id = id;
        this.aClass = aClass;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public void setVersion(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getDescr() {
        return descr;
    }


    public void setShortDescr(String shortDescr) {
        this.shortDescr = shortDescr;
    }

    public String getShortDescr() {
        return shortDescr;
    }

    public void setGameClass(Class aClass) {
        this.aClass = aClass;
    }

    public Class getGameClass() {
        return aClass;
    }
}
