import com.test.demo.Cat;

public class CatLauncher {
    public static void main(String[] args) {
        System.out.println("In Outside World:");
        Cat cat = new Cat();
        cat.type = ' ';
        System.out.println("cat.name is protected!");
        System.out.println("cat.age is package-private!");
        System.out.println("cat.weight is private!");
    }
}
class Cats extends Cat {
    private void test() {
        System.out.println("In SubClass:");
        this.type = ' ';
        this.name = "";
        System.out.println("this.age is package-private!");
        System.out.println("this.weight is private!");
    }
}