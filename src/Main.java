import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        JobApplicationTracker tracker = new JobApplicationTracker();
        Scanner scanner = new Scanner(System.in);
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_RESET = "\u001B[0m";



        boolean exit = false;

        while (!exit) {
            System.out.println("1. Enter job details");
            System.out.println("2. Search job applications by company");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    // Enter job application
                    System.out.println("Enter company name:");
                    String company = scanner.nextLine();
                    System.out.println("Enter job Role:");
                    String jobRole = scanner.nextLine();
                    if (tracker.isJobApplicationDuplicate(company, jobRole)) {
                        System.out.println(ANSI_RED + "This job application has already been applied."+ ANSI_RESET);
                    } else {
                        JobApplication application = new JobApplication(company, jobRole);
                        tracker.addJobApplication(application);
                    }
                    break;
                case 2:
                    // Filter by company
                    System.out.println("Enter company name to filter:");
                    String filterCompany = scanner.nextLine();
                    List<JobApplication> filteredApplications = tracker.getJobApplicationsByCompany(filterCompany);
                    if (filteredApplications.isEmpty()) {
                        System.out.println("No job applications found for the specified company.");
                    } else {
                        System.out.println("Job applications for company " + filterCompany + ":");
                        for (JobApplication application : filteredApplications) {
                            System.out.println(application);
                        }
                    }
                    break;
                case 3:
                    tracker.closeConnection();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}