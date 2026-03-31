import java.util.*;

class Person {
    String name;
    double paid;

    Person(String name) {
        this.name = name;
        this.paid = 0;
    }
}

public class BillSplitter {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < n; i++) {
            System.out.print("Enter name " + (i + 1) + ": ");
            people[i] = new Person(sc.nextLine());
        }

        // Input expenses
        while (true) {
            System.out.print("Enter payer name (or 'done' to finish): ");
            String payer = sc.nextLine();

            if (payer.equalsIgnoreCase("done"))
                break;

            System.out.print("Enter amount: ");
            double amount = sc.nextDouble();
            sc.nextLine();

            for (Person p : people) {
                if (p.name.equalsIgnoreCase(payer)) {
                    p.paid += amount;
                    break;
                }
            }
        }

        // Calculate total
        double total = 0;
        for (Person p : people) {
            total += p.paid;
        }

        double share = total / n;

        System.out.println("\nTotal expense: ₹" + total);
        System.out.println("Each person should pay: ₹" + share);

        System.out.println("\nFinal Settlement:");

        for (Person p : people) {
            double balance = p.paid - share;

            if (balance > 0)
                System.out.println(p.name + " should receive ₹" + balance);
            else if (balance < 0)
                System.out.println(p.name + " should pay ₹" + (-balance));
            else
                System.out.println(p.name + " is settled.");
        }

        sc.close();
    }
}