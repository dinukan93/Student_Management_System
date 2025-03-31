import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.io.FileWriter;


public class Student_Management_System {
    public static void main(String[] args) {
        final int availableSeats = 100;
        Student[] students = new Student[availableSeats];
        int studentCount = 0;
        Scanner sc = new Scanner(System.in);
        printMenu(sc, studentCount, availableSeats, students);

    }

    public static void printMenu(Scanner sc, int studentCount, int availableSeats, Student[] students) {
        System.out.println();
        System.out.println("\t<<<MAIN MENU>>>");
        System.out.println("1. Check available seats");
        System.out.println("2. Register student (with ID)");
        System.out.println("3. Delete student");
        System.out.println("4. Find student (with student ID)");
        System.out.println("5. Store student details into a file");
        System.out.println("6. Load student details from the file to the system");
        System.out.println("7. View the list of students based on their names");
        System.out.println("8. Add additional details");
        System.out.println("9. Exit");

        int choice;
        while (true) {
            try {
                System.out.println();//empty line
                System.out.print("Enter choice from above list:");
                choice = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input.Please enter Integer.");
                sc.next();//Clears the invalid input."Invalid Input.Please enter Integer ".display infinitely.
            }
        }
        switch (choice) {
            case 1:
                System.out.println("\tCheck available seats");
                System.out.println("Check available seats: " + checkAvailableSeats(availableSeats, studentCount));
                displayArray(students, studentCount);
                break;

            case 2:
                System.out.println("\tRegister student (with ID)");
                studentCount = registerStudent(students, studentCount, sc, availableSeats);
                displayArray(students, studentCount);
                System.out.println();
                break;

            case 3:
                System.out.println("\tDelete student");
                studentCount = deleteStudent(students, studentCount, sc);
                displayArray(students, studentCount);
                break;

            case 4:
                System.out.println("\tFind student (with student ID)");
                findStudentIndex(sc, studentCount, students);
                break;

            case 5:
                System.out.println("\tStore student details into a file");
                storeStudentDetails(students, studentCount);
                break;
            case 6:
                System.out.println("\tLoad student details from the file to the system");
                studentCount = loadStudentDetails(studentCount, students);
                break;
            case 7:
                System.out.println("\tView the list of students based on their names");
                viewTheListOfStudents(studentCount, students);
                break;
            case 8:
                System.out.println("\tAdd additional details");
                addAdditionalDetails(sc, students, studentCount, availableSeats);
                break;
            case 9:
                System.out.println("\tExit.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid input.Please enter the 1,2,3,4,5,6,7,8 or 9");
        }
        printMenu(sc, studentCount, availableSeats, students);
    }

    public static int checkAvailableSeats(int availableSeats, int studentCount) {
        return availableSeats - studentCount;
    }

