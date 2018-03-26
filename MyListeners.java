import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class MyListeners {
    private int check = 0;
    private String[] str = {"     ","Название", "Автор", "Дата издания", "Издатель", "Количество страниц", "Количество книг"};
    private BookManager bookManager;

    public MyListeners(BookManager bookManager){
        this.bookManager = bookManager;
    }

    private void increase (){
        check++;
    }

    private void setZero (){
        check = 0;
    }

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

    public void listenerFrame (JFrame frame, JButton ... buttons) {
        frame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                if(bookManager.getRowCount() == 0){
                    buttons[0].setEnabled(false);
                    buttons[1].setEnabled(false);
                    buttons[2].setEnabled(false);
                }else{
                    buttons[0].setEnabled(true);
                    buttons[1].setEnabled(true);
                    buttons[2].setEnabled(true);
                }
            }
            @Override
            public void windowLostFocus(WindowEvent e) {
                windowGainedFocus(e);
            }
        });
    }


    public void listenerTextField (JTextField textField, JLabel label, JButton ... buttons) {
        textField.addActionListener(new ActionListener() {
            Book book;

            private void bookCreate(){
                book = new Book();
            }

            private void setName () {
                String text = textField.getText();
                book.setName(text);
                textField.setText("");
                increase();
                label.setText(str[2] + " (цифры не допускаются):");
            }

            private void setAuthor(){
                String text = textField.getText();
                char [] textChar = text.toCharArray();
                boolean next = true;
                try {
                    for (int i = 0; i < textChar.length; i++) {
                        if (textChar[i] >= '0' && textChar[i] <= '9') {
                            throw new NumberFormatException();
                        }
                    }
                }
                catch (NumberFormatException exc){
                    label.setText("Неверный формат, " + str[2] + ":");
                    textField.setText("");
                    next = false;
                }
                if (next) {
                    book.setAuthor(text);
                    textField.setText("");
                    increase();
                    label.setText(str[3] + " ( от 1500 до 2018 гг.):");
                }
            }

            private void setTime() {
                String text = textField.getText();
                int time = 0;
                boolean next = true;
                try {
                    time = Integer.parseInt(text);
                    if (time < 1500 || time > 2018){
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException exc) {
                    label.setText("Неверный формат, " + str[3] + ":");
                    textField.setText("");
                    next = false;
                }
                if (next) {
                    book.setTime(time);
                    textField.setText("");
                    increase();
                    label.setText(str[4] + ":");
                }
            }

            private void setPublisher(){
                String text = textField.getText();
                book.setPublisher(text);
                textField.setText("");
                label.setText(str[5] + " от 10 до 23675 стр.:");
                increase();
            }

            private void setPages(){
                String text = textField.getText();
                int pages = 0;
                boolean next = true;
                try {
                    pages = Integer.parseInt(text);
                    if (pages < 10 || pages > 23675){
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException exc) {
                    label.setText("Неверный формат, "+ str[5] + ":");
                    textField.setText("");
                    next = false;
                }
                if(next){
                    book.setPages(pages);
                    textField.setText("");
                    increase();
                    label.setText(str[6] + " от 1 до 10 шт.:");
                }
            }

            private void setCount(){
                String text = textField.getText();
                int count = 0;
                boolean next = true;
                try {
                    count = Integer.parseInt(text);
                    if (count < 1 || count > 10){
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException exc) {
                    label.setText("Неверный формат, " + str[6] + ":");
                    textField.setText("");
                    next = false;
                }
                if(next) {
                    book.setCount(count);
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
}
