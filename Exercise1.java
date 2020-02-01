import java.util.Scanner;
import java.lang.Math;


public class exercise1
{
  public static void main(String[] Args)
  {
    Scanner input = new Scanner(System.in);

    int poly_a, poly_b, poly_c;
    double root_1, root_2, discriminant;

    System.out.println("This program calculates polonomials roots.");
    System.out.println("Polonomial format is Ax^2 + Bx + C\n");

    System.out.print("Enter 'a' for polonomial funtion: ");
    poly_a = input.nextInt();

    System.out.print("Enter 'b' for polonomial funtion: ");
    poly_b = input.nextInt();

    System.out.print("Enter 'c' for polonomial funtion: ");
    poly_c = input.nextInt();

    discriminant = (poly_b * poly_b) - (4 * poly_a * poly_c);

    if (discriminant < 0 ){System.out.println("This polynomial doesn't have real roots.");}

    else if (discriminant == 0)
    {
      root_1 = (-poly_b + Math.sqrt(discriminant)) / (2 * poly_a);
      System.out.print("This polynomial has one real root, ");
      System.out.println("which is: " + root_1 + ".");
    }

    else 
    {
      root_1 = (-poly_b + Math.sqrt(discriminant)) / (2 * poly_a);

      root_2 = (-poly_b - Math.sqrt(discriminant)) / (2 * poly_a);

      System.out.print("This polynomial has two real roots, ");
      System.out.println("which are: " + root_1 + " and " + root_2 + ".");
    }
  }
}
