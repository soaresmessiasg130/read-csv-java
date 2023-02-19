package com.soaresmessiasg130;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.ArrayUtils;

import com.opencsv.CSVReaderBuilder;

public class App {
  public void run (String[] args) {
    CommandLine cmdLine = parseArguments(args);

    if (cmdLine.hasOption("filename")) {
      System.out.println(cmdLine.getOptionValue("filename"));

      String filename = cmdLine.getOptionValue("filename");

      var data = readData(filename);

      calculateAndPrint(data);
    } else {
      printAppHelp();
    }
  }

  private CommandLine parseArguments(String[] args) {
    Options options = getOptions();

    CommandLine cmdLine = null;

    CommandLineParser parser = new DefaultParser();

    try {
      cmdLine = parser.parse(options, args);
    } catch (Exception ex) {
      System.err.println("Failed to parse command line arguments");

      System.err.println(ex.toString());

      System.exit(1);
    }

    return cmdLine;
  }

  private Options getOptions () {
    var options = new Options();

    options.addOption("f", "filename", true, "Filename to load data");

    return options;
  }

  private void printAppHelp() {
    Options options = new Options();

    var formatter = new HelpFormatter();

    formatter.printHelp("ReadCsvJava", options, true);
  }

  private double[] readData(String filename) {
    var data = new ArrayList<Double>();

    double[] res = null;

    try {
      var pathToFile = Paths.get(filename);

      var fileReader = Files.newBufferedReader(pathToFile);

      var csvReader = new CSVReaderBuilder(fileReader).build();

      String[] nextLine;

      while ((nextLine = csvReader.readNext()) != null) {
        for (String e : nextLine) {
          data.add(Double.parseDouble(e));
        }
      }

      res = ArrayUtils.toPrimitive(data.toArray(new Double[0]));
    } catch (IOException ex) {
      System.out.println("Failed to read file...");

      System.out.println(ex.toString());

      System.exit(1);
    }

    return res;
  }

  private void calculateAndPrint(double[] data) {
    System.out.println(data.length);
  }
}
