public class exercise2
{
  public static void main(String[] Args)
  {
    if(Args.length != 3){System.out.println("There must be 3 input arguments.");}
    else
    {
      if(isNumeric(Args[1]) && isNumeric(Args[2]))
      {
        int beginning = Integer.parseInt(Args[1]);
        int ending = Integer.parseInt(Args[2]);
        if(Args[0].length() < beginning || Args[0].length() < ending){System.out.println("String is too short to cut.");}
        else
          System.out.println(Args[0].substring(beginning, ending+1));
      }
      else
        System.out.println("Arguments are invalid.");
    }
  }
  public static boolean isNumeric(String str)
  {
    try 
    {
      Integer.parseInt(str);
      return true;
    } 
    catch(NumberFormatException e)
    {
      return false;
    }
  }
}
