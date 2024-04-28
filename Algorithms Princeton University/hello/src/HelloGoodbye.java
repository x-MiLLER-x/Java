public class HelloGoodbye {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java HelloGoodbye <FirstName> <SecondName>");
        }
        else {
            String firstName = args[0];
            String secondName = args[1];

            // Print the hello message
            System.out.println("Hello " + firstName + " and " + secondName + ".");

            // Print the goodbye message in reverse order
            System.out.println("Goodbye " + secondName + " and " + firstName + ".");
        }
    }
}
