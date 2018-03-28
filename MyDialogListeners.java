import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.event.*;

public class MyDialogListeners {
    private String[] str = {"     ", "Название", "Автор", "Дата издания", "Издатель", "Количество страниц", "Количество книг"};
    private int index = 0, attribute = -1, valueInt = -1;
    private BookManager bookManager;
    private String valueStr = "";
    private boolean check = false;

    public MyDialogListeners(BookManager bookManager) {
        this.bookManager = bookManager;
    }
    // Обработчик для текстового поля, принимающего ID
    public void textIDListener(JTextField textID, JComboBox comboBox) {
        int choice = MyDialog.getChoice();
        textID.addCaretListener(new CaretListener() {
            private void setIndex() {
                try {
                    index = Integer.parseInt(textID.getText());
                    if (index < 0 || index > (bookManager.getRowCount() - 1)) {
                        throw new NumberFormatException();
                    }
                    check = true;
                } catch (NumberFormatException | IllegalStateException exc) {
                    check = false;
                }

                if (choice == 2 && check) {
                    comboBox.setEnabled(true);
                } else {
                    comboBox.setEnabled(false);
                    attribute = -1;
                }
            }

            @Override
            public void caretUpdate(CaretEvent e) {
                if (choice != 1) {
                    setIndex();
                }
            }
        });
    }
    // Обработчик для меня выбора атрибута
    public void comboboxListener(JComboBox comboBox, JTextField textValue, JLabel labelTextValue) {
        int choice = MyDialog.getChoice();
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    int temp = comboBox.getSelectedIndex();
                    if (choice == 2) {
                        if (check && temp != 0) {
                            textValue.setEnabled(true);
                        } else {
                            textValue.setEnabled(false);
                            textValue.setText("");
                        }
                    } else if (choice == 1) {
                        if (temp != 0) {
                            textValue.setEnabled(true);
                        } else {
                            textValue.setEnabled(false);
                            textValue.setText("");
                        }
                    }
                    if (temp > 0) {
                        attribute = temp - 1;
                        labelTextValue.setText("Введите значение атрибута " + str[attribute + 1] + ":");
                    } else {
                        labelTextValue.setText("Введите значение атрибута: ");
                    }
                    textValue.setText("");

                }
            }
        });
    }
    // Обработчик для текстового поля, принимающего значения атрибутов
    public void textValueKeyListener(JTextField textValue, JLabel labelTextValue) {
        int choice = MyDialog.getChoice();
        textValue.addKeyListener(new KeyListener() {
            private void setName() {
                String text = textValue.getText();
                validationString(text, labelTextValue, attribute);
            }

            private void setAuthor() {
                String text = textValue.getText();
                validationString(text, labelTextValue, attribute);
            }

            private void setTime() {
                String text = textValue.getText();
                validationInt(text, labelTextValue, 1500, 2018, attribute);
            }

            private void setPublisher() {
                String text = textValue.getText();
                validationString(text, labelTextValue, attribute);
            }

            private void setPages() {
                String text = textValue.getText();
                validationInt(text, labelTextValue, 10, 23675, attribute);
            }

            private void setCount() {
                String text = textValue.getText();
                validationInt(text, labelTextValue, 1, 10, attribute);
            }

            @Override
            public void keyTyped(KeyEvent e) {
                switch (attribute) {
                    case 0:

                        setName();
                        break;
                    case 1:

                        setAuthor();
                        break;
                    case 2:

                        setTime();
                        break;
                    case 3:

                        setPublisher();
                        break;
                    case 4:

                        setPages();
                        break;
                    case 5:

                        setCount();
                        break;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                keyTyped(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keyTyped(e);
            }
        });
    }
    // Обработчик для кнопки Ок, которая закрывает окно и исполняет действие в соответсвии с полученными данными
    public void okListener(JDialog dialog, JButton buttonOk) {
        int choice = MyDialog.getChoice();
        buttonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (choice == 3 && check) {
                    bookManager.removeBook(index);
                    check = false;
                }
                if (choice == 2 && check && attribute != -1 && (!valueStr.equals("") || valueInt != -1)) {
                    bookManager.changeBook(index, attribute, valueInt, valueStr);
                    check = false;
                }
                if (choice == 1 && attribute != -1 && (!valueStr.equals("") || valueInt != -1)) {
                    bookManager.searchBook(attribute, valueInt, valueStr);
                }
                dialog.dispose();
            }
        });
    }
    // Обработчик для лейбла, содержащий инструкцию к окну.
    public void dialogListener(JDialog dialog, JLabel label) {
        int choice = MyDialog.getChoice();
        dialog.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                if (choice == 3) {
                    label.setText("Укажите id строчки для удаления и нажмите Ок.");
                    dialog.pack();
                } else if (choice == 2) {
                    label.setText("Укажите id сторчки, а затем в следующем окне атрибут для изменения. Введите значение и нажмите Ок.");
                    dialog.pack();
                } else {
                    label.setText("Выберите атрибут по которому ведется поиск и введите значение. Нажмите Ок.                        ");
                    dialog.pack();
                }
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                windowGainedFocus(e);
            }
        });
    }
    // Вспомогательные классы для валидации
    private void validationString(String text, JLabel label, int attr) {
        char[] textChar = text.toCharArray();
        try {
            for (int i = 0; i < textChar.length; i++) {
                if (textChar[i] != ' ' && (textChar[i] > 'Z' || textChar[i] < 'A') &&
                        (textChar[i] > 'z' || textChar[i] < 'a') &&
                        (textChar[i] > 'я' || textChar[i] < 'А')) {
                    throw new NumberFormatException();
                }
            }
            label.setText("Введите значение атрибута " + str[attr + 1] + ":");
            valueStr = text;
        } catch (NumberFormatException exc) {
            label.setText("Неверный формат, только буквы: ");
            valueStr = "";
        }

    }

    private void validationInt(String text, JLabel label, int begin, int end, int attr) {
        int count = -1;
        try {
            if (!text.equals("")) {
                count = Integer.parseInt(text);
            }
            if (count < begin || count > end) {
                throw new NumberFormatException();
            }
            label.setText("Введите значение атрибута " + str[attr + 1] + ":");
            valueInt = count;
        } catch (NumberFormatException exc) {
            label.setText("Неверный ввод, (" + begin + "-" + end + "): ");
            valueInt = -1;
        }

    }
}
