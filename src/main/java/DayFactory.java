import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class DayFactory {
    public JFrame overviewDayFrame;
    public String dayNum;
    public JTextArea dayTextArea;
    public JPanel mainPanel, showDayMainPanel, comboShowDayPanel,buttonShowDayPanel;
    public JButton viewButton, editButton, closeButton;
    public JLabel dayLabel, areaComboLabel;
    public Day day;
    public JComboBox areaComboBox;
    public final String[] AREA_COLORS = new String[]{"Red", "Blue", "Green", "Orange", "Brown"};

    public DayFactory(int intDayNum)
    {
        /*
        * TODO:
        *  -This DayFactory will pass in a day.class as a parameter
        *  -From the day object, display each selected area's employees staffed that day
        *  -When a different area is selected, update the text area with the respective area selected
        *
        * */
        dayNum = Integer.toString(intDayNum);

        // day overview frame
        overviewDayFrame = new JFrame("Day Overview");
        overviewDayFrame.setSize(new Dimension(400, 400));
        overviewDayFrame.setResizable(false);
        overviewDayFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        overviewDayFrame.setLocationRelativeTo(null);

        //label for day number and combobox
        dayLabel = new JLabel(dayNum);
        areaComboLabel = new JLabel("Select Area:");

        // text pane that will show the overview of the day of that specific area
        dayTextArea = new JTextArea("This is where the info for each specific area will be shown...");
        dayTextArea.setEditable(false);


        // view button for panel in the home window
        viewButton = new JButton("View");
        // buttons for day overview frame
        editButton = new JButton("Edit");
        closeButton = new JButton("Close");

        // ComboBox for selecting area where workers are scheduled
        areaComboBox = new JComboBox(AREA_COLORS);

        // panel for home screen
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        // main panel for day overview frame
        showDayMainPanel = new JPanel();
        showDayMainPanel.setLayout(new BoxLayout(showDayMainPanel, BoxLayout.Y_AXIS));
        //top third of day overview window
        comboShowDayPanel = new JPanel();
        comboShowDayPanel.setLayout(new BoxLayout(comboShowDayPanel, BoxLayout.X_AXIS));
        //bottom third of day overview window
        buttonShowDayPanel = new JPanel();
        buttonShowDayPanel.setLayout(new BoxLayout(buttonShowDayPanel, BoxLayout.X_AXIS));

        //add components to the top part of the day overview
        comboShowDayPanel.add(areaComboLabel);
        comboShowDayPanel.add(areaComboBox);
        buttonShowDayPanel.add(editButton);
        buttonShowDayPanel.add(closeButton);
        showDayMainPanel.add(comboShowDayPanel);
        showDayMainPanel.add(dayTextArea);
        showDayMainPanel.add(buttonShowDayPanel);

        //adding components for day panel in home screen and stylizing
        mainPanel.add(dayLabel);
        mainPanel.add(viewButton);
        mainPanel.setBorder(new LineBorder(Color.WHITE));

        // creating day object
        day = new Day(intDayNum);

        // setting showDayMainPanel to be main panel of the day overview frame, so all different components go in here
        overviewDayFrame.setContentPane(showDayMainPanel);

        // when view button is pressed, this will show the day over
        viewButton.addActionListener(e -> {
                overviewDayFrame.setVisible(true);
        });

        closeButton.addActionListener(e -> {
            overviewDayFrame.dispose();
        });
    }

    public void setPanelColor(Color color)
    {
        mainPanel.setBackground(color);
        dayLabel.setForeground(Color.white);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
