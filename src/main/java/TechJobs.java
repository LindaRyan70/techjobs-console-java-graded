import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    static Scanner in = new Scanner(System.in);

    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View jobs by (type 'x' to quit):", actionChoices);

            if (actionChoice == null) {
                break;
            } else if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println();
                    System.out.println("*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println();
                System.out.println("Search term:");
                String searchTerm = in.nextLine();

                if (searchField.equals("all")) {
                    printJobs(JobData.findByValue(searchTerm));
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                }
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        int choiceIdx = -1;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        int i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println();
            System.out.println(menuHeader);

            // Print available choices
            for (int j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            if (in.hasNextInt()) {
                choiceIdx = in.nextInt();
                in.nextLine();
            } else {
                String line = in.nextLine();
                boolean shouldQuit = line.equals("x");
                if (shouldQuit) {
                    return null;
                }
            }

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }



  /* 1st Version printJobs() Task - This was one version of passing code using Map.Entry from textbook examples. NOTE: Utilized advice on Slack for
  Windows-users removing all hard-coded "\n" line breaks & replacing with System.out.println() to pass print tests. */

    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {

        if (someJobs.size() >= 1) {
            /* For every HashMap (job) in the ArrayList of HashMaps (someJobs), print line brk & formatting, then
            create an entrySet() of key/values and iterate through to print them, followed by more formatting. */
            for (HashMap<String, String> job : someJobs) {

                System.out.println();
                System.out.println("*****");

                for (Map.Entry<String, String> jobInfo : job.entrySet()) {
                    System.out.println(jobInfo.getKey() + ": " + jobInfo.getValue());
                }

                System.out.println("*****");
            }

        } else {
            System.out.print("No Results");
        }
    }



// /* THIS ONE IS TOGGLED OFF BUT BOTH VERSIONS WORK. */
//
//  /* 2nd Version printJobs() Task: This was another version of passing code that I found by researching the
//   .forEach() method and lambda expressions. */
//
//    // Print a list of jobs
//
//    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {
//
// /* Part 1: Implement printJobs - Per Directions, I created a nested loop to iterate over each HashMap.
//    NOTE: I had to utilize the advice on Slack for Windows-users removing all hard-coded "\n" line breaks and replace
//    with System.out.println() to pass print tests. Used if/else for initial conditionals to ensure there
//    is data to return/print based on user input by checking the .size() of someJobs. */
//
////      Started with if/else conditional
//        if (someJobs.size() >= 1) {
//
//           /* Created a for : each loop to iterate over each HashMap (job) : in the ArrayList of HashMops (someJobs) to
//            get individual job HashMap. Then I can iterate through that to access and print. */
//            for (HashMap<String, String> job : someJobs) {
////                Printing a line break and asterisks to match formatting output.
//                System.out.println();
//                System.out.println("*****");
//
//               /* Googled how to iterate over an ArrayList of HashMaps in Java and discovered .forEach() method code
//               line below. It uses a lambda expression (->). It is also considered an Internal Loop that is faster to
//               run/return info. It was introduced in Java 8. It takes the individual iteration of the job HashMap it is
//               on, and passing the 2 required parameters. The types are implied by being typed in job HashMap. I named
//               them key and value since that is what they are, but could be anything. It then uses the lambda symbol ->
//               as a shortcut vs {} to indicate a block of code and prints those out with appropriate formatting. */
//                job.forEach( (key, value) -> System.out.println(key + ": " + value));
//
////              I repeated the required formatting.
//                System.out.println("*****");
//            }
//
////      If there is no data to return based on user input, the following prints.
//        } else {
//            System.out.print("No Results");
//        }
//    }


}
