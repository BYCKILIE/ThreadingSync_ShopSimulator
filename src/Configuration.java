import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Queue;

public class Configuration extends JPanel {
    private final ShowQueue[] queues;
    private final int checkOuts;

    public Configuration(int checkOuts, int maxTime, int noClients) {
        this.setPreferredSize(new Dimension(1400, 510));
        this.setLayout(new FlowLayout());
        this.checkOuts = checkOuts;
        queues = new ShowQueue[checkOuts];

        for (int i = 0; i < checkOuts; i++) {
            this.add(queues[i] = new ShowQueue(maxTime, noClients));
        }

    }

    public void placeClient(Client client) {
        if (client == null)
            return;
        int min = Integer.MAX_VALUE;
        int pos = 0;
        for (int i = 0; i < checkOuts; i++) {
            if (queues[i].getStatus()) {
                queues[i].addClient(client);
                return;
            }
            if (queues[i].getAllTime() < min) {
                min = queues[i].getAllTime();
                pos = i;
            }
        }
        queues[pos].addClient(client);
    }

    public boolean finish() {
        for (ShowQueue queue : queues) {
            if (!queue.getStatus()) {
                return false;
            }
        }
        return true;
    }

    public boolean isClosed() {
        return queues[0].isClosed();
    }

    public int getCheckOuts() {
        return checkOuts;
    }

    public int totalClientsServed() {
        int count = 0;
        for (ShowQueue queue : queues) {
            count += queue.getClientsWait();
        }
        return count;
    }

    public double averageTimeSpent() {
        double average = 0;
        for (ShowQueue queue : queues) {
            average += (queue.averageTimeSpentEach() / checkOuts);
        }
        return average;
    }

    public int buildPeekHour() {
        int cont = 0;
        for (ShowQueue queue : queues) {
            cont += queue.getAllTime();
        }
        return cont;
    }

    public double averageTimeService() {
        int clientsServed = 0;
        for (ShowQueue queue : queues) {
            clientsServed += queue.getClientsWait();
        }

        double average = 0;
        for (ShowQueue queue : queues) {
            ArrayList<Double> util = queue.getClientsService();
            for (Double i : util) {
                average += i / clientsServed;
            }
        }
        return average;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < checkOuts; i++) {
            string.append("Queue").append(i + 1).append(": ");
            Queue<Client> queue = queues[i].getQueue();
            if (queue.isEmpty()) {
                string.append("closed\n");
                continue;
            }
            for (Client client : queue) {
                string.append(client);
            }
            string.append("\n");
        }
        return string.toString();
    }

}
