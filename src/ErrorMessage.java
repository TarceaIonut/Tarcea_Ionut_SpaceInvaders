import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorMessage extends JDialog implements ActionListener,GameComponent {
    public ErrorMessage(String title, String message, DisplayOld d, SignUpDialog signDialog) {
        super(signDialog, true);
        this.setTitle(title);
        JButton ok = new JButton("OK");
        ok.addActionListener(this);
        JLabel l = new JLabel(message);

        setDesign(ok);
        setDesign(l);

        this.setLayout(new BorderLayout());
        this.add(l, BorderLayout.CENTER);
        this.add(ok, BorderLayout.SOUTH);
        this.setSize(new Dimension(1000, 200));
        this.setLocation(d.getLocation().x + 250, d.getLocation().y + 350);
        this.setVisible(true);
        //this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
    }
}
