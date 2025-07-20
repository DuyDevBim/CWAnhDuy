package CWAnhDuy;

public class ExecutionTimeMeasurement {
    public static void main(String[] args) {

        // Number of iterations for the repeat functions
        int numOfLoop = 13_000_000;

        // Record start time
        long startTime = System.nanoTime();

        // Execute repeat1 function
        repeat2("A", numOfLoop);

        // Record end time
        long endTime = System.nanoTime();

        // Calculate elapsed time in milliseconds
        long elapsedTime = endTime - startTime;

        // Output the execution time
        System.out.println("Execution time: " + elapsedTime + " (ns).");
    }

    // Concatenates the string 'c' 'n' times using String
    public static String repeat1(String c, int n) {
        String answer = "";
        for (int i = 0; i < n; i++) {
            answer += c;
        }
        return answer;
    }

    // Concatenates the string 'c' 'n' times using StringBuilder
    public static String repeat2(String c, int n) {
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < n; i++) {
            answer.append(c);
        }
        return answer.toString();
    }
}
