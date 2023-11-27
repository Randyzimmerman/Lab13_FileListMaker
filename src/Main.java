import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<String> itemList = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static String currentListFileName = "defaultList.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("A - Add an item");
            System.out.println("D - Delete an item");
            System.out.println("V - View the list");
            System.out.println("O - Open a list file");
            System.out.println("S - Save the list to a file");
            System.out.println("C - Clear the list");
            System.out.println("Q - Quit");

            String choice = scanner.next().toUpperCase();

            switch (choice) {
                case "A":
                    addItem(scanner);
                    break;
                case "D":
                    deleteItem(scanner);
                    break;
                case "V":
                    viewList();
                    break;
                case "O":
                    openList(scanner);
                    break;
                case "S":
                    saveList();
                    break;
                case "C":
                    clearList();
                    break;
                case "Q":
                    quitProgram(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void addItem(Scanner scanner) {
        System.out.println("Enter the item to add:");
        String newItem = scanner.next();
        itemList.add(newItem);
        needsToBeSaved = true;
    }

    private static void deleteItem(Scanner scanner) {
        if (!itemList.isEmpty()) {
            viewList();
            System.out.println("Enter the index of the item to delete:");
            int index = scanner.nextInt();

            if (index >= 0 && index < itemList.size()) {
                itemList.remove(index);
                needsToBeSaved = true;
            } else {
                System.out.println("Invalid index. Please try again.");
            }
        } else {
            System.out.println("The list is empty. Nothing to delete.");
        }
    }

    private static void viewList() {
        if (!itemList.isEmpty()) {
            System.out.println("List:");
            for (int i = 0; i < itemList.size(); i++) {
                System.out.println(i + ": " + itemList.get(i));
            }
        } else {
            System.out.println("The list is empty.");
        }
    }

    private static void openList(Scanner scanner) {
        System.out.println("Enter the filename to open:");
        String fileName = scanner.next();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            itemList.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                itemList.add(line);
            }
            currentListFileName = fileName;
            needsToBeSaved = false;
            System.out.println("List loaded from " + fileName);
        } catch (IOException e) {
            System.out.println("Error opening the file: " + e.getMessage());
        }
    }

    private static void saveList() {
        if (needsToBeSaved) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentListFileName))) {
                for (String item : itemList) {
                    writer.write(item);
                    writer.newLine();
                }
                needsToBeSaved = false;
                System.out.println("List saved to " + currentListFileName);
            } catch (IOException e) {
                System.out.println("Error saving the file: " + e.getMessage());
            }
        } else {
            System.out.println("No changes to save.");
        }
    }

    private static void clearList() {
        if (!itemList.isEmpty()) {
            itemList.clear();
            needsToBeSaved = true;
            System.out.println("List cleared.");
        } else {
            System.out.println("The list is already empty.");
        }
    }

    private static void quitProgram(Scanner scanner) {
        if (needsToBeSaved) {
            System.out.println("Save changes before quitting? (Y/N)");
            String saveChoice = scanner.next().toUpperCase();
            if (saveChoice.equals("Y")) {
                saveList();
            }
        }
        System.out.println("Exiting program. Goodbye!");
        System.exit(0);
    }
}
