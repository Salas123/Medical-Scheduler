import javax.swing.*;
import java.awt.*;

import java.util.Calendar;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientSettings;
import org.bson.Document;


public class MainComponent
{
    static JFrame mainFrame;
    static JLabel calendarMonthYearLabel;
    static JPanel optionsPanel, calendarComponent, calenderPanel;
    static JTextField calendarText;
    static JButton addEmployeeButton, createScheduleButton, createExcelSheetButton;
    static AddEmployeeComponent employeeComponent;
    static CreateScheduleComponent createScheduleComponent;
    static Calendar date;
    private String[] MONTHS = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    static MongoClient mongoClient;
    static MongoDatabase employeeDatabase;
    static MongoCollection<Document> employeesCollections;

    public MainComponent()
    {
        date = Calendar.getInstance();

        // main component
        mainFrame = new JFrame("Scheduler Application");
        mainFrame.setSize(900, 600);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new BorderLayout());

        // sub components
        optionsPanel = new JPanel();
        calenderPanel = new JPanel();
        calendarComponent = new JPanel();

        // Buttons for main component
        addEmployeeButton = new JButton("Add Employee");
        createScheduleButton = new JButton("Create Schedule");
        createExcelSheetButton = new JButton("Create Excel Sheet");

        optionsPanel.setPreferredSize(new Dimension(220, 500));
        calenderPanel.setPreferredSize(new Dimension(650, 400));
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        calenderPanel.setLayout(new GridLayout());
        calendarComponent.setLayout(new BoxLayout(calendarComponent, BoxLayout.Y_AXIS));

        String calendarStr = MONTHS[date.get(Calendar.MONTH)] + " " + Integer.toString(date.get(Calendar.YEAR));
        calendarMonthYearLabel = new JLabel(calendarStr);

        calendarText = new JTextField("Here is where the calendar will go");
        calendarText.setEditable(false);

        optionsPanel.add(addEmployeeButton);
        optionsPanel.add(createScheduleButton);
        optionsPanel.add(createExcelSheetButton);

        calendarComponent.add(calendarMonthYearLabel);
        calenderPanel.add(calendarText);
        calendarComponent.add(calenderPanel);

        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.add(BorderLayout.WEST, optionsPanel);
        mainFrame.add(BorderLayout.EAST, calendarComponent);

        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);

        // Connecting to database here
        System.out.println("[CONNECTION to DB]: Connecting to DB....");
        try{
            ConnectionString connectionString = new ConnectionString(
                    //ENTER URI HERE
            );

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .retryWrites(true)
                    .build();

            mongoClient = MongoClients.create(settings);
            System.out.println("[CONNECTION to DB]: Connected to DB!");

            employeeDatabase = mongoClient.getDatabase("EmployeeDB");
            employeesCollections = employeeDatabase.getCollection("Employees");


        }catch (Exception e)
        {
            System.out.println("[ERROR connection to DB]: \n" + e);
        }

        // Adding employee action listener button... this opens up the addEmployee window
        addEmployeeButton.addActionListener(e ->
                {
                    employeeComponent = new AddEmployeeComponent(employeesCollections);
                }
        );


        createScheduleButton.addActionListener(e -> {
            createScheduleComponent = new CreateScheduleComponent();
        });


    }


    public static void main(String[] args) {
        MainComponent mainComponent = new MainComponent();

    }
}
