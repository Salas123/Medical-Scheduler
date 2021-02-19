import javax.swing.*;
import java.awt.*;

public class CreateScheduleComponent
{
    static JFrame createScheduleFrame;
    static JPanel mainPanel, buttonPanel, labelPanel, comboBoxPanel;
    static JLabel monthsDescriptorLabel;
    static JButton optimizeButton, previewButton, cancelButton;
    static String[] MONTHS = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    static JComboBox monthsComboBox;

    /*
    * TODO:
    *  - Need to add drop down menu for indexing which month the user would like to create schedule for
    *  - Add basic logic for adding 1st year employees into a schedule
    *
    * */

    public CreateScheduleComponent()
    {
        //Create schedule component
        createScheduleFrame = new JFrame("Create Schedule");
        createScheduleFrame.setLayout(new BoxLayout(createScheduleFrame, BoxLayout.Y_AXIS));
        createScheduleFrame.setSize(500, 150);
        createScheduleFrame.setResizable(false);
        createScheduleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));

        comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.X_AXIS));

        monthsDescriptorLabel = new JLabel("Select Month To Create Schedule For:");

        monthsComboBox = new JComboBox(MONTHS);


        optimizeButton = new JButton("Optimize");
        previewButton = new JButton("Preview...");
        cancelButton = new JButton("Cancel");


        buttonPanel.add(optimizeButton);
        buttonPanel.add(previewButton);
        buttonPanel.add(cancelButton);

        previewButton.setVisible(false);

        labelPanel.add(monthsDescriptorLabel);

        comboBoxPanel.add(monthsComboBox);
        comboBoxPanel.setPreferredSize(new Dimension(250, 50));
        comboBoxPanel.setMaximumSize(new Dimension(250, 50));

        mainPanel.add(labelPanel);
        mainPanel.add(comboBoxPanel);
        mainPanel.add(buttonPanel);


        createScheduleFrame.setContentPane(mainPanel);
        createScheduleFrame.setLocationRelativeTo(null);
        createScheduleFrame.setVisible(true);


        optimizeButton.addActionListener(e -> {
            previewButton.setVisible(true);
            /*
            * TODO:
            *  -This is where the optimizing will take place where each respective employee from the database will be put in their shift then
            *   all shifts will be encapsulated in a day, then the days will encapsulated into weeks
            *
            * */

        });


        previewButton.addActionListener(e -> {
            /*
            * TODO:
            *   - This is where the user will be able to see the window with the optimize calendar and user can edit last minute
            * */
        });

        cancelButton.addActionListener(e -> {
        createScheduleFrame.dispose();
    });
    }
}
