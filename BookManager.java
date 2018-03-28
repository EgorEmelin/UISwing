import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.*;

public class BookManager extends AbstractTableModel {
    private List<Book> books = new ArrayList<>();
    private List<Book> temp = new ArrayList<>();

    public BookManager() {
    }

    public void addBook(Book b){
        books.add(b);
        fireTableDataChanged();
    }

    public void removeBook(int index){
        books.remove(index);
        fireTableDataChanged();
    }

    public void changeBook(int index, int attribute, int valueInt, String valueStr)
    {
        switch (attribute){
            case 0:
                books.get(index).setName(valueStr);
                break;
            case 1:
                books.get(index).setAuthor(valueStr);
                break;
            case 2:
                books.get(index).setTime(valueInt);
                break;
            case 3:
                books.get(index).setPublisher(valueStr);
                break;
            case 4:
                books.get(index).setPages(valueInt);
                break;
            case 5:
                books.get(index).setCount(valueInt);
                break;
        }
        fireTableDataChanged();
    }


    public void searchBook(int attribute, int valueInt, String valueStr){
        for (int i = 0; i < books.size();i++){
            temp.add(books.get(i));
        }
        books.clear();
        switch (attribute){
            case 0:
                for(Book x: temp) {
                    if(x.getName().equalsIgnoreCase(valueStr))
                        books.add(x);
                }
                break;
            case 1:
                for(Book x: temp) {
                    if(x.getAuthor().equalsIgnoreCase(valueStr))
                        books.add(x);
                }
                break;
            case 2:
                for(Book x: temp) {
                    if((x.getTime() + " г.").equalsIgnoreCase(valueInt+ " г."))
                        books.add(x);
                }
                break;
            case 3:
                for(Book x: temp) {
                    if(x.getPublisher().equalsIgnoreCase(valueStr))
                        books.add(x);
                }
                break;
            case 4:
                for(Book x: temp) {
                    if(x.getPages() == valueInt)
                        books.add(x);
                }
                break;
            case 5:
                for(Book x: temp) {
                    if(x.getCount() == valueInt)
                        books.add(x);
                }
                break;
        }
        fireTableDataChanged();
    }

    public void showBooks (){
        if (temp.size()!=0) {
            books.clear();
            for (int i = 0; i < temp.size(); i++) {
                books.add(temp.get(i));
            }
            temp.clear();
        }
        fireTableDataChanged();
    }

    public void saveBooks(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            for (File myFile : new File("jsons" + System.getProperty("file.separator")).listFiles())
                if (myFile.isFile()) myFile.delete();
        }
        catch (NullPointerException e)
        {
            System.out.println("Ошибка удаления");
        }
        for (int i = 0; i<books.size(); i++) {
            String relativePath = "jsons" + System.getProperty("file.separator") + "book" + i + ".json";
            File file = new File(relativePath);
            try {
                mapper.writeValue( new FileOutputStream(relativePath),books.get(i));
            }
            catch (IOException e)
            {
                System.out.println("Ошибка записи");
            }
        }
    }

    public void openBooks(){
        ObjectMapper mapper = new ObjectMapper();
        books.clear();
        for (File myFile : new File("jsons" + System.getProperty("file.separator")).listFiles()){
            String relativePath = myFile.getAbsolutePath();
            try {
                books.add(mapper.readValue(new FileInputStream(relativePath),Book.class)) ;
            }
            catch (IOException e)
            {
                System.out.println("Ошибка записи");
            }
        }
        fireTableDataChanged();
    }

    public int getRowCount() {
        fireTableDataChanged();
        return books.size();
    }

    public int getColumnCount() {
        return 7;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Book cur=books.get(rowIndex);
        switch (columnIndex){
            case 0:
                return cur.getName();
            case 1:
                return cur.getAuthor();
            case 2:
                return cur.getTime() + " г.";
            case 3:
                return cur.getPublisher();
            case 4:
                return cur.getPages();
            case 5:
                return cur.getCount();
            case 6:
                return rowIndex;
        }
        return null;
    }

    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Название";
            case 1:
                return "Автор";
            case 2:
                return "Дата издания";
            case 3:
                return "Издатель";
            case 4:
                return "Количество страниц";
            case 5:
                return "Книг в наличии";
            case 6:
                return "ID";
        }
        return "";
    }

    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return Integer.class;
            case 3:
                return String.class;
            case 4:
                return Integer.class;
            case 5:
                return Integer.class;
            case 6:
                return Integer.class;
        }
        return Object.class;
    }
}
