package com.bricks_klin.Util;

/**
 * Created by Anonymous on 8/2/2018.
 */

public class SetterGetterEmp {
    private String Id;
    private String name;
    private String age;
    private String cell;
    private String addr;
    private String klinid;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getKlinid() {
        return klinid;
    }

    public void setKlinid(String klinid) {
        this.klinid = klinid;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    private String salary;
}
