import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class BookManager extends AbstractTableModel {
    private List<Book> books = new ArrayList<>();

    public BookManager() {
        books.add(new Book("Назв12daf2ание","Автор", 132, "fdsf", 33,44));
        books.add(new Book("Назва23dafние","Автор", 132, "fdsf", 33,44));
        books.add(new Book("Назв123aadfание","Автор", 132, "fdsf", 33,44));
        books.add(new Book("Назвsfsdание","Автор", 132, "fdsf", 33,44));
        books.add(new Book("Назваasdgfние","Автор", 132, "fdsf", 33,44));
        books.add(new Book("Назваsdfgние","Автор", 132, "fdsf", 33,44));
    }

    public void addBook(Book b){
        books.add(b);
        fireTableDataChanged();
    }

    public void removeBook(int index){
        books.remove(index);
        fireTableDataChanged();
    }

    public int getRowCount() {
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
                return cur.getTime();
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
                return String.class;
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
