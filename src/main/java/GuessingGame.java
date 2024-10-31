import java.util.Random;
import java.util.Scanner;

public class GuessingGame
{
    public static void main(final String[] args)
     {
        final Scanner scanner;
        final Random  random;
        String  userInput;
        int     userNumber;
        int     computerNumber;
        boolean keepPlaying;

        keepPlaying = true;
        random  = new Random();
        scanner = new Scanner(System.in);

        computerNumber = random.nextInt(1, 11);


        System.out.print("Enter 1-10 or Q to quit: ");
         while(scanner.hasNext())
         {
                if(scanner.hasNextInt())
                {
                    userNumber = scanner.nextInt();
                    System.out.printf("(computer picked %d)" + System.lineSeparator(),
                            computerNumber);
                    if(userNumber == computerNumber)
                    {
                        System.out.println("Correct!");
                        computerNumber = random.nextInt(1, 11);

                    }
                    else
                    {
                        System.out.println(userNumber + " is wrong");
                        System.out.println("Try again");
                    }
                }
                else // user typed a NON INT
                {
                    userInput = scanner.next();
                    if(userInput.equalsIgnoreCase("Q"))
                    {
                        System.out.println("thanks for playing");
                        break;
                    }
                    else
                    {
                        System.err.println("invalid input: " + userInput);
                    }
                }
             System.out.print("Enter 1-10 again or Q to quit: ");
        }
        scanner.close();
    }
}
