import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class MainTimer extends JLabel {
    private final SimulationFrame frame;
    private final Lobby lobby;
    private final Configuration configuration;

    private final FileOutputStream writer;
    private int peekHour = 0, lastPeek = 0;
    private static int count = 0;

    public MainTimer(SimulationFrame frame, Lobby lobby, Configuration configuration) {
        this.frame = frame;
        this.lobby = lobby;
        this.configuration = configuration;

        this.setHorizontalAlignment(CENTER);
        this.setPreferredSize(new Dimension(1400, 40));

        this.setFont(new Font("SansSerif Bold", Font.BOLD, 35));
        this.setText("000");

        try {
            writer = new FileOutputStream("LogTest_" + frame.getTestCaseNo() + ".txt");
            this.startTimer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean finish() {
        if (configuration.isClosed()) {
            return true;
        }
        if (lobby.finish())
            return configuration.finish();
        return false;
    }

    public void startTimer() throws IOException {
        Thread timer = new Thread(() -> {
            try {
                Thread.sleep(1000);
                while (!finish()) {
                    updateClientsState();
                    updateTimerState();
                    updateFileCurrState();

                    Thread.sleep(1000);
                    count++;
                }

                writeFinalStats();
                frame.hideFrame();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        timer.start();
    }

    private void updateClientsState() {
        ArrayList<Client> curr = lobby.placeInLine(count, configuration.isClosed());
        if (curr != null) {
            for (Client client : curr) {
                configuration.placeClient(client);
                lobby.revalidate();
                lobby.repaint();
                configuration.revalidate();
                configuration.repaint();
            }
        }

        int tempPeek = configuration.buildPeekHour();
        if (tempPeek > lastPeek) {
            peekHour = count;
            lastPeek = tempPeek;
        }
    }

    private void updateFileCurrState() {
        try {
            writer.write(("Time " + count + "\n").getBytes());
            if (lobby.toString().equals("")) {
                writer.write(("Waiting clients: none\n").getBytes());
            } else {
                writer.write(("Waiting clients: " + lobby + "\n").getBytes());
            }
            writer.write((configuration + "\n").getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateTimerState() {
        if (count < 10) {
            this.setText("00" + count);
        } else if (count < 100) {
            this.setText("0" + count);
        } else {
            this.setText(String.valueOf(count));
        }
    }

    private void writeFinalStats() {
        try {
            writer.write(("For Test No.<" + frame.getTestCaseNo() + "> with parameters: |" +
                    lobby.getNoClients() + " Clients| -- |" +
                    configuration.getCheckOuts() + " CheckOuts| -- |" +
                    lobby.getMaxTime() + " MaxDuration|" + "\n").getBytes());
            writer.write(("Program Stats:\n    -> " + configuration.totalClientsServed() +
                    " (clients) - served\n    -> " + count + " (seconds) - time spent serving\n    -> " +
                    String.format("%.02f", configuration.averageTimeSpent()) +
                    " (seconds) - average waiting time per client\n    -> " +
                    String.format("%.02f", configuration.averageTimeService()) +
                    " (seconds) - average service time per client\n    -> " +
                    peekHour + " (time) - peek hour\n").getBytes());
            if (configuration.isClosed()) {
                writer.write(("Note<Time limit exceeded (Store could not serve all clients)>\n").getBytes());
            } else {
                writer.write(("Note<Store served all clients>\n").getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getCount() {
        return count;
    }

}
