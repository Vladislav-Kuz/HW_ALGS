package org.example;

public class Employee {
    // наделим класс состоянием
    private String name;
    private int age;

    //сгенерим конструктор


    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // сгенерим геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
