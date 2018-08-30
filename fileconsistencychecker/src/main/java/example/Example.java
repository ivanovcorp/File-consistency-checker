package example;

import com.ivanovcorp.consistencychecker.ConsistencyChecker;

public class Example {

  private static final String algorithm = "MD5";

  public static void main(String[] args) {
    //TODO: fix the issues with Logger
    System.out.print("Positive case. Are files equal:  ");
    runPositiveCase();
    System.out.println();

    System.out.print("Negative case. Are files equal: ");
    runNegativeCase();
    System.out.println();
  }

  private static void runNegativeCase() {
    String fileA = "src\\main\\resources\\fail-base-file.txt";
    String fileB = "src\\main\\resources\\fail-target-file.txt";
    System.out.print(ConsistencyChecker.checkFileConsistency(fileA, fileB, algorithm));
  }


  private static void runPositiveCase() {
    String fileA = "src\\main\\resources\\success-base-file.txt";
    String fileB = "src\\main\\resources\\success-target-file.txt";
    System.out.print(ConsistencyChecker.checkFileConsistency(fileA, fileB, algorithm));
  }
}
