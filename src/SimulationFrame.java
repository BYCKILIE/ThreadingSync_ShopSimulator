import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class SimulationFrame extends JFrame {
    private final String testCaseNo;

    public SimulationFrame(int clients, int checkOuts, int maxTime, int[] tArrival, int[] tService, String testCaseNo) {
        this.testCaseNo = testCaseNo;

        this.setTitle("Shop Simulation");
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setResizable(false);
        this.setIconImage(new ImageIcon("client.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        JPanel action = new JPanel();
        action.setLayout(new FlowLayout());
        action.setPreferredSize(new Dimension(1400, 800));

        Lobby lobby = new Lobby(clients, maxTime, tArrival, tService);
        Configuration configuration = new Configuration(checkOuts, maxTime, clients);

        action.add(configuration);
        action.add(lobby);

        this.add(new MainTimer(this, lobby, configuration));
        this.add(action);

        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }

    public void hideFrame() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public String getTestCaseNo() {
        return testCaseNo;
    }

}
