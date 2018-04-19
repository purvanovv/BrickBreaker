import javax.swing.*;

public class MainClass {
    public static void main(String[] args) {
        Gameplay gameplay=new Gameplay();
        JFrame obj=new JFrame();
        obj.setBounds(10,10,710,600);
        obj.setTitle("Break Ball");
        obj.setVisible(true);
        obj.setResizable(false);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplay);
    }
}
