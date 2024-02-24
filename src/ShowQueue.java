import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ShowQueue extends JPanel {
    private int allTime = 0;
    private final int maxTime;
    private final BlockingQueue<Client> queue;
    private boolean timeLimitExceeded = false;
    private final ArrayList<Double> clientsWait = new ArrayList<>();
    private final ArrayList<Double> clientsService = new ArrayList<>();

    public ShowQueue(int maxTime, int noClients) {
        this.maxTime = maxTime;
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(30, 500));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        queue = new ArrayBlockingQueue<>(noClients);

        JPanel checkOut = new JPanel();
        checkOut.setBackground(Color.BLUE);
        checkOut.setPreferredSize(new Dimension(20, 20));
        this.add(checkOut);

        beginWork();
    }

    public void addClient(Client client) {
        if (timeLimitExceeded)
            return;

        this.add(client);
        allTime += client.getTService();
        queue.add(client);
        client.setTimeSpent(allTime);
    }

    public void removeClient() {
        if (queue.isEmpty())
            return;

        Client curr = queue.peek();
        clientsWait.add((double) curr.getTimeSpent());
        clientsService.add((double) curr.getTServiceCopy());

        this.remove(curr);
        this.revalidate();
        this.repaint();

        queue.remove();
    }

    private void serveClient() {
        allTime--;
        if (!queue.isEmpty()) {
            Client curr = queue.peek();
            if (curr.update() == 0) {
                removeClient();
            }
        }
    }

    private void beginWork() {
        Thread worker = new Thread(() -> {
            try {
                while (!timeLimitExceeded) {
                    Thread.sleep(1000);
                    if (allTime > 0) {
                        serveClient();
                    }
                    if (MainTimer.getCount() == maxTime) {
                        timeLimitExceeded = true;
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        worker.start();
    }

    public int getAllTime() {
        return allTime;
    }

    public boolean getStatus() {
        return allTime == 0;
    }


    public boolean isClosed() {
        return timeLimitExceeded;
    }

    public Queue<Client> getQueue() {
        return queue;
    }

    public int getClientsWait() {
        return clientsWait.size();
    }

    public double averageTimeSpentEach() {
        double average = 0;
        for (double time : clientsWait) {
            average += (time / clientsWait.size());
        }

        return average;
    }

    public ArrayList<Double> getClientsService() {
        return clientsService;
    }

}
