import java.util.Random;

import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;


public class exercise4
{
    public static void main(String[] Args)
    {
        exercise4 exercise = new exercise4();
        
        String text = exercise.randomizedChars();
    
        long startTimeIO = System.nanoTime();
        File ioFile = new File("javaio.txt");
        exercise.writeUsingIO(ioFile, text);

        long ioTime = exercise.readUsingIO(ioFile, startTimeIO);

        System.out.println("\n");        

        long startTimeNIO = System.nanoTime();
        String filePath = ".//javaNio.txt";

        exercise.writeUsingNIO(filePath, text);

        long nioTime = exercise.readUsingNIO(filePath, startTimeNIO);

        System.out.println("\n");

        exercise.comparison(ioTime, nioTime);
    }

    private String randomizedChars()
    {
        Random random = new Random();
        char c;
        String text = "";
        while (text.length() != 1000)
        {
            c = (char)(random.nextInt('z' - 'a' + 1) + 'a');
            text += "" + c;
        }
        return text;
    }

    private void comparison(long ioTime, long nioTime)
    {
        if(ioTime == 0 || nioTime == 0)
        {
            System.out.println("Error!");
        }
        else
        {
            if(ioTime > nioTime)
            {
                System.out.print("java.nio is ");
                System.out.print(ioTime - nioTime);
                System.out.println(" nanoseconds faster than java.io");
                System.out.println("Which result in " + String.valueOf((ioTime - nioTime)/1000000) + " miliseconds.");
            }
            else
            {
                System.out.print("java.io is ");
                System.out.print(nioTime - ioTime);
                System.out.println(" nanoseconds faster than java.nio");
                System.out.println("Which result in " + String.valueOf((nioTime - ioTime)/1000000) + " miliseconds.");
            }
        }
    }

    private void writeUsingIO(File file, String text)
    {
        try 
        {
            PrintWriter ioText = new PrintWriter(file);
            ioText.println(text);
            ioText.close();
        } 
        catch(FileNotFoundException ex)
        {
            System.out.println("java.io couldn't find any file to write the data.");
        }
    }

    private long readUsingIO(File file, long startTime)
    {
        long result;

        try
        {
            Scanner read = new Scanner(file);
            String text = read.nextLine();

            System.out.println(text);

            result = System.nanoTime() - startTime;

            System.out.print("\nTime elapsed while using java.io package's read/write procedure is ");
            System.out.print(result);
            System.out.println(" nanoseconds.");

            read.close();
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("java.io couldn't found any file to read from.");
            result = 0;
        }
        return result;
    }

    private void writeUsingNIO(String path, String text)
    {
        try
        {
            Files.write(Paths.get(path),text.getBytes());
        } 
        catch(IOException ex)
        {
            System.out.println("java.nio couldn't find any file to write the data.");
        }
    }

    private long readUsingNIO(String path, long startTime)
    {
        String text = "";
        long result;
        try
        {
            text = new String(Files.readAllBytes(Paths.get(path)));
            System.out.println(text);

            result = System.nanoTime() - startTime;
            
            System.out.print("\nTime elapsed while using java.nio package's read/write procedure is ");
            System.out.print(result);
            System.out.println(" nanoseconds.");
            
            
        }
        catch (IOException e)
        {
            System.out.println("java.nio couldn't find any file to read the data.");
            result = 0;
        }
        return result;
    }
}