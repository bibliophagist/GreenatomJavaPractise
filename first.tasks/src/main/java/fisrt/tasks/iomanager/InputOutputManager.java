package fisrt.tasks.iomanager;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputOutputManager extends Thread {

    private static final String REGEX_TO_FIND_FILE_PATH = "([A-Z|a-z]:\\\\[^*|\"<>?\\n- ]*)|(\\\\\\\\.*?\\\\[^*|\"<>?\\n- ]*)";
    private static final String REGEX_TO_FIND_COMMAND = "-([^*|\"<>?\\n- ]*)";
    private static final String REGEX_TO_FIND_MESSAGE = "\"(.*)\"";

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String inputLine = scanner.nextLine();
            Matcher path = Pattern.compile(REGEX_TO_FIND_FILE_PATH).matcher(inputLine);
            Matcher command = Pattern.compile(REGEX_TO_FIND_COMMAND).matcher(inputLine);
            Matcher message = Pattern.compile(REGEX_TO_FIND_MESSAGE).matcher(inputLine);
            if (command.find() && path.find()) {
                String pathString = path.group(0);
                String commandString = command.group(1);
                System.out.println(commandString);
                switch (commandString) {
                    case "create": {
                        String output = (InputOutputManagerUtils.createFile(pathString)) ?
                                "File was successfully created" : "File was not created, such file already exists";
                        System.out.println(output);
                        break;
                    }
                    case "delete": {
                        String output = (InputOutputManagerUtils.deleteFile(pathString)) ?
                                "File was successfully deleted" : "File was not deleted";
                        System.out.println(output);
                        break;
                    }
                    case "read":
                        try {
                            String output = InputOutputManagerUtils.readFile(pathString);
                            System.out.println(output);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "write":
                        if (message.find()) {
                            String messageString = message.group(1);
                            try {
                                InputOutputManagerUtils.writeToFile(pathString, messageString);
                                System.out.println("Input string has been successfully added to the file.");
                            } catch (FileNotExistsException e) {
                                e.printStackTrace();
                                System.err.println("Such file is not exist");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        break;
                    default:
                        printErrorMessage();
                        break;
                }
            } else printErrorMessage();
        }
    }

    private void printErrorMessage() {
        System.out.println("The command was not processed.\n" +
                "Please use one of the commands -write, -create, -delete or -read. Command example:" +
                " \"c:\\\\testdata\\myfile.txt -write \"blablabla\"\".");
    }
}
