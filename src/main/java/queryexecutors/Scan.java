package queryexecutors;

import com.google.common.collect.Streams;

import java.io.*;
import java.util.Arrays;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Scan implements Resettable {

  private String tableName;
  private BufferedReader buffer;
  private String[] headers;

  public Scan(String tableName) {
    this.tableName = tableName;
  }

  public Optional<Map> next() {
    if (buffer == null) {
      initialize();
    }

    return readNextLine()
        .map(
            values -> {
              Map<String, String> tuple = new HashMap<>();
              for (int i = 0; i < headers.length; i++) {
                tuple.put(headers[i], values[i]);
              }
              return tuple;
            });
  }

  public void reset() {
    initialize();
  }

  private void initialize() {
    String csv = String.format("data/%s.csv", tableName);
    buffer = openFile(csv);
    headers =
        readNextLine()
            .orElseThrow(
                () -> {
                  throw new IllegalStateException("No Headers!");
                });
  }

  private BufferedReader openFile(String fileName) {
    BufferedReader result = null;
    try {
      result = new BufferedReader(new FileReader(fileName));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return result;
  }

  private Optional<String[]> readNextLine() {
    String nextLine = "";

    try {
      nextLine = buffer.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return Optional.ofNullable(nextLine).map(str -> str.split(","));
  }

  public static void main(String[] args) {
    Scan movies = new Scan("movies");
    System.out.println();
    movies.next().ifPresent(tuple -> tuple.forEach((key, value) -> System.out.println(key.toString() + value.toString())));
  }
}
