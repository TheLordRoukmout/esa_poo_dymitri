public class Exo3 {
    public static void main(String[] args) {
        boolean[] arr = new boolean[100];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = true;
        }

        for (int i = 2; i < arr.length; i++) {
            for (int j = i; j < arr.length; j += i) {
                arr[j] = false;
            }
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
