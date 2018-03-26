import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    private static String[] str = {"     ","Название", "Автор", "Дата издания", "Издатель", "Количество страниц", "Количество книг"};
    private static BookManager bookManager = new BookManager();
    private static MyListeners myListeners = new MyListeners(bookManager);
    private static int index = 0;
    private static boolean check = false;

    public Main()
    {
        JFrame frame = new JFrame("Accounting system");
        JTable table = new JTable(bookManager);
        JScrollPane scrollPane = new JScrollPane(table);

        frame.setSize(700,500);
        frame.setLocation(100,100);
        frame.setDefaultCloseOperation( EXIT_ON_CLOSE );

        JPanel flow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        createPanel(flow,frame);
        JPanel border = new JPanel(new BorderLayout());
        border.add(flow, BorderLayout.NORTH);
        border.add(scrollPane,BorderLayout.CENTER);
        frame.getContentPane().add(border);
        frame.pack();
        frame.setVisible(true);
        }

    public static void createPanel (Container container, JFrame frame){

        JButton buttonLook, buttonNew, buttonSearch, buttonChange, buttonDelete, buttonCancel;

        JLabel label;
        JTextField textField;

        container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        container.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        buttonLook = new JButton("Все книги");

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(4,2,0,2);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        container.add (buttonLook, constraints);

        buttonNew = new JButton("Новая запись");
        constraints.gridx = 1;
        container.add (buttonNew, constraints);

        buttonSearch = new JButton("Поиск по атрибуту");
        constraints.gridx = 2;
        container.add (buttonSearch, constraints);

        buttonChange = new JButton("Изменение атрибута");
        constraints.gridx = 3;
        container.add (buttonChange, constraints);

        buttonDelete = new JButton("Удаление записи");
        constraints.gridx = 4;
        container.add (buttonDelete, constraints);

        label = new JLabel("Поле для ввода значений атрибутов: ");
        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        container.add(label, constraints);

        buttonCancel = new JButton("Отмена");
        constraints.gridy = 1;
        constraints.gridx = 4;
        container.add(buttonCancel,constraints);
        buttonCancel.setEnabled(false);

        textField = new JTextField();
        constraints.gridy = 1;
        constraints.gridx = 2;
        constraints.gridwidth = 2;
        container.add(textField, constraints);
        textField.setEnabled(false);

        myListeners.listenerFrame(frame,buttonSearch,buttonChange,buttonDelete);
        myListeners.listenerButtonNew(textField,label,buttonLook,buttonNew,buttonSearch,buttonChange,buttonDelete,buttonCancel);
        listenerButtonSearch(frame,buttonSearch);
        listenerButtonChange(frame,buttonChange);
        listenerButtonDelete(frame,buttonDelete);
        myListeners.listenerButtonCancel(textField,label,buttonLook,buttonNew,buttonSearch,buttonChange,buttonDelete,buttonCancel);
        myListeners.listenerTextField(textField,label,buttonLook,buttonNew,buttonSearch,buttonChange,buttonDelete,buttonCancel);

    }

    private static JDialog createDialog(JFrame frame, String title, boolean modal, int choice)
    {
        JDialog dialog = new JDialog(frame, title, modal);

        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setLocation(800,100);
        dialog.setSize(200, 200);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(4,2,0,2);
        constraints.gridx = 0;
        constraints.gridy = 0;
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


        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(new JLabel("Введите значение атрибута: "),constraints);

        JTextField textValue = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(textValue, constraints);
        textValue.setEnabled(false);
        textID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (choice == 2) {
                    comboBox.setEnabled(true);
                    comboBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            textValue.setEnabled(true);
                        }
                    });
                }
            }
        });
        if (choice == 1) {
            comboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED){
                        textValue.setEnabled(true);
                    }
                }
            });
        }

        JButton buttonOk = new JButton("Ok");
        constraints.gridx = 2;
        constraints.gridy = 1;
        panel.add(buttonOk,constraints);
        buttonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (choice == 3 && check){
                    bookManager.removeBook(index);
                    check = false;
                }
                dialog.dispose();
            }
        });

        textID.addCaretListener(new CaretListener() {

            private void removeCase(){
                try {
                    index = Integer.parseInt(textID.getText());
                    if (index < 0 || index > (bookManager.getRowCount() - 1))
                    {
                        throw new NumberFormatException();
                    }
                    check = true;
                }
                catch (NumberFormatException | IllegalStateException exc)
                {
                    check = false;
                }
            }

            @Override
            public void caretUpdate(CaretEvent e) {
                if (choice == 3) {
                    removeCase();
                }
            }
        });

        Container container = dialog.getContentPane();
        container.add(panel);
        dialog.pack();
        return dialog;
    }

    private static void listenerButtonSearch (JFrame frame, JButton buttons) {
        buttons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = Main.createDialog(frame,"Выбор атрибута для поиска",true,1);
                dialog.setVisible(true);
            }
        });
    }
    private static void listenerButtonChange (JFrame frame, JButton buttons) {
        buttons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = Main.createDialog(frame,"Выбор атрибута и id на изменение",true,2);
                dialog.setVisible(true);
            }
        });
    }
    private static void listenerButtonDelete (JFrame frame, JButton buttons) {
        buttons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = Main.createDialog(frame," id на удаление",true,3);
                dialog.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });

    }
}
