import java.util.Vector;
import java.util.Arrays;
import java.util.Scanner;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.IOException;

public class Exercise5
{

    public static void main(String[] arg) 
    { 
		Exercise5 demo = new Exercise5();
		WriteVector Vec = new WriteVector();
		Vector<Double> vec_1 = new Vector<>();
		Vector<Double> vec_2 = new Vector<>();
		Vector<Double> vec_result = new Vector<>();

		vec_1 = demo.getInput();
		vec_2 = demo.getInput();

		String path = ".//vector.txt";

		Vec.writeVector(path, "----------");

        try 
		{
            vec_result = demo.Adding(vec_1, vec_2);
        } 
		catch(DifferentVectorsLengthException e)
		{
        	System.out.println(e.getMessage());
        }


        for(int i = 0; i < vec_result.size(); i++)
		{
            Vec.writeVector(path, Double.toString(vec_result.get(i)));
            System.out.println(vec_result.get(i));
        }
 
    }

    class DifferentVectorsLengthException extends Exception
		{
       	private int v1_length;
        private int v2_length;
        public DifferentVectorsLengthException(String message, int l1, int l2)
		{
        	super(message);
        	v1_length = l1;
        	v2_length = l2;
        }

        	public void setV1Length(int l){v1_length = l;}
        	public void setV2Length(int l){v2_length = l;}

        	public int getV1Length(){return v1_length;}
        	public int getV2Length(){return v2_length;}
    }

    boolean isNumeric(String a)
    {
		try
		{
			Double.parseDouble(a);
			return true;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
    }


    Vector<Double> getInput()
    {
			Vector<Double> x = new Vector<>();
	
			Scanner input = new Scanner(System.in);
			System.out.println("Please enter the members of the first vector giving a space between them. Press Enter when you've done.");

      String delimiter = " ";

      String[] Members = input.nextLine().split(delimiter);

			for (int i = 0; i < Members.length; i++)
			{
				if(isNumeric(Members[i])){x.add(Double.parseDouble(Members[i]));}
			}
			return x;	
    }

    Vector<Double> Adding(Vector v, Vector y) throws DifferentVectorsLengthException
    {
			if(v.size() != y.size()){throw new DifferentVectorsLengthException("Error: Vectors length should be equal, please enter vectors again: ", v.size(), y.size());}
			else
			{
				Vector<Double> x = new Vector<>();
				for(int i = 0; i < v.size(); i++){x.add((Double)v.get(i) + (Double)y.get(i));}
				return x;
			}
    }
}

class WriteVector
{
  void writeVector(String path, String text)
  {
		try
		{
			Files.write(Paths.get(path), Arrays.asList(text),
    	Files.exists(Paths.get(path)) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
  	} 
  	catch(IOException ex)
    {
   		System.out.println("Error: No File");
    }
  }
}