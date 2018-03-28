import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class MyDialog {
    private String[] str = {"     ","Название", "Автор", "Дата издания", "Издатель", "Количество страниц", "Количество книг"};
    private static int choice;
    private BookManager bookManager;
    private MyDialogListeners myDialogListeners;

    public MyDialog (BookManager bookManager){
        this.bookManager = bookManager;
        myDialogListeners = new MyDialogListeners(bookManager);
    }

    public JDialog createDialog(JFrame frame, String title, boolean modal, int choice)
    {
        this.choice = choice;
        JDialog dialog = new JDialog(frame, title, modal);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setLocation(800,100);
        dialog.setSize(200, 200);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(4,2,0,2);

        JLabel label = new JLabel("  ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(label,constraints);

        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(new JLabel("Введите id от 0 до " + (bookManager.getRowCount() - 1) + " :"),constraints);

        JTextField textID = new JTextField(5);
        constraints.gridx = 1;
        constraints.weightx = 0.5;
        panel.add(textID,constraints);
        if (choice == 2 || choice == 3) { textID.setEnabled(true); }
        else { textID.setEnabled(false); }

        JComboBox comboBox = new JComboBox(str);
        constraints.gridx = 2;
        panel.add(comboBox,constraints);
        if (choice == 2 || choice == 3) { comboBox.setEnabled(false); }
        else { comboBox.setEnabled(true); }

        JLabel labelTextValue = new JLabel("Введите значение атрибута: ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(labelTextValue,constraints);

        JTextField textValue = new JTextField(10);
        constraints.gridx = 1;
        panel.add(textValue, constraints);
        textValue.setEnabled(false);

        JButton buttonOk = new JButton("Ok");
        constraints.gridx = 2;
        panel.add(buttonOk,constraints);

        myDialogListeners.textIDListener(textID, comboBox);
        myDialogListeners.comboboxListener(comboBox,textValue,labelTextValue);
        myDialogListeners.textValueKeyListener(textValue,labelTextValue);
        myDialogListeners.okListener(dialog,buttonOk);
        myDialogListeners.dialogListener(dialog,label);

        Container container = dialog.getContentPane();
        container.add(panel);
        dialog.pack();
        return dialog;
    }

    public static int getChoice() {
        return choice;
    }
}
