package com.test.demo;

public class CatTest {
    public static void main(String[] args) {
       System.out.println("In same Package:");
       Cat cat = new Cat();
       cat.type = ' ';
       cat.name = "";
       cat.age = 1;
       System.out.println("cat.weight is private!");
    }
}
