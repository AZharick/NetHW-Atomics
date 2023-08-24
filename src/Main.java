import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
   private static AtomicInteger beautiful3 = new AtomicInteger(0);
   private static AtomicInteger beautiful4 = new AtomicInteger(0);
   private static AtomicInteger beautiful5 = new AtomicInteger(0);

   public static void main(String[] args) throws InterruptedException {
      Random random = new Random();
      String[] texts = new String[100_000];
      for (int i = 0; i < texts.length; i++) {
         texts[i] = generateText("abc", 3 + random.nextInt(3));
      }

      Thread palindromeChecker = new Thread(() -> {
         for (int i = 0; i < texts.length; i++) {
            if (isPalindrome(texts[i])) {
               incrementCounter(texts[i].length());
            }
         }
      });

      Thread sameLetterChecker = new Thread(() -> {
         for (int i = 0; i < texts.length; i++) {
            if (isSameLetter(texts[i])) {
               incrementCounter(texts[i].length());
            }
         }
      });

      Thread charsOrderChecker = new Thread(() -> {
         for (int i = 0; i < texts.length; i++) {
            if (isCharOrdered(texts[i])) {
               incrementCounter(texts[i].length());
            }
         }
      });

      palindromeChecker.start();
      sameLetterChecker.start();
      charsOrderChecker.start();
      palindromeChecker.join();
      sameLetterChecker.join();
      charsOrderChecker.join();

      displayResult();
   }

   public static String generateText(String letters, int length) {
      Random random = new Random();
      StringBuilder text = new StringBuilder();
      for (int i = 0; i < length; i++) {
         text.append(letters.charAt(random.nextInt(letters.length())));
      }
      return text.toString();
   }

   public static boolean isPalindrome(String word) {
      StringBuilder stringBuilder = new StringBuilder(word);
      String reversed = String.valueOf(stringBuilder.reverse());
      if (word.equals(reversed)) {
         return true;
      } else {
         return false;
      }
   }

   public static boolean isSameLetter(String word) {
      char[] chars = word.toCharArray();
      int equalCount = 0;
      for (int i = 0; i < chars.length; i++) {
         if (chars[i] == chars[0]) {
            equalCount++;
         }
      }
      if (equalCount == chars.length) {
         return true;
      } else {
         return false;
      }
   }

   public static boolean isCharOrdered(String word) {
      char[] chars = word.toCharArray();
      int equalCount = 1;
      for (int i = 1; i < word.length(); i++) {
         if (chars[i] >= chars[i - 1]) {
            equalCount++;
         }
      }
      if (equalCount == chars.length) {
         return true;
      } else {
         return false;
      }
   }

   public static void incrementCounter(int wordLength) {
      switch (wordLength) {
         case 3:
            beautiful3.getAndIncrement();
            break;
         case 4:
            beautiful4.getAndIncrement();
            break;
         case 5:
            beautiful5.getAndIncrement();
            break;
      }
   }

   public static void displayResult() {
      System.out.println("Красивых слов с длиной 3: " + beautiful3);
      System.out.println("Красивых слов с длиной 4: " + beautiful4);
      System.out.println("Красивых слов с длиной 5: " + beautiful5);
   }
}