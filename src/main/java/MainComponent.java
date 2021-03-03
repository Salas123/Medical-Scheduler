import javax.swing.*;
import java.awt.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientSettings;
import org.bson.Document;


/*
*  Sun Mon Tues Wed Thurs Fri Sat
*
* */


public class MainComponent
{

    static JFrame mainFrame;
    static JLabel calendarMonthYearLabel, sundayLabel, mondayLabel, tuesdayLabel, wednesdayLabel, thursdayLabel, fridayLabel, saturdayLabel;
    static JPanel optionsPanel, calendarComponent, calenderDaysPanel, daysLabelPanel, mainPanel;
    static JButton addEmployeeButton, editAnExistingEmployeeButton, editAnExistingScheduleButton, createScheduleButton, exportCalendarButton;
    static AddEmployeeComponent employeeComponent;
    static CreateScheduleComponent createScheduleComponent;
    static EditExisitingEmployeeComponent editExisitingEmployeeComponent;
    static Calendar date;
    private String[] MONTHS = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    static int[] DAYS_IN_MONTHS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    static MongoClient mongoClient;
    static MongoDatabase employeeDatabase;
    static MongoCollection<Document> employeesCollections;

    public MainComponent()
    {
        date = Calendar.getInstance();


        // main component
        mainFrame = new JFrame("Salas Scheduler");
        mainFrame.setSize(1400, 650);
        mainFrame.setResizable(false);


        // sub components
        optionsPanel = new JPanel();
        calenderDaysPanel = new JPanel();
        calendarComponent = new JPanel();
        daysLabelPanel = new JPanel();
        mainPanel = new JPanel();

        // Buttons for main component
        addEmployeeButton = new JButton("Add Employee");
        createScheduleButton = new JButton("Create Schedule");
        exportCalendarButton = new JButton("Export Calendar...");
        editAnExistingEmployeeButton = new JButton("Edit An Existing Employee");
        editAnExistingScheduleButton = new JButton("Edit An Existing Calendar");

        //Panels for main component: optionsPanel for buttons and calendarComponent for the day panels
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        calenderDaysPanel.setLayout(new GridLayout(5, 7));
        calendarComponent.setLayout(new BoxLayout(calendarComponent, BoxLayout.Y_AXIS));
        calendarComponent.setPreferredSize(new Dimension(1400, 650));
        calenderDaysPanel.setPreferredSize(new Dimension(1400, 600));

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
        // adding the label for the month
        calendarComponent.add(calendarMonthYearLabel);
        calendarMonthYearLabel.setForeground(Color.BLUE);

        // instantiating day name labels
        sundayLabel = new JLabel("Sunday");
        mondayLabel = new JLabel("Monday");
        tuesdayLabel = new JLabel("Tuesday");
        wednesdayLabel = new JLabel("Wednesday");
        thursdayLabel = new JLabel("Thursday");
        fridayLabel = new JLabel("Friday");
        saturdayLabel = new JLabel("Saturday");
        // add labels to calendar component
        daysLabelPanel.setLayout(new GridLayout());
        daysLabelPanel.add(sundayLabel);
        daysLabelPanel.add(mondayLabel);
        daysLabelPanel.add(tuesdayLabel);
        daysLabelPanel.add(wednesdayLabel);
        daysLabelPanel.add(thursdayLabel);
        daysLabelPanel.add(fridayLabel);
        daysLabelPanel.add(saturdayLabel);

        sundayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mondayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tuesdayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wednesdayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        thursdayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fridayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        saturdayLabel.setHorizontalAlignment(SwingConstants.CENTER);

        calendarComponent.add(daysLabelPanel);

        // This for adding empty boxes to demonstrate what days the month actually starts at and ends (i.e. Monday, etc.)
        int boxesLeftToAdd = 35 - addEmptyBoxes(getFirstDateOfCurrentMonth(), calenderDaysPanel);

        for (int i = 1; i <= DAYS_IN_MONTHS[date.get(Calendar.MONTH)]; i++) {


            DayFactory day = new DayFactory(i);

            if(i == date.get(Calendar.DAY_OF_MONTH))
                day.setPanelColor(Color.DARK_GRAY);

            calenderDaysPanel.add(day.getMainPanel());
        }

        boxesLeftToAdd = boxesLeftToAdd - DAYS_IN_MONTHS[date.get(Calendar.MONTH)];
        System.out.println(boxesLeftToAdd);
        addEmptyBoxes(boxesLeftToAdd, calenderDaysPanel);

        calendarComponent.add(calenderDaysPanel);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.add(optionsPanel);
        mainPanel.add(calendarComponent);

        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setContentPane(mainPanel);
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);

        // Connecting to database here
        System.out.println("[CONNECTION to DB]: Connecting to DB....");
        try{
            ConnectionString connectionString = new ConnectionString(
                    //DB URI GOES HERE
                    "mongodb+srv://scheduler_user_001:2VTJ8dAIwG6Bg4rJ@schedulecluster.3vcnp.mongodb.net/SchedulerDB?retryWrites=true&w=majority"
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
                    System.out.println("[Add Employee Component]: Opening up employee component...");
                    employeeComponent = new AddEmployeeComponent(employeesCollections);
                }
        );

        // Creating schedule button listner button.. this opens up the creating schedule window
        createScheduleButton.addActionListener(e -> {
            System.out.println("[Create Schedule Component]: Opening up create schedule component...");
            createScheduleComponent = new CreateScheduleComponent(employeesCollections);
        });

        editAnExistingEmployeeButton.addActionListener(e -> {
            System.out.println("[Edit Employee Component]: Opening up edit employee component...");
            editExisitingEmployeeComponent = new EditExisitingEmployeeComponent(employeesCollections);
        });


    }


    public static void main(String[] args) {
        MainComponent mainComponent = new MainComponent();

    }


    static String getFirstDateOfCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));

        DateFormat dateFormat = new SimpleDateFormat("EEEEEEEE");


       return dateFormat.format(cal.getTime());
    }

    // add empty boxes to panel where days go
    static int addEmptyBoxes(String startDay, JPanel panelToAdd)
    {
        int emptyBoxes = 0;
        if(startDay.equals("Monday"))
            emptyBoxes = 1;
        else if(startDay.equals("Tuesday"))
            emptyBoxes = 2;
        else if (startDay.equals("Wednesday"))
            emptyBoxes = 3;
        else if(startDay.equals("Thursday"))
            emptyBoxes = 4;
        else if(startDay.equals("Friday"))
            emptyBoxes = 5;
        else if(startDay.equals("Saturday"))
            emptyBoxes = 6;

        for(int i = 0; i < emptyBoxes; i++)
        {
            DayFactory day = new DayFactory(0);
            panelToAdd.add(day.getMainPanel());
        }

        return emptyBoxes;
    }

    static int addEmptyBoxes(int boxesToAdd, JPanel panelToAdd)
    {
        for(int i = 0; i < boxesToAdd; i++)
        {
            DayFactory day = new DayFactory(0);
            panelToAdd.add(day.getMainPanel());
        }

        return 0;
    }
}
