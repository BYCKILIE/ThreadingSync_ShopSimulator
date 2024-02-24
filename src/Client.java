import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Client extends JLayeredPane {
    private int timeSpent;
    private final int id, tArrival;
    private final AtomicInteger tService;
    private final int tServiceCopy;
    private final JLabel timeTaken;

    public Client(int id, int tArrival, int tService) {
        this.id = id;
        this.tArrival = tArrival;
        this.tService = new AtomicInteger(tService);
        this.tServiceCopy = tService;

        this.setPreferredSize(new Dimension(26, 26));
        this.setLayout(null);

        JLabel shape = new JLabel();
        shape.setIcon(new ImageIcon("client.png"));
        shape.setBounds(0, 0, 27, 28);

        timeTaken = new JLabel(String.valueOf(tService));
        timeTaken.setBounds(9, 3, 20, 20);

        this.add(timeTaken, 0);
        this.add(shape, 1);
    }

    public int getTService() {
        return tService.get();
    }

    public int update() {
        int time = tService.decrementAndGet();
        timeTaken.setText(String.valueOf(tService));
        return time;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    public int getTServiceCopy() {
        return tServiceCopy;
    }

    @Override
    public String toString() {
        return "(" + id + ", " + tArrival + ", " + tService + "); ";
    }
}
