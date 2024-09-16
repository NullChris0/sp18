package com.test.demo;
/*
   Package is namespace for classes and interfaces.
   To create a package, we need make new folders and use `package` statement.
 */
/*
    Any Java class without an explicit package name at the top of the file is automatically
    considered to be part of the “default” package.
    However, when writing real programs, you should avoid leaving your files in the default package
    (unless it’s a very small example program).
    This is because code from the default package cannot be imported(unless in the same folder)
    and it is possible to accidentally create classes with the same name under the default package.
 */
public class Cat {
    // in this package, we can't use default package's code(here is ./resource).
    /*
        |Modifier       |Class|Package|SubClass|World|
        |public         | Yes | Yes   | Yes    | Yes |
        |protected      | Yes | Yes   | Yes    | No  |
        |package-private| Yes | Yes   | No     | No  |
        |private        | Yes | No    | No     | NO  |
     */
    public char type;
    protected String name;
    int age;
    private double weight;
}
class CCat extends Cat {
    private void test() {
        System.out.println("In same Package & SubClass:");
        this.type = ' ';
        this.name = " ";
        this.age = 1;
        System.out.println("this.weight is private!");
    }
}
