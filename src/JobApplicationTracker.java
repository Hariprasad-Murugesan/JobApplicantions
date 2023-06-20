import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class JobApplicationTracker {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/job application information";
    private static final String USERNAME = "hari";
    private static final String PASSWORD = "06031996";

    private Connection connection;

    public JobApplicationTracker() {
        try {
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connected to the database");

            // Test query to check the connection
            String testQuery = "SELECT 1";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(testQuery);
            if (resultSet.next()) {
                System.out.println("Database connection test successful");
            } else {
                System.out.println("Failed to test the database connection");
                // Handle the error or exit the application
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database: " + e.getMessage());
        }
    }

    public void addJobApplication(JobApplication application) {
        String insertQuery = "INSERT INTO job_applications (company, job_description,time) VALUES (?, ?,?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, application.getCompany());
            statement.setString(2, application.getjobRole());
            statement.setTimestamp(3, Timestamp.valueOf(application.getDateTime()));
            statement.executeUpdate();
            System.out.println("Job application added successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to add job application: " + e.getMessage());
        }
    }

    public List<JobApplication> getJobApplicationsByCompany(String company) {
        List<JobApplication> filteredApplications = new ArrayList<>();
        String selectQuery = "SELECT * FROM job_applications WHERE company = ?";
        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setString(1, company);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String jobRole = resultSet.getString("job_description");
                JobApplication application = new JobApplication(company, jobRole);
                filteredApplications.add(application);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve job applications: " + e.getMessage());
        }
        return filteredApplications;
    }

    public boolean isJobApplicationDuplicate(String company, String jobRole) {
        String selectQuery = "SELECT COUNT(*) FROM job_applications WHERE company = ? AND job_description = ?";
        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setString(1, company);
            statement.setString(2, jobRole);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.out.println("Failed to check job application duplicate: " + e.getMessage());
        }
        return false;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close the database connection: " + e.getMessage());
        }
    }
}
