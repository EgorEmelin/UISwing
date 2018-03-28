import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    private static BookManager bookManager = new BookManager();
    private static MyListeners myListeners = new MyListeners(bookManager);


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


        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(4,2,0,2);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Файл");
        JMenuItem open = new JMenuItem("Открыть");
        JMenuItem save = new JMenuItem("Сохранить");
        JMenuItem exit = new JMenuItem("Выход");
        file.add(open);
        file.add(save);
        file.addSeparator();
        file.add(exit);
        menuBar.add(file);
        container.add(menuBar,constraints);
        if(bookManager.getRowCount()!=0) {
            save.setEnabled(true);
        } else save.setEnabled(false);

        constraints.gridwidth =1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        buttonLook = new JButton("Все книги");
        container.add (buttonLook, constraints);

        buttonNew = new JButton("Новая запись");
        constraints.gridx = 1;
        container.add (buttonNew, constraints);

        buttonSearch = new JButton("Поиск по атрибуту в таблице");
        constraints.gridx = 2;
        container.add (buttonSearch, constraints);

        buttonChange = new JButton("Изменение атрибута записи");
        constraints.gridx = 3;
        container.add (buttonChange, constraints);

        buttonDelete = new JButton("Удаление записи");
        constraints.gridx = 4;
        container.add (buttonDelete, constraints);

        label = new JLabel("Поле для ввода значений атрибутов:         ");
        constraints.gridy = 2;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        container.add(label, constraints);

        textField = new JTextField();
        constraints.gridx = 2;
        constraints.gridwidth = 2;
        container.add(textField, constraints);
        textField.setEnabled(false);

        buttonCancel = new JButton("Отмена");
        constraints.gridx = 4;
        container.add(buttonCancel,constraints);
        buttonCancel.setEnabled(false);

        myListeners.listenerButtonNew(textField,label,buttonLook,buttonNew,buttonSearch,buttonChange,buttonDelete,buttonCancel);
        myListeners.listenerButtonSearch(frame,buttonSearch);
        myListeners.listenerButtonChange(frame,buttonChange);
        myListeners.listenerButtonDelete(frame,save,buttonDelete, buttonSearch,buttonChange,buttonDelete);
        myListeners.listenerButtonCancel(textField,label,buttonLook,buttonNew,buttonSearch,buttonChange,buttonDelete,buttonCancel);
        myListeners.listenerTextField(textField,label,buttonLook,buttonNew,buttonSearch,buttonChange,buttonDelete,buttonCancel);
        myListeners.listenerAllBooks(save,buttonLook,buttonSearch,buttonChange,buttonDelete);
        myListeners.listenerOpen(open,save,buttonSearch,buttonChange,buttonDelete);
        myListeners.listenerSave(save);
        myListeners.listenerExit(exit);
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
