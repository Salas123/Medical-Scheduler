import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;

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
    static JButton addEmployeeButton, editAnExistingEmployeeButton, editAnExistingScheduleButton, createScheduleButton, exportCalendarButton;
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
        mainFrame = new JFrame("Salas Scheduler");
        mainFrame.setSize(950, 650);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new BorderLayout());


        // sub components
        optionsPanel = new JPanel();
        calenderPanel = new JPanel();
        calendarComponent = new JPanel();

        // Buttons for main component
        addEmployeeButton = new JButton("Add Employee");
        createScheduleButton = new JButton("Create Schedule");
        exportCalendarButton = new JButton("Export Calendar...");
        editAnExistingEmployeeButton = new JButton("Edit An Existing Employee");
        editAnExistingScheduleButton = new JButton("Edit An Existing Calendar");

        //Panels for main component: optionsPanel for buttons and calendarComponent for the day panels
        optionsPanel.setPreferredSize(new Dimension(220, 500));
        calenderPanel.setPreferredSize(new Dimension(650, 350));
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        calenderPanel.setLayout(new GridLayout(5,7));
        calendarComponent.setLayout(new BoxLayout(calendarComponent, BoxLayout.Y_AXIS));

        String calendarStr = MONTHS[date.get(Calendar.MONTH)] + " " + Integer.toString(date.get(Calendar.YEAR));
        calendarMonthYearLabel = new JLabel(calendarStr);

        optionsPanel.add(Box.createRigidArea(new Dimension(20, 400)));
        optionsPanel.add(addEmployeeButton);
        optionsPanel.add(createScheduleButton);
        optionsPanel.add(Box.createRigidArea(new Dimension(20,30)));
        optionsPanel.add(editAnExistingEmployeeButton);
        optionsPanel.add(editAnExistingScheduleButton);
        optionsPanel.add(Box.createRigidArea(new Dimension(20,30)));
        optionsPanel.add(exportCalendarButton);
        optionsPanel.setBackground(Color.DARK_GRAY);

        calendarComponent.add(calendarMonthYearLabel);


        for (int i = 1; i <= 28; i++) {
            /*
             * TODO:
             *  This is where all the days will be made from the day factory, this needs to pull from the DB
             *
             * */


            DayFactory day = new DayFactory(i);

            if(i == date.get(Calendar.DAY_OF_MONTH))
                day.setPanelColor(Color.DARK_GRAY);

            calenderPanel.add(day.getMainPanel());
        }

        calendarComponent.add(calenderPanel);

        System.out.println("Calendar bounds: " + calenderPanel.getHeight());

        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.add(BorderLayout.WEST, optionsPanel);
        mainFrame.add(BorderLayout.EAST, calendarComponent);

        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);

        // Connecting to database here
        System.out.println("[CONNECTION to DB]: Connecting to DB....");
        try{
            ConnectionString connectionString = new ConnectionString(
                    //DB URI GOES HERE

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
            createScheduleComponent = new CreateScheduleComponent(employeesCollections);
        });


    }


    public static void main(String[] args) {
        MainComponent mainComponent = new MainComponent();

    }
}
