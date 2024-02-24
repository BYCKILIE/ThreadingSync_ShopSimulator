import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Lobby extends JPanel {
    private final HashMap<Integer, ArrayList<Client>> shop;
    private final int noClients;
    private final int maxTime;
    StringBuilder clientsString = new StringBuilder();

    public Lobby(int noClients, int maxTime, int[] tArrival, int[] tService) {
        this.noClients = noClients;
        this.maxTime = maxTime;
        this.setPreferredSize(new Dimension(1400, 258));
        this.setLayout(new FlowLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        shop = new RandomClientGenerator(noClients, tArrival, tService, clientsString, this).getShop();
    }

    public ArrayList<Client> placeInLine(int timeNow, boolean isClosed) {
        if (isClosed)
            return null;
        ArrayList<Client> res = shop.remove(timeNow);
        if (res == null)
            return null;
        for (Client client : res) {
            int start = clientsString.indexOf(client.toString());
            int end = start + client.toString().length();
            clientsString.delete(start, end);
            this.remove(client);
        }
        return res;
    }

    public boolean finish() {
        return shop.isEmpty();
    }

    public int getNoClients() {
        return noClients;
    }

    public int getMaxTime() {
        return maxTime;
    }

    @Override
    public String toString() {
        return clientsString.toString();
    }
}
