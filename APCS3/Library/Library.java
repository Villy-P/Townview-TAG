import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    public static ArrayList<Book> books = new ArrayList<>();
    public static int id = 1;

    public static void populateBooks() {
        books.add(new Book("The Hunger Games", 3));
        books.add(new Book("Lord of the Flies", 1));
        books.add(new Book("To Kill a Mockingbird", 4));
        books.add(new Book("Pride and Prejudice", 5));
        books.add(new Book("Dracula", 2));
        books.add(new Book("The Book Thief", 6));
        books.add(new Book("Gone with the Wind", 3));
        books.add(new Book("1984", 1));
        books.add(new Book("Animal Farm", 7));
        books.add(new Book("Jane Eyre", 4));
        books.add(new Book("The Da Vinci Code", 6));
        books.add(new Book("The Giving Tree", 3));
        books.add(new Book("Les Miserables", 1));
        books.add(new Book("Crime and Punishment", 7));
        books.add(new Book("The Little Prince", 5));
        books.add(new Book("Romeo and Juliet", 2));
    }

    public static void main(String[] args) {
        populateBooks();
        while (true) {
            System.out.println("\n\nWelcome to Library Operations Systems.");
            System.out.println("1: Add a Book");
            System.out.println("2: Checkout a book");
            System.out.println("3: View Inventory");
            System.out.println("4: View Checked Out Books");
            System.out.println("5: Add a returned book");
            System.out.println("6: Sort Books");
            Scanner s = new Scanner(System.in);
            int input = s.nextInt();
            if (input == 1)
                addNewBook();
            if (input == 2)
                checkoutBook(getBookByID());
            if (input == 3)
                viewInventory();
            if (input == 4)
                viewCheckedout();
            if (input == 5)
                addReturned(getBookByID());
            if (input == 6)
                sortBook(getBookSortType());
            s.close();
        }
    }

    public static int getBookSortType() {
        System.out.println("What do you want to sort the books by?");
        System.out.println("1: Aisle");
        System.out.println("2: Book name");
        Scanner s = new Scanner(System.in);
        int id = s.nextInt();
        s.close();
        return id;
    }

    public static boolean validateInput(int min, int max, int num) {
        if (num < min || num > max) {
            System.out.println("Invalid Input: Must between " + min + " and " + max);
            return false;
        }
        return true;
    }

    public static void sortBook(int type) {
        if (!validateInput(1, 2, type))
            return;
        for (int i = 0; i < books.size() - 1; i++)   {  
            int index = i;  
            for (int j = i + 1; j < books.size(); j++) {
                boolean less;
                if (type == 1)
                    less = books.get(j).aisle < books.get(index).aisle;
                else
                    less = books.get(j).name.compareTo(books.get(index).name) < 0;
                if (less)
                    index = j;
            }
            Book temp = books.get(index);   
            books.set(index, books.get(i));  
            books.set(i, temp);
        }  
        System.out.println("Succesfully sorted inventory");
    }

    public static void addReturned(Book book) {
        if (book == null)
            return;
        if (!book.checkedOut) {
            System.out.println("That book is still in the library.");
            return;
        }
        book.checkedOut = false;
        System.out.println("Book succesfully returned.");
    }

    public static void viewCheckedout() {
        System.out.println("Checked Out:");
        for (int i = 0; i < books.size(); i++)
            if (books.get(i).checkedOut)
                System.out.println("(" + books.get(i).id + ") " + books.get(i).name + " --- Checked out by user with ID" + books.get(i).checkoutID);
    }

    public static void viewInventory() {
        System.out.println("Library Inventory:");
        for (int i = 0; i < books.size(); i++)
            if (!books.get(i).checkedOut)
                System.out.println("(" + books.get(i).id + ") " + books.get(i).name + " --- Aisle " + books.get(i).aisle);
    }

    public static void addNewBook() {
        System.out.println("What is the name of the Book you are adding?");
        Scanner s = new Scanner(System.in);
        String name = s.nextLine();
        int aisle = (int)(Math.random() * 7);
        Book book = new Book(name, aisle);
        books.add(book);
        System.out.println("Book " + name + " (" + book.id + ") placed in aisle " + aisle);
        s.close();
    }

    public static int promptID() {
        System.out.println("What is the id of the Book you are trying to get?");
        Scanner s = new Scanner(System.in);
        int id = s.nextInt();
        s.close();
        return id;
    }

    public static Book getBookByID() {
        int ID = promptID();
        for (int i = 0; i < books.size(); i++)
            if (books.get(i).id == ID)
                return books.get(i);
        System.out.println("That book wasn't found.");
        return null;
    }

    public static void checkoutBook(Book book) {
        if (book == null)
            return;
        System.out.println("Checking out book " + book.name);
        System.out.println("What is the code on the Libary ID card of the person checking this book out?");
        Scanner s = new Scanner(System.in);
        int id = s.nextInt();
        book.checkedOut = true;
        book.checkoutID = id;
        System.out.println("Book Checked out");
        s.close();
    }
}

class Book {
    public String name;
    public int aisle;
    public int id;
    public boolean checkedOut = false;
    public int checkoutID = -1;

    public Book(String name, int aisle) {
        this.name = name;
        this.aisle = aisle;
        this.id = Library.id;
        Library.id++;
    }
}