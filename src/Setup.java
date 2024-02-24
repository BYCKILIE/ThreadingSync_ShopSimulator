import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Setup extends JFrame implements ActionListener {
    JButton start = new JButton("Start");
    JTextField[] entries = new JTextField[7];

    public Setup() {
        this.setTitle("Shop Simulation Manager");
        this.setSize(new Dimension(400, 390));
        this.setResizable(false);
        this.setIconImage(new ImageIcon("client.png").getImage());
        this.setLayout(new FlowLayout());

        JPanel[] panels = new JPanel[2];
        for (int i = 0; i < 2; i++) {
            panels[i] = new JPanel();
            panels[i].setPreferredSize(new Dimension(170, 400));
        }

        panels[0].setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 10));
        panels[1].setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));

        JPanel[] helpers = new JPanel[7];
        JLabel[] labels = new JLabel[7];
        String[] texts = new String[]{"No. of Clients:", "No. of Checks:", "Max Time:", "Min Arrival Time:",
        "Max Arrival Time:", "Min Service Time:", "Max Service Time:"};
        for (int i = 0; i < 7; i++) {
            helpers[i] = new JPanel();
            helpers[i].setPreferredSize(new Dimension(150, 30));
            helpers[i].setLayout(new FlowLayout(FlowLayout.RIGHT));
            labels[i] = new JLabel(texts[i]);
            labels[i].setFont(new Font("arial", Font.BOLD, 15));
            helpers[i].add(labels[i]);
            panels[0].add(helpers[i]);
        }

        for (int i = 0; i < 7; i++) {
            entries[i] = new JTextField();
            entries[i].setPreferredSize(new Dimension(150, 30));
            panels[1].add(entries[i]);
        }

        start.setPreferredSize(new Dimension(150, 30));
        start.addActionListener(this);
        panels[1].add(start);

        this.add(panels[0]);
        this.add(panels[1]);

        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }

    private boolean verify() {
        for (JTextField field : entries) {
            String temp = field.getText();
            if (temp.equals("")) {
                return true;
            }
            for (int i = 0; i < temp.length(); i++) {
                if (!Character.isDigit(temp.charAt(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    private int[] content() {
        int[] res = new int[7];
        for (int i = 0; i < 7; i++) {
            res[i] = Integer.parseInt(entries[i].getText());
        }
        return res;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            if (verify())
                return;
            int[] val = content();
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            new SimulationFrame(val[0], val[1], val[2], new int[]{val[3], val[4]}, new int[]{val[5], val[6]}, "Custom");
        }
    }
}
