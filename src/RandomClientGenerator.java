import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RandomClientGenerator {
    private final HashMap<Integer, ArrayList<Client>> shop = new HashMap<>();

    public RandomClientGenerator(int noClients, int[] tArrival, int[] tService, StringBuilder string, JPanel panel) {
        Random random = new Random();
        for (int i = 0; i < noClients; i++) {
            int arrive = random.nextInt(tArrival[0], tArrival[1] + 1);
            int service = random.nextInt(tService[0], tService[1] + 1);
            Client curr = new Client(i + 1, arrive, service);
            panel.add(curr);

            string.append(curr);
            if (shop.containsKey(arrive)) {
                shop.get(arrive).add(curr);
            } else {
                ArrayList<Client> temp = new ArrayList<>();
                temp.add(curr);
                shop.put(arrive, temp);
            }
        }
    }

    public HashMap<Integer, ArrayList<Client>> getShop() {
        return shop;
    }

}
