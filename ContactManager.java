import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ContactManager {

    private static final String CONTACT_FILE = "contacts.txt";
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Contact> contacts = new ArrayList<Contact>();

    // Add a contact
    public void addContact() {
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        System.out.println("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.println("Enter email: ");
        String email = scanner.nextLine();
        Contact contact = new Contact(name, phoneNumber, email);
        contacts.add(contact);
        System.out.println("Contact added: " + contact);
    }

    // Search for a contact
    public void searchContact() {
        System.out.println("Enter a name for search: ");
        String name = scanner.nextLine();
        System.out.println("Searching for contact: " + name);
        Contact contact = findContactByName(name);
        if (contact != null) {
            System.out.println("Contact found: " + contact);
        } else {
            System.out.println("Contact not found.");
        }
    }

    // Edit a contact
    public void editContact() {
        System.out.print("Enter contact name to edit: ");
        String nameToEdit = scanner.nextLine();
        Contact contact = findContactByName(nameToEdit);

        if (contact == null) {
            System.out.println("Contact not found.");
            return;
        }
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter new email: ");
        String email = scanner.nextLine();

        contact.setName(name);
        contact.setPhoneNumber(phoneNumber);
        contact.setEmail(email);
        System.out.println("Contact updated successfully!");
    }

    // Remove a contact
    public void removeContact() {
        System.out.print("Enter contact name to delete: ");
        String nameToDelete = scanner.nextLine();
        Contact contact = findContactByName(nameToDelete);
        if(contact != null) {
            contacts.remove(contact);
            System.out.println("Contact deleted successfully!");
        } else {
            System.out.println("Contact not found.");
        }
    }

    // Save contacts to a file
    public void saveContacts() {
        try {
            FileWriter writer = new FileWriter(CONTACT_FILE);
            for (Contact contact : contacts) {
                writer.write(contact.toString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    // Load contacts from a file
    public void loadContacts() {            
        contacts = new ArrayList<Contact>();
        try(BufferedReader reader = new BufferedReader(new FileReader(CONTACT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");
                String name = parts[0];
                String phoneNumber = parts[1];
                String email = parts[2];
                Contact contact = new Contact(name, phoneNumber, email);
                contacts.add(contact);
            }            
        } catch (IOException e) {
            System.out.println("Error loading contacts: " + e.getMessage());
        }   
    }

    // Find a contact by name
    private Contact findContactByName(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    // Switch case for menu
    public void menu() {
        Integer choice = 0;
        do{
            System.out.println("************");
            System.out.println("1. Add contact");
            System.out.println("2. Search contact");
            System.out.println("3. Edit contact");
            System.out.println("4. Remove contact");
            System.out.println("5. Save contacts");
            System.out.println("6. Load contacts");
            System.out.println("7. Exit");
            System.out.println("************");
            System.out.print("Enter your choice: ");
    
            choice = getValidChoice();

            switch (choice){
                case 1 -> addContact();
                case 2 -> searchContact();
                case 3 -> editContact();
                case 4 -> removeContact();
                case 5 -> saveContacts();
                case 6 -> loadContacts();
                case 7 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice");
            }
        }while(choice != 7);
    }

    private int getValidChoice() {
        int[] validChoice = {1, 2, 3, 4, 5, 6, 7};

        while (true) {
            System.out.println("Enter your choice (1-7): ");
            try{
                if (scanner.hasNextInt()) {
                    int input = scanner.nextInt();
                    scanner.nextLine();
                    for (int i : validChoice) {
                        if (input == i) {
                            return input;
                        }
                    }
                    System.out.println("Invalid choice. Please enter a valid choice (1-7).");
                } else {
                    scanner.next();
                    System.out.println("Invalid input. Please enter a valid integer.");
                }
        }catch(InputMismatchException e) {
            scanner.next();
            System.out.println("Invalid input. Please enter a valid integer.");
        }  
    }
    }

    public static void main(String[] args) {
        System.out.println("************");
        System.out.println("* Contact Manager *");
        System.out.println("************");

        ContactManager contactManager = new ContactManager();
        contactManager.loadContacts();
        contactManager.menu();
    }
}
