import BoyerMooreSearch.Demo.BoyerMooreSearchDemo;

public class Main {
    // Program entry point.
    public static void main(String[] args) {
        // Create the demo object with the path to the searchable text.
        BoyerMooreSearchDemo demo = new BoyerMooreSearchDemo("../../input.txt");

        // Begin the demo.
        demo.start();
    }
}
