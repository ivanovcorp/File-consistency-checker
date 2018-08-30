package com.ivanovcorp.consistencychecker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ConsistencyChecker {

  private static final Logger LOG = LogManager.getLogger(ConsistencyChecker.class);

  private ConsistencyChecker(){}

  public static boolean checkFileConsistency(String baseFilePath, String targetFilePath, String algorithm) {
    try {
      return checkFilesHash(new File(baseFilePath), new File(targetFilePath), algorithm);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException();
    }
  }

  private static boolean checkFilesHash(File baseFile, File targetFile, String algorithm)
      throws NoSuchAlgorithmException {
    if (baseFile.isDirectory() || targetFile.isDirectory()) {
      LOG.error("You've provided the following path: " +
          (baseFile.isDirectory() == true ? baseFile.getAbsolutePath() : targetFile.getAbsolutePath()));
      throw new RuntimeException();
    }
    String baseFileHash = generateHash(baseFile, algorithm);
    String targetFileHash = generateHash(targetFile, algorithm);
    return compareHashes(baseFileHash, targetFileHash);
  }

  private static boolean compareHashes(String baseFileHash, String targetFileHash) {
    return baseFileHash.contentEquals(targetFileHash);
  }

  private static String generateHash(File file, String algorithm) throws NoSuchAlgorithmException {
    FileInputStream fis = null;
    StringBuffer sb = null;
    try
    {
      MessageDigest md = MessageDigest.getInstance(algorithm); //MD5
      fis = new FileInputStream(file);
      byte[] dataBytes = new byte[1024];

      int nread = 0;

      while ((nread = fis.read(dataBytes)) != -1)
      {
        md.update(dataBytes, 0, nread);
      }

      byte[] mdbytes = md.digest();

      // convert the byte to hex format
      sb = new StringBuffer("");
      for (int i = 0; i < mdbytes.length; i++)
      {
        sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
      }
    }
    catch (NoSuchAlgorithmException e)
    {
      LOG.error(Thread.currentThread().getName() + "\n" + e.getMessage(), e);
    }
    catch (FileNotFoundException e)
    {
      LOG.error(Thread.currentThread().getName() + "\n" + e.getMessage(), e);
    }
    catch (IOException e)
    {
      LOG.error(Thread.currentThread().getName() + "\n" + e.getMessage(), e);
    }

    finally
    {
      try
      {
        if (null != fis)
        {
          fis.close();
        }
      }
      catch (IOException e)
      {
        LOG.error(Thread.currentThread().getName() + "\n" + e.getMessage(), e);
      }
      finally
      {
        if (null != fis)
        {
          fis = null;
        }
      }
    }
    return sb.toString();
  }
}
