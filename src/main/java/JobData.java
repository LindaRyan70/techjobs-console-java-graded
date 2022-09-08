import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "src/main/resources/job_data.csv";
    private static boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allJobs;

    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * @param field The column to retrieve values from
     * @return List of all of the values of the given field
     */
    public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadData();

        ArrayList<String> values = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        // Bonus mission: sort the results
        Collections.sort(values);

        return values;
    }

    public static ArrayList<HashMap<String, String>> findAll() {

        // load data, if not already loaded
        loadData();

        // Bonus mission; normal version returns allJobs
        return new ArrayList<>(allJobs);
    }

    /**
     * Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column   Column that should be searched.
     * @param value Value of teh field to search for
     * @return List of all jobs matching the criteria
     */
    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {

            String aValue = row.get(column);
//  For Final Task, I added .toLowerCase() in the line below to make search case-insensitive.
            if (aValue.toLowerCase().contains(value.toLowerCase())) {

                jobs.add(row);
            }
        }

        return jobs;
    }




  /* 1st Version findByValue() Task - My first passing way to solve the findByValue using textbook-taught Map.Entry
    and referencing the findByColumnAndValue() code. */

    /**
     * Search all columns for the given term
     *
     * @param value The search term to look for
     * @return      List of all jobs with at least one field containing the value
     */
    public static ArrayList<HashMap<String, String>> findByValue(String value) {

        // load data, if not already loaded
        loadData();

        // TODO - implement this method

// Make an ArrayList of HashMaps for the printJobs()
        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

// Search the info in each HashMap
        for (HashMap<String, String> jobRow : allJobs) {

//  Used Map.Entry per textbook Chapter 3.6 example code to create copy of jobRow k/v pairs to iterate through.
            for (Map.Entry<String, String> jobInfo : jobRow.entrySet()) {

//          This line is unnecessary b/c I can use method chaining to include .getValue() in the conditional below it.
//                String jobInfoValue = jobInfo.getValue();

//          Add case-insensitive code & a check to prevent duplicate entries & added HashMap info to jobs ArrayList
                if (jobInfo.getValue().toLowerCase().contains(value.toLowerCase()) && !jobs.contains(jobRow)) {
                    jobs.add(jobRow);
                }
            }
        }
        return jobs;
    }



// /* THIS ONE IS TOGGLED OFF BUT BOTH VERSIONS WORK */
//
//    /* 2nd Version printJob() Task - This was my second passing version for the findByValue() based on more research
//        into lambda expressions b/c of what I did with the printJob() task. */
///**
// * Search all columns for the given term
// *
// * @param value The search term to look for
// * @return      List of all jobs with at least one field containing the value
// */
//    public static ArrayList<HashMap<String, String>> findByValue(String value) {
//
//        // load data, if not already loaded
//        loadData();
//
//        // TODO - implement this method
//
////  I used similar code from the findByColumnAndValue() method to get started on this method.
//
//        ArrayList<HashMap<String, String>> jobValues = new ArrayList<>();
//
///* Used the same .forEach() method here to iterate through the key,values for the value entered by user and then add to the jobValues result
//    if it does not already contain it. I am able to define aValue within the .forEach() as it is implementing a Consumer interface with Map. Still learning how this all works,
//    so I may not have all the vocab correct. I added a step to see if the value of each iteration (aValue) contains the user term (value)
//    before determining if it should be added to the final jobValues ArrayList<HashMap<String, String>>.  */
//
//        for (HashMap<String, String> val : allJobs) {
//
//            val.forEach((key, aValue) -> {
////  For Final Task, I added .toLowerCase() to make search case-insensitive and added a duplicate entry check.
//                if (aValue.toLowerCase().contains(value.toLowerCase()) && !jobValues.contains(val)) {
//                    jobValues.add(val);
//                }
//            });
//        }
//        return jobValues;
//    }


    /**
     * Read in data from a CSV file and store it in a list
     */
    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

}
