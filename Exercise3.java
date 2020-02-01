import java.util.Random;
import java.util.Scanner;

class exercise3
{
  public static void main(String[] Args)
  {
    boolean playAgain = true;
    while(playAgain)
    {
      Random random = new Random();
      Scanner input = new Scanner(System.in);

      int target = random.nextInt(101);
      int tries = 1;

      System.out.println("Enter your guess between 0 - 100");
      int guess = input.nextInt();
      while(guess != target)
      {
        System.out.println("You Have guessed wrong!");

        if(target > guess){System.out.println(guess + " is less than the target");}

        else
          System.out.println(guess + " is greater than the target");

        guess = input.nextInt();
        tries++;
      }

      System.out.println("You have guessed it right!");
      System.out.println("Number of tries is: " + tries);

      System.out.println("Do you want to play again? (true or false) ");
      playAgain = input.nextBoolean();
    }
  }
}
