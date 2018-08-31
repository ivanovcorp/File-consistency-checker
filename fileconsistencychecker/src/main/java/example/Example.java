package example;

import com.ivanovcorp.consistencychecker.ConsistencyChecker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Example {

  private static final String algorithm = "MD5";
  private static final Logger LOG = LogManager.getLogger(Example.class);

  public static void main(String[] args) {
    LOG.info("Positive case. Are files equal:  " + runPositiveCase());
    LOG.info("Negative case. Are files equal: " + runNegativeCase());
  }

  private static boolean runNegativeCase() {
    String fileA = "src\\main\\resources\\fail-base-file.txt";
    String fileB = "src\\main\\resources\\fail-target-file.txt";
    return ConsistencyChecker.checkFileConsistency(fileA, fileB, algorithm);
  }


  private static boolean runPositiveCase() {
    String fileA = "src\\main\\resources\\success-base-file.txt";
    String fileB = "src\\main\\resources\\success-target-file.txt";
    return ConsistencyChecker.checkFileConsistency(fileA, fileB, algorithm);
  }
}