    public static int registerStudent(Student[] students, int studentCount, Scanner sc, int availableSeats) {
        String studentId;
        if (availableSeats > 0) {
            while (true) {
                try {
                    System.out.print("Enter student ID w:");
                    int id = sc.nextInt();
                    String newId = Integer.toString(id);
                    if (newId.length() == 7) {
                        studentId = "w" + newId;
                        for (int i = 0; i < studentCount; i++) {
                            if (students[i].getStudentId().equals(studentId)) {
                                System.out.println("Student id already entered.");
                                studentId = null;//if user input id already entered assign studentId to null
                                break;
                            }
                        }
                        break;
                    } else {
                        System.out.println("Invalid Input.Please enter 7-digit.Student ID");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input.Enter Integer number.");
                    sc.next();//without this line Invalid input.Enter Integer number. display infinitely.
                }
            }
            if (studentId != null) {//if studentId == null not execute this block
                Module module = new Module(0, 0, 0);
                Student student = new Student(studentId, null, module);
                students[studentCount] = student;
                studentCount++;
            }
        } else {
            System.out.println("No seats available.");
        }
        return studentCount;
    }

    public static int deleteStudent(Student[] students, int studentCount, Scanner sc) {
        displayArray(students, studentCount);
        int index = findStudentIndex(sc, studentCount, students);
        if (index == -1) {
            return studentCount;
        }
        if (studentCount > 0) {
            for (int i = index; i < studentCount; i++) {
                students[i] = students[i + 1];
            }
            students[studentCount - 1] = null;
            studentCount--;
            System.out.println("Delete student successfully");
        }
        return studentCount;
    }


    public static int findStudentIndex(Scanner sc, int studentCount, Student[] students) {
        String findStudentId;
        System.out.print("Enter the student ID w:");
        findStudentId = "w" + sc.next();
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentId().equals(findStudentId)) {
                System.out.println("ID :" + students[i].getStudentId());
                System.out.println("Name :" + students[i].getStudentName());
                System.out.println("Module marks :" + students[i].getModule());
                return i;
            }
        }
        System.out.println("Student ID not found");
        return -1;
    }

    public static void storeStudentDetails(Student[] students, int studentCount) {
        if (studentCount > 0) {
            try {
                int i;
                FileWriter storeData = new FileWriter("students results.txt");
                for (i = 0; i < studentCount; i++) {
                    if (students[i] != null) {
                        storeData.write(students[i].getStudentId() + "," + students[i].getStudentName() + "," + students[i].getModule());
                    } else {
                        break;
                    }
                }
                if (i > 0) {
                    System.out.println("All data store successfully.");
                }
                storeData.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        } else {
            System.out.println("Array is empty.");
        }
    }

    public static int loadStudentDetails(int studentCount, Student[] students) {
        String studentId;
        String studentName;
        double moduleMark_1;
        double moduleMark_2;
        double moduleMark_3;
        try {
            File oldFile = new File("students results.txt");
            Scanner read_file = new Scanner(oldFile);
            while (read_file.hasNextLine()) {
                String line = read_file.nextLine();
                String[] item = line.split(",");
                studentId = item[0];
                studentName = item[1];
                moduleMark_1 = Double.parseDouble(item[2]);
                moduleMark_2 = Double.parseDouble(item[3]);
                moduleMark_3 = Double.parseDouble(item[4]);

                Module module = new Module(moduleMark_1, moduleMark_2, moduleMark_3);
                Student student = new Student(studentId, studentName, module);
                students[studentCount] = student;
                studentCount++;
            }
            System.out.println("All data loaded.");
            read_file.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return studentCount;
    }

    public static void viewTheListOfStudents(int studentCount, Student[] students) {
        if(studentCount>0) {
            String[] studentNames = new String[studentCount];
            for (int i = 0; i < studentCount; i++) {
                studentNames[i] = students[i].getStudentName();
            }
            for (int i = 0; i < studentCount - 1; i++) {
                for (int j = 0; j < studentCount - i - 1; j++) {
                    if (studentNames[j].compareTo(studentNames[j + 1]) > 0) {
                        String temp = studentNames[j];
                        studentNames[j] = studentNames[j + 1];
                        studentNames[j + 1] = temp;
                    }
                }
            }
            System.out.println("List of students by name:");
            for (String name : studentNames) {
                System.out.print(name + ",");
            }
        }else{
            System.out.println("Student count is zero");
        }
    }

    public static void displayArray(Student[] students, int studentCount) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i] != null) {
                System.out.print(students[i].getStudentId() + "," + students[i].getStudentName() + "," + students[i].getModule());
            }
        }
    }

    public static void addAdditionalDetails(Scanner sc, Student[] students, int studentCount, int availableSeats) {
        if (studentCount > 0) {
            System.out.println("\na. Add student name");
            System.out.println("b. Module marks 1, 2 and 3");
            System.out.println("c. Summary");
            System.out.println("d. Report");
            System.out.println("e. Exit to Main menu\n");

            String option;
            while (true) {
                System.out.print("Enter option: ");
                option = sc.next().toLowerCase();
                if (option.length() == 1) {
                    break;
                } else {
                    System.out.println("Invalid input.Please enter a,b,c,d or e.");
                }
            }
            switch (option) {
                case "a":
                    System.out.println("a. Add student name");
                    addStudentName(studentCount, students, sc);
                    break;
                case "b":
                    System.out.println("b. Module marks 1, 2 and 3");
                    moduleMarks(studentCount, students, sc);
                    break;
                case "c":
                    System.out.println("c. Summary");
                    summary(studentCount, students);
                    break;
                case "d":
                    System.out.println("d. Report");
                    report(studentCount, students);
                    break;
                case "e":
                    System.out.println("e. Exit to main menu");
                    printMenu(sc, studentCount, availableSeats, students);

                    break;

                default:
                    System.out.println("Invalid input.Enter a,b,c,d or e.");

            }
            addAdditionalDetails(sc, students, studentCount, availableSeats);
        } else {
            System.out.println("Student count is Zero. Can't add additional details.");
        }

    }

    public static void addStudentName(int studentCount, Student[] students, Scanner sc) {
        int index = findStudentIndex(sc, studentCount, students);
        if (index == -1) {
            return;// if id not found exit
        }
        sc.nextLine();
        String studentName;
        while (true) {
            System.out.print("Enter student name :");
            studentName = sc.nextLine().toLowerCase();
            if (studentName.matches(".*\\d.*")) {
                System.out.println("Invalid name. Student name should not contain numbers.");
            } else {
                break;
            }
        }
        students[index].setStudentName(studentName);
        System.out.println("Student name add successfully");
    }

    public static void moduleMarks(int studentCount, Student[] students, Scanner sc) {
        int index = findStudentIndex(sc, studentCount, students);
        if (index == -1) {
            return;// if id not found exit
        }
        double moduleMark_1;
        double moduleMark_2;
        double moduleMark_3;
        while (true) {
            try {
                System.out.print("Enter module 1 marks:");
                moduleMark_1 = sc.nextInt();
                if (0 <= moduleMark_1 && moduleMark_1 <= 100) {
                    System.out.print("Enter module 2 marks:");
                    moduleMark_2 = sc.nextInt();
                    if (0 <= moduleMark_2 && moduleMark_2 <= 100) {
                        System.out.print("Enter module 3 marks:");
                        moduleMark_3 = sc.nextInt();
                        if (0 <= moduleMark_3 && moduleMark_3 <= 100) {
                            break;
                        } else {
                            System.out.println("Invalid input.Please enter correct marks.");
                        }
                    } else {
                        System.out.println("Invalid input.Please enter correct marks. ");
                    }
                } else {
                    System.out.println("Invalid input.Please enter correct marks. ");
                }
            } catch (InputMismatchException e) {
                System.out.println(e);
                sc.next();//without this line Invalid input.please enter valid student ID. display infinitely.
            }
        }
        Module module = new Module(moduleMark_1, moduleMark_2, moduleMark_3);
        students[index].setModule(module);
        System.out.println("Student module marks add successfully");
    }

    public static void summary(int studentCount, Student[] students) {
        int module_1 = 0;
        int module_2 = 0;
        int module_3 = 0;
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getModule().getModuleMark_1() >= 40) {
                module_1++;
            }
        }
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getModule().getModuleMark_2() >= 40) {
                module_2++;
            }
        }
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getModule().getModuleMark_3() >= 40) {
                module_3++;
            }
        }
        System.out.println("The total student registrations: " + studentCount);
        System.out.println("Total no of students who are scored more than 40 marks in Module 1: " + module_1);
        System.out.println("Total no of students who are scored more than 40 marks in Module 2: " + module_2);
        System.out.println("Total no of students who are scored more than 40 marks in Module 3: " + module_3);
    }

    public static void report(int studentCount, Student[] students) {
        // Bubble sort to sort students by average marks in descending order
        for (int i = 0; i < studentCount - 1; i++) {
            for (int j = 0; j < studentCount - i - 1; j++) {
                if (students[j] != null && students[j + 1] != null) {
                    if (students[j].getModule().getAverage() < students[j + 1].getModule().getAverage()) {
                        Student temp = students[j];
                        students[j] = students[j + 1];
                        students[j + 1] = temp;
                    }
                }
            }
        }

        // Print the sorted list of students by average marks
        System.out.println("Sorted list of students by average marks:");
        for (int i = 0; i < studentCount; i++) {
            if (students[i] != null) {
                System.out.print(students[i]);
            }
        }
        System.out.println("\n"+"\t***** Student Report *****");
        for (int k = 0; k < studentCount; k++) {
            if (students[k] != null) {
                Module module = students[k].getModule();
                System.out.println( "Student ID    :"+students[k].getStudentId()+"\n"+
                                    "Student Name  :"+students[k].getStudentName()+"\n"+
                                    "Module mark 1 :"+module.getModuleMark_1() +"\n" +
                                    "Module mark 2 :"+module.getModuleMark_2() +"\n"+
                                    "Module mark 3 :"+module.getModuleMark_3() +"\n"+
                                    "Total         :"+module.getTotal() + "\n" +
                                    "Average       :"+module.getAverage() + "\n" +
                                    "Grade         :"+module.calculateGrade()+"\n");
            }
        }
    }
}




