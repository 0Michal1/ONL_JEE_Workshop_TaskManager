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

   public static void main(String[] args){
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
                case "add" -> addTask();
                case "remove" -> {
                    removeTask(tasks, getTheNumber());
                    System.out.println("Value was successfully deleted.");
                }
                case "list" -> printTab(tasks);
                case "exit" -> {
                    saveTasksToFile(fileName, tasks);
                    System.out.println(ConsoleColors.RED + "Good Bye, see you next time");
                    System.exit(0);
                }
                default -> System.out.println("Please select a correct option.");
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

    public static void showWelcome () {
            String userName = System.getProperty("user.name");
            System.out.println(ConsoleColors.RED + "Welcome " + userName + ConsoleColors.RESET);
        }
        public static void printTab (String[][]tasks){
            for (String[] task : tasks) {
                for (String s : task) {
                    System.out.println(s + " ");
                }
                System.out.println();
            }
        }

        private static void removeTask (String[][] tasks, int theNumber){
            try {
                if (theNumber < tasks.length) {
                    if (tasks.length - 1 - theNumber >= 0) {
                        System.arraycopy(tasks, theNumber + 1, tasks, theNumber, tasks.length - 1 - theNumber);
                        tasks = Arrays.copyOf(tasks, tasks.length - 1);
                    }
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Element not exist in tab");
            }
        }

        public static void saveTasksToFile(String fileName, String[][]tasks){
            Path dir = Paths.get("/home/michal/ONL_JEE_Workshop_TaskManager/src/main/java/pl/coderslab/workshop/taskmanager/tasks.csv");

            String[] lines = new String[tasks.length];
            for (int i = 0; i < tasks.length; i++) {
                lines[i] = String.join(",", tasks[i]);
            }

            try {
                Files.write(dir, Arrays.asList(lines));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
            String isImportant;
            do {
                System.out.println("Is your task important: true/false");
                isImportant = scanner.nextLine();
            }while (!(isImportant.equals("true") || isImportant.equals("false"))) ;
                tasks = Arrays.copyOf(tasks, tasks.length + 1);
                tasks[tasks.length - 1] = new String[3];
                tasks[tasks.length - 1][0] = description;
                tasks[tasks.length - 1][1] = dueDate;
                tasks[tasks.length - 1][2] = isImportant;
                return tasks;
        }

            private static String[][] loadFile (String filename){
                Path path = Paths.get("/home/michal/ONL_JEE_Workshop_TaskManager/src/main/java/pl/coderslab/workshop/taskmanager/tasks.csv");
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
                        System.arraycopy(split, 0, tab[i], 0, split.length);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return tab;
            }
}