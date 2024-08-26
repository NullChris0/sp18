public class HelloNumbers {
    public static void main(String[] args) {
        int sum = 0, x = 0;
        while (x < 10) {
            x = x + 1;
            System.out.print(sum + " ");
            sum += x;
        }
    }
}
