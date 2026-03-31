import java.util.*;

class Person {
    String name;
    double paid;
    double balance; // New: tracking net balance

    Person(String name) {
        this.name = name;
        this.paid = 0;
        this.balance = 0;
    }
}

public class BillSplitter {
    public static void main(String[] args) {
        // Try-with-resources ensures 'sc' is closed automatically
        try (Scanner sc = new Scanner(System.in)) {

            System.out.print("Enter number of people: ");
            int n = 0;
            try {
                n = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Exiting.");
                return; // Now 'sc' closes automatically here!
            }

            if (n <= 0) {
                System.out.println("Number of people must be positive. Exiting.");
                return;
            }

            Person[] people = new Person[n];

            for (int i = 0; i < n; i++) {
                System.out.print("Enter name for person " + (i + 1) + ": ");
                people[i] = new Person(sc.nextLine());
            }

            // Expense Input Loop
            while (true) {
                System.out.print("\nEnter payer name (or 'done' to finish): ");
                String payerName = sc.nextLine();

                if (payerName.equalsIgnoreCase("done")) break;

                Person payer = null;
                for (Person p : people) {
                    if (p.name.equalsIgnoreCase(payerName)) {
                        payer = p;
                        break;
                    }
                }

                if (payer == null) {
                    System.out.println("Name not found in the group!");
                    continue;
                }

                System.out.print("Enter amount paid by " + payer.name + ": ₹");
                try {
                    double amount = Double.parseDouble(sc.nextLine());
                    if (amount < 0) {
                        System.out.println("Error: Amount cannot be negative.");
                        continue; 
                    }
                    payer.paid += amount;
                } catch (Exception e) {
                    System.out.println("Invalid amount. Try again.");
                }
            }

            // Calculation Logic
            double total = 0;
            for (Person p : people) total += p.paid;
            double share = total / n;

            System.out.println("\n--- Summary ---");
            System.out.printf("Total Expense: ₹%.2f\n", total);
            System.out.printf("Individual Share: ₹%.2f\n", share);
            System.out.println("----------------\n");

            List<Person> debtors = new ArrayList<>();
            List<Person> creditors = new ArrayList<>();

            for (Person p : people) {
                p.balance = p.paid - share;
                if (p.balance < -0.01) debtors.add(p);
                else if (p.balance > 0.01) creditors.add(p);
            }

            System.out.println("Final Settlement Plan:");
            int d = 0, c = 0;
            while (d < debtors.size() && c < creditors.size()) {
                Person debtor = debtors.get(d);
                Person creditor = creditors.get(c);

                double payAmount = Math.min(-debtor.balance, creditor.balance);

                System.out.printf("💸 %s pays %s: ₹%.2f\n", debtor.name, creditor.name, payAmount);

                debtor.balance += payAmount;
                creditor.balance -= payAmount;

                if (Math.abs(debtor.balance) < 0.01) d++;
                if (Math.abs(creditor.balance) < 0.01) c++;
            }

            if (total > 0 && debtors.isEmpty() && creditors.isEmpty()) {
                System.out.println("Everyone is perfectly settled!");
            }
            
        } // 'sc' closes here automatically
    }
}