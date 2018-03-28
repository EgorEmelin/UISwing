import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class MyListeners {
    private int check = 0;
    private String[] str = {"     ","Название", "Автор", "Дата издания", "Издатель", "Количество страниц", "Количество книг"};
    private BookManager bookManager;
    private MyDialog myDialog ;

    public MyListeners(BookManager bookManager){
        this.bookManager = bookManager;
        myDialog = new MyDialog(bookManager);
    }
    // Обработчик событий для кнопки Все книги
    public void listenerAllBooks (JMenuItem save, JButton ... button){
        button[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookManager.showBooks();
                if (bookManager.getRowCount()!=0){
                    save.setEnabled(true);
                    button[1].setEnabled(true);
                    button[2].setEnabled(true);
                    button[3].setEnabled(true);
                }
            }
        });
    }
    // Обработчик событий для кнопки Новая запись
    public void listenerButtonNew(JTextField textField, JLabel label, JButton ... buttons){
        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttons[0].setEnabled(false);
                buttons[1].setEnabled(false);
                buttons[2].setEnabled(false);
                buttons[3].setEnabled(false);
                buttons[4].setEnabled(false);
                textField.setEnabled(true);
                buttons[5].setEnabled(true);
                label.setText(str[1] + ":");
            }
        });
    }
    // Обработчик событий для кнопки Отмена, работающая при новой записи
    public void listenerButtonCancel (JTextField textField, JLabel label, JButton ... buttons) {
        buttons[5].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setZero();
                label.setText("Поле для ввода значений атрибутов: ");
                textField.setText("");
                buttons[0].setEnabled(true);
                buttons[1].setEnabled(true);
                buttons[2].setEnabled(true);
                buttons[3].setEnabled(true);
                buttons[4].setEnabled(true);
                textField.setEnabled(false);
                buttons[5].setEnabled(false);
            }
        });
    }
    // Обработчик событий для текстового поля, при введении значений атрибутов для новой записи
    public void listenerTextField (JTextField textField, JLabel label, JButton ... buttons) {
        textField.addActionListener(new ActionListener() {
            Book book;

            private void bookCreate(){
                book = new Book();
            }

            private void setName () {
                String text = textField.getText();
                if (validationString(text, label, textField)) {
                    book.setName(text);
                    textField.setText("");
                    increase();
                    label.setText(str[2] + " (только рус./англ. буквы и пробел):");
                }

            }

            private void setAuthor(){
                String text = textField.getText();
                if (validationString(text, label, textField)) {
                    book.setAuthor(text);
                    textField.setText("");
                    increase();
                    label.setText(str[3] + " ( от 1500 до 2018 гг.):");
                }
            }

            private void setTime() {
                String text = textField.getText();
                if (validationInt(text, label, textField,1500,2018)) {
                    book.setTime(Integer.parseInt(text));
                    textField.setText("");
                    increase();
                    label.setText(str[4] + ":");
                }
            }

            private void setPublisher(){
                String text = textField.getText();
                if (validationString(text, label, textField)) {
                    book.setPublisher(text);
                    textField.setText("");
                    label.setText(str[5] + " от 10 до 23675 стр.:");
                    increase();
                }
            }

            private void setPages(){
                String text = textField.getText();
                if(validationInt(text, label, textField,10,23675)){
                    book.setPages(Integer.parseInt(text));
                    textField.setText("");
                    increase();
                    label.setText(str[6] + " от 1 до 10 шт.:");
                }
            }

            private void setCount(){
                String text = textField.getText();
                if(validationInt(text, label, textField,1,10)) {
                    book.setCount(Integer.parseInt(text));
                    textField.setText("");
                    setZero();
                    label.setText("Поле для ввода значений атрибутов: ");
                    bookManager.addBook(book);
                    buttons[0].setEnabled(true);
                    buttons[1].setEnabled(true);
                    buttons[2].setEnabled(true);
                    buttons[3].setEnabled(true);
                    buttons[4].setEnabled(true);
                    textField.setEnabled(false);
                    buttons[5].setEnabled(false);
                }
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (check ==0) {
                    bookCreate();
                    label.setText(str[1] + ":");
                    setName();
                }
                else if (check ==1){
                    setAuthor();
                }
                else if (check ==2){
                    setTime();
                }
                else if (check ==3){
                    setPublisher();
                }
                else if (check ==4){
                    setPages();
                }
                else if (check ==5){
                    setCount();
                }
            }
        });
    }

    // Обработчик событий для кнопки Поиск по атрибуту в таблице. Создает диалоговое окно
    public void listenerButtonSearch (JFrame frame, JButton buttons) {
        buttons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = myDialog.createDialog(frame,"Поиск по атрибуту в таблице",true,1);
                dialog.setVisible(true);
            }
        });
    }
    // Обработчик событий для кнопки Изменение атрибута в записи. Создает диалоговое окно
    public void listenerButtonChange (JFrame frame, JButton buttons) {
        buttons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = myDialog.createDialog(frame,"Изменение атрибута записи",true,2);
                dialog.setVisible(true);
            }
        });
    }
    // Обработчик событий для кнопки Удаления записи. Создает диалоговое окно
    public void listenerButtonDelete (JFrame frame,JMenuItem save, JButton ... buttons) {
        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = myDialog.createDialog(frame," Удаление записи",true,3);
                dialog.setVisible(true);
                if(bookManager.getRowCount()== 0){
                    save.setEnabled(false);
                    buttons[1].setEnabled(false);
                    buttons[2].setEnabled(false);
                    buttons[3].setEnabled(false);
                }else{
                    save.setEnabled(true);
                    buttons[1].setEnabled(true);
                    buttons[2].setEnabled(true);
                    buttons[3].setEnabled(true);
                }
            }
        });
    }
    //Обработчик событий для открытия таблицы
    public void listenerOpen (JMenuItem open, JMenuItem save, JButton ... button ) {
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookManager.openBooks();
                save.setEnabled(true);
                button[0].setEnabled(true);
                button[1].setEnabled(true);
                button[2].setEnabled(true);
            }
        });
    }
    //Обработчик событий для сохранения таблицы
    public void listenerSave (JMenuItem save) {
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    bookManager.saveBooks();

            }
        });
    }
    //Обработчик событий для кнопки Exit из выпадающиего меню сверху.
    public void listenerExit (JMenuItem exit) {
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    //Далее 4 впомогательных метода для валидации вводимых данных.
    private boolean validationString (String text, JLabel label, JTextField textField){
        char [] textChar = text.toCharArray();
        boolean next = true;
        try {
            for (int i = 0; i < textChar.length; i++) {
                if (textChar[i]!=' ' && (textChar[i] > 'Z' || textChar[i] < 'A') &&
                        (textChar[i] > 'z' || textChar[i] < 'a')&&
                        (textChar[i] > 'я' || textChar[i] < 'А')) {
                    throw new NumberFormatException();
                }
            }
        }
        catch (NumberFormatException exc){
            label.setText("Неверный формат, " + str[2] + ":");
            textField.setText("");
            next = false;
        }
        return next;
    }

    private boolean validationInt (String text, JLabel label, JTextField textField, int begin, int end) {
        int count;
        boolean next = true;
        try {
            count = Integer.parseInt(text);
            if (count < begin || count > end){
                throw new NumberFormatException();
            }
        } catch (NumberFormatException exc) {
            label.setText("Неверный формат, " + str[6] + ":");
            textField.setText("");
            next = false;
        }
        return next;
    }

    private void increase (){
        check++;
    }

    private void setZero (){
        check = 0;
    }
}
