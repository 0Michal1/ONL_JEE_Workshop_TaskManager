package pl.coderslab.workshop.taskmanager;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import pl.coderslab.ConsoleColors;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static final String fileName = "tasks.csv";
    static String[][] tasks;

    public static void Main(String[] args) {
        run();
    }

    public static void run() {
        showWelcome();
        Scanner scanner = new Scanner(System.in);
        tasks = loadFile(fileName);
        do {
            showMenu();
            String input = scanner.nextLine();
            switch (input) {
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask(tasks, getTheNumber());
                    System.out.println("Value was successfully deleted.");
                    break;
                case "list":
                    printTab(tasks);
                    break;
                case "exit":
                    saveTasksToFile(fileName, tasks);
                    System.out.println(ConsoleColors.RED + "Good Bye, see you next time");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
        } while (true);
    }

    public static void showMenu() {
        String[] options = {"add", "remove", "list", "exit" };
        System.out.println(ConsoleColors.BLUE + "Please select an option: " + ConsoleColors.RESET);
        for (String o : options) {
            System.out.println(o);
        }
    }

    private static void showWelcome () {
            String userName = System.getProperty("user.name");
            System.out.println(ConsoleColors.RED + "Welcome " + userName + ConsoleColors.RESET);
        }

        private static void printTab (String[][]tasks){
            for (int i = 0; i < tasks.length; i++) {
                for (int j = 0; j < tasks[i].length ; j++) {
                    System.out.println(tasks[i][j]+" ");
                }
                System.out.println();
            }
        }

        private static void removeTask (String[][]tasks, int theNumber){
            try {
                if (theNumber < tasks.length) {
                    tasks = ArrayUtils.remove(tasks, theNumber);
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Element not exist in tab");
            }


        }

        private static void saveTasksToFile(String fileName, String[][]tasks){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please add task description");
            String description = scanner.nextLine();
            System.out.println("Please add task due date");
            String dueDate = scanner.nextLine();
            System.out.println("Is your task important: true/false");
            String isImportant = scanner.nextLine();
            tasks = Arrays.copyOf(tasks, tasks.length + 1);
            tasks[tasks.length - 1] = new String[3];
            tasks[tasks.length - 1][0] = description;
            tasks[tasks.length - 1][1] = dueDate;
            tasks[tasks.length - 1][2] = isImportant;

        }

        private static int getTheNumber () {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please select task's number to remove.");
            String number = scanner.nextLine();
            while (!isNumberGreaterEqualZero(number)) {
                System.out.println("Incorrect argument passed. Please give number greater or equal 0");
                scanner.nextLine();
            }
            return Integer.parseInt(number);
        }

    private static boolean isNumberGreaterEqualZero(String number) {
        if (NumberUtils.isParsable(number)){
            return Integer.parseInt(number) >= 0;
        }return false;
    }

    private static String[][] addTask () {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please add task description");
            String description = scanner.nextLine();
            System.out.println("Please add task due date");
            String dueDate = scanner.nextLine();
            String isImportant = "true";
            do {
                System.out.println("Is your task important: true/false");
                isImportant = scanner.nextLine();
            }while (!(scanner.nextLine().equals("true") || scanner.nextLine().equals("false"))) ;
                tasks = Arrays.copyOf(tasks, tasks.length + 1);
                tasks[tasks.length - 1] = new String[3];
                tasks[tasks.length - 1][0] = description;
                tasks[tasks.length - 1][1] = dueDate;
                tasks[tasks.length - 1][2] = isImportant;
                return tasks;
        }

            private static String[][] loadFile (String filename){
                Path path = Paths.get(fileName);
                String[][] tab = null;
                if (!Files.exists(path)) {
                    System.out.println("File not exist.");
                    System.exit(0);
                }
                try {
                    List<String> strings = Files.readAllLines(path);
                    tab = new String[strings.size()][strings.get(0).split(",").length];

                    for (int i = 0; i < strings.size(); i++) {
                        String[] split = strings.get(i).split(",");
                        for (int j = 0; j < split.length; j++) {
                            tab[i][j] = split[j];
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return tab;
            }
}