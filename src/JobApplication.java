import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JobApplication {
    private String company;
    private LocalDateTime dateTime;
    private String jobRole;

    public JobApplication(String company,String jobRole){
        this.company = company;
        this.dateTime = LocalDateTime.now();
        this.jobRole = jobRole;

    }

    public String getCompany() {
        return company;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getjobRole() {
        return jobRole;
    }

    public String toString()
    {
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fomattedDateTime= dateTime.format(formatter);
        return "Company:" + company+ "\n"+
                "Date and Time:" + fomattedDateTime+ "\n"+
                "Job Role:" +jobRole+ "\n";
    }
}
