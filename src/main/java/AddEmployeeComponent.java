import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class AddEmployeeComponent
{
    static JFrame addEmployeeFrame;
    static JPanel topThird;
    static JButton addButton, cancelButton;
    static JPanel firstNamePanel, lastNamePanel, dropDownPanel, buttonPanel,descriptorsPanel, middlePanel ,mainPanel;
    static JTextField firstNameInput, lastNameInput;
    static JLabel firstNameLabel, lastNameLabel, descriptorLabel;
    static JComboBox concentrationComboBox, standingComboBox, rankingComboBox;
    static String[] concentrationStrings = new String[]{"--Choose Concentration--", "Internal Medicine", "Emergency Medicine"};
    static String[] standingStrings = new String[]{"--Attending or Resident--","Attending", "Resident"};
    static String[] rankingStrings = new String[]{"--Choose Ranking--","Rank 1", "Rank 2", "Rank 3", "Rank 4", "Chief", "PA","Regular", "APD", "AF", "Core"};
    static String standing, concentration, ranking;
    static MongoCollection<org.bson.Document> employeeDB;

    public AddEmployeeComponent(MongoCollection<org.bson.Document> employeeDB)
    {
        // Add Employee Frame
        addEmployeeFrame = new JFrame("Add Employee");
        addEmployeeFrame.setSize(800, 200);
        addEmployeeFrame.setLayout(new BoxLayout(addEmployeeFrame, BoxLayout.Y_AXIS));

        // Input fields for first name and last name;
        firstNameInput = new JTextField("Add first name here...");
        lastNameInput = new JTextField("Add last name here..." );

        // Buttons
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");

        // Labels for descriptions
        firstNameLabel = new JLabel("First Name:");
        lastNameLabel = new JLabel("Last Name:");
        descriptorLabel = new JLabel("Select the following features for the employee");

        // Container for first name input
        firstNamePanel = new JPanel();
        firstNamePanel.setLayout(new BoxLayout(firstNamePanel, BoxLayout.X_AXIS));
        firstNamePanel.add(firstNameLabel);
        firstNamePanel.add(firstNameInput);
        firstNamePanel.setPreferredSize(new Dimension(400, 25));
        firstNamePanel.setMaximumSize(new Dimension(400, 25));
        // Container for last name input
        lastNamePanel = new JPanel();
        lastNamePanel.setLayout(new BoxLayout(lastNamePanel, BoxLayout.X_AXIS));
        lastNamePanel.add(lastNameLabel);
        lastNamePanel.add(lastNameInput);
        lastNamePanel.setPreferredSize(new Dimension(400, 25));
        lastNamePanel.setMaximumSize(new Dimension(400, 25));

        // Dropdown menus
        concentrationComboBox = new JComboBox(concentrationStrings);
        standingComboBox = new JComboBox(standingStrings);
        rankingComboBox = new JComboBox(rankingStrings);

        // Container for dropdown menus
        descriptorsPanel = new JPanel();
        descriptorsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        descriptorsPanel.add(descriptorLabel);

        dropDownPanel = new JPanel();
        dropDownPanel.setLayout(new BoxLayout(dropDownPanel, BoxLayout.X_AXIS));
        dropDownPanel.add(standingComboBox);
        dropDownPanel.add(concentrationComboBox);
        dropDownPanel.add(rankingComboBox);
        dropDownPanel.setPreferredSize(new Dimension(800, 25));
        dropDownPanel.setMaximumSize(new Dimension(800, 25));

        middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.add(descriptorsPanel);
        middlePanel.add(dropDownPanel);
        middlePanel.setPreferredSize(new Dimension(800, 50));
        middlePanel.setMaximumSize(new Dimension(800, 50));


        // Container for buttons
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);


        topThird = new JPanel();
        topThird.setLayout(new BoxLayout(topThird, BoxLayout.Y_AXIS));
        topThird.add(firstNamePanel);
        topThird.add(lastNamePanel);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(topThird);
        mainPanel.add(middlePanel);
        mainPanel.add(buttonPanel);

        addEmployeeFrame.setContentPane(mainPanel);
        addEmployeeFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addEmployeeFrame.setLocationRelativeTo(null);
        addEmployeeFrame.setVisible(true);

        this.employeeDB = employeeDB;

        //Button action listeners
        cancelButton.addActionListener(e -> {
            System.out.println("[Add Employee Component]: Closing down add employee component...");
            addEmployeeFrame.dispose();
        });

        // Adding employee to DB
        addButton.addActionListener(e -> {

            System.out.println("[Add Employee Component]: Adding employee to DB...");
            if(standingComboBox.getSelectedIndex() == 0 || concentrationComboBox.getSelectedIndex() == 0 || rankingComboBox.getSelectedIndex() == 0)
            {
                JOptionPane.showMessageDialog(addEmployeeFrame, "One or more of the following selections was not chosen...",
                    "ERROR", JOptionPane.ERROR_MESSAGE);

                System.out.println("[Adding Employee Component]: No item was selected from the dropdown menus....");
            }
            else
            {
                standing = standingStrings[standingComboBox.getSelectedIndex()];
                concentration = concentrationStrings[concentrationComboBox.getSelectedIndex()];
                ranking = rankingStrings[rankingComboBox.getSelectedIndex()];

                if(inputValidation(standing, concentration, ranking))
                {
                    String strToShow = "Does this look correct?\nFirst Name: " + firstNameInput.getText() + "\nLast Name: " + lastNameInput.getText()
                            + "\nClass Level: " + standing + "\nConcentration: " + concentration + "\nRank: " + ranking;

                    int option = JOptionPane.showConfirmDialog(addEmployeeFrame, strToShow, "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if(option == JOptionPane.YES_OPTION)
                    {

                         JOptionPane.showMessageDialog(addEmployeeFrame, "Adding " + firstNameInput.getText()
                         + " " + lastNameInput.getText() + " to database!", "Success", JOptionPane.INFORMATION_MESSAGE);

                         /*
                         * Creating a document with the following info of the employee... number of shifts gets assigned once the
                         * employee is pulled from the DB to make an optimized schedule
                         * */

                        String employeeId = createRandomizedHash();

                        Document employeeDocument = new Document("_id", new ObjectId());
                        employeeDocument.append("First_Name", firstNameInput.getText())
                                .append("Last_Name", lastNameInput.getText())
                                .append("Standing", standing)
                                .append("Concentration", concentration)
                                .append("Rank", ranking)
                                .append("Employee_ID", employeeId);

                        System.out.println("[Adding Employee Component]: Inserting Employee....");
                        employeeDB.insertOne(employeeDocument);

                        // close window once the employee is added to the DB
                        System.out.println("[Add Employee Component]: Closing down add employee component...");
                        addEmployeeFrame.dispose();
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(addEmployeeFrame, "Employee fields do not match with their respective concentrations and/or standing!", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                    System.out.println("[Adding Employee Component]: Input validation found incorrect combination....");
                }
            }

        });



    }



    //This function provides validation on whether the correct concentrations are paired with their correct rankings
    static boolean inputValidation(String standing_param, String concentration_param, String ranking_param)
    {
            if(concentration_param.equals("Emergency Medicine") && ranking_param.equals("PA"))
                return false;
            if(concentration_param.equals("Internal Medicine") && (ranking_param.equals("Chief") || ranking_param.equals("Rank 4")))
                return false;
            if(standing_param.equals("Attending") && !(ranking_param.equals("Regular") || ranking_param.equals("APD") || ranking_param.equals("AF") || ranking_param.equals("Core")))
                return false;

            return true;
    }

    // This function creates the random employee id for the DB, so it can help with future querying and not relying on objectID() method
    static String createRandomizedHash()
    {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

       return generatedString;
    }
}
