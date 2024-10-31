public class Main {
    public static void main(String[] args) {
        Stack stack = new Stack(3);
        stack.Push(10);
        stack.Push(20);
        System.out.println("Pop de stack: " + stack.Pop());

        stack.Clear();
        System.out.println("Après Clear, Pop de stack: " + stack.Pop());

        Queue queue = new Queue(3);
        queue.Push(30);
        queue.Push(40);
        System.out.println("Pop de queue: " + queue.Pop());


        queue.Clear();
        System.out.println("Après Clear, Pop de queue: " + queue.Pop());
    }
}
