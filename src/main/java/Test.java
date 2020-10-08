import sun.awt.WindowClosingListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Timer;

public class Test extends JFrame implements KeyListener {

    private static String SYS_CLOSE_MESSAGE = "確定要結束程式嗎?";
    private static String SYS_MESSAGE = "系統訊息";

    public Test() {
        Logger.getInstance();
        this.addKeyListener(this);
        Dimension dimension = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        dimension.setSize(dimension.width / 2, dimension.height / 2);
        this.setTitle("Logger Test");
        this.setSize(dimension);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                exit();
            }
        });

    }

    public static void main(String[] args) throws IOException {
        Test t = new Test();

    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.out.println("ESC pressed");
            exit();
        }

    }

    public void keyReleased(KeyEvent e) {


    }

    private void exit() {
        int choice = JOptionPane.showConfirmDialog(null, SYS_CLOSE_MESSAGE, SYS_MESSAGE, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == 0) {
            JOptionPane.showMessageDialog(null, "close", "application close", JOptionPane.INFORMATION_MESSAGE);
            Logger.exit();
            System.exit(0);
        }

    }

}
