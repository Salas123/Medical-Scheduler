import javax.swing.*;

public class CreateScheduleComponent
{
    static JFrame createScheduleFrame;
    static JPanel mainPanel, buttonPanel;
    static JButton optimizeButton, previewButton, cancelButton;

    public CreateScheduleComponent()
    {
        //Create schedule component
        createScheduleFrame = new JFrame("Create Schedule");
        createScheduleFrame.setLayout(new BoxLayout(createScheduleFrame, BoxLayout.Y_AXIS));
        createScheduleFrame.setSize(500, 200);
        createScheduleFrame.setResizable(false);
        createScheduleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        optimizeButton = new JButton("Optimize");
        previewButton = new JButton("Preview...");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(optimizeButton);
        buttonPanel.add(previewButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel);


        createScheduleFrame.setContentPane(mainPanel);
        createScheduleFrame.setLocationRelativeTo(null);
        createScheduleFrame.setVisible(true);
    }
}
