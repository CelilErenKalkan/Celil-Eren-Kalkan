import java.util.Scanner;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.io.BufferedWriter;

class DataBase
{
    public static void main(String[] args)
    {
        DataBase db = new DataBase();
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome! Please type your command.");

        while(true)
        {
            String[] com = input.nextLine().split(" ");

            if(com[0].equals("EXIT")) {break;}

            else if(com[0].equals("TABLE")) {db.createTable(com);}

            else if(com[0].equals("SELECT") && com[com.length-2].equals("FROM")) {db.select(com);}

            else if(com[0].equals("SELECT") && com[com.length-4].equals("FROM") && com[com.length-2].equals("LIMIT")) {db.selectlimit(com);}

            else if(com[0].equals("INSERT") && com[1].equals("INTO") && com[3].equals("VALUES")) {db.insert(com);}

            else if(com[0].equals("UPDATE") && com[2].equals("SET") && com[4].equals("WHERE")) {db.update(com);}

            else if(com[0].equals("DELETE") && com[1].equals("FROM")) {db.delete(com);}

            else {System.out.println("Incorrect command, please use proper commands.");}

        }

        input.close();
    }

    void createTable(String[] com)
    {
        try
        {
            String path = ".//" + com[1] + ".txt";
            String prop = "";

            if(Files.exists(Paths.get(path))){System.out.println("The Table you are trying to create is already exist. Please type another name or use UPDATE command.");}

            else
            {
                for(int i = 2; i < com.length; i++)
                {
                    prop += com[i] + ";";               
                }

                Files.write(Paths.get(path), prop.getBytes(), StandardOpenOption.CREATE);
                System.out.println("Table " + com[1] + " has been created succesfully.");
            }
        } 
        catch(IOException ex)
        {
            System.out.println("Couldn't Create the Table.");
        }
    }

    void select(String[] com)
    {
        String path = ".//" + com[com.length-1] + ".txt";
        String content = "";

        try{content = new String(Files.readAllBytes(Paths.get(path)));}

        catch(IOException e){System.out.println("Couldn't Find the Table.");}

        String[] con = content.split("#");
        String[] c = con[0].split(";");
        int index = 0;

        for(int i = 0; i < c.length; i++)
        {
            if(com[1].equals(c[i])){index = i;}
        }

        for(int i = 1; i < con.length; i++)
        {
            c = con[i].split(";");
            System.out.println(c[index]);
        }
    }

    void selectlimit(String[] com)
    {
        String path = ".//" + com[com.length-3] + ".txt";
        String content = "";

        int a = Integer.valueOf(com[com.length-1]);

        try{content = new String(Files.readAllBytes(Paths.get(path)));}

        catch(IOException e){System.out.println("Couldn't Find the Table.");}

        String[] con = content.split("#");
        String[] c = con[0].split(";");
        int index = 0;

        for(int i = 0; i < a; i++)
        {
            if(com[1].equals(c[i])){index = i;}
        }

        for(int i = 1; i < a+1; i++)
        {
            c = con[i].split(";");
            System.out.println(c[index]);
        }
    }

    void insert(String[] com)
    {
        if(com.length - 4 == propertyInsert(com))
        {
            try
            {
                String path = ".//" + com[2] + ".txt";
                String values = "#";

                for(int i = 4; i < com.length; i++){values += com[i] + ";";}

                Files.write(Paths.get(path), values.getBytes(), StandardOpenOption.APPEND);
                System.out.println("Inputs has inserted.");
            } 
            catch(IOException ex)
            {
                System.out.println("Table " + com[2] + " isn't exists.");
            }
        }
        else
        {
            System.out.println("Amount of inputs is not equal to your entry. Please, give exact amount of the inputs.");
        }
    }

    int propertyInsert(String[] com)
    {
        String path = ".//" + com[2] + ".txt";
        String content = "";

        try{content = new String(Files.readAllBytes(Paths.get(path)));}

        catch(IOException e){System.out.println("Couldn't Find the Table.");}

        String[] contents = content.split("#");
        String[] c = contents[0].split(";");

        return c.length;
    }


    void update(String[] com)
    {
        String path = ".//" + com[1] + ".txt";
        String content = "";
        String[] lines;
        String[] properties;
        String[] set = com[3].split("=");
        String[] where = com[5].split("=");
        String[] c;
        String updated = "";

        int indexWhere = -1;
        int indexSet = -1;

        try
        {
            content = new String(Files.readAllBytes(Paths.get(path)));

            lines = content.split("#");
            properties = lines[0].split(";");
            updated = lines[0];


            for(int i = 0; i < properties.length; i++)
            {
                if(where[0].equals(properties[i])){indexWhere = i;}

                if(set[0].equals(properties[i])){indexSet = i;}                
            }

            if (indexWhere == -1 || indexSet == -1) System.out.println("Property Couldn't Found.");

            for (int i = 1; i < lines.length; i++)
            {
                c = lines[i].split(";");

                if(c[indexWhere].equals(where[1])){c[indexSet] = set[1];}

                updated += "#" + String.join(";", c);
            }

            BufferedWriter writer = Files.newBufferedWriter(Paths.get(path));
            writer.write("");
            writer.flush();

            Files.write(Paths.get(path), updated.getBytes(), StandardOpenOption.APPEND);
            System.out.println("Input has been updated.");
        }

        catch(IOException e){System.out.println("Couldn't Erase the File Content.");}
    }

    void delete(String[] com)
    {
        String property = propertyNames(com);
        String path = ".//" + com[2] + ".txt";

        try
        {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(path));
            writer.write("");
            writer.flush();
            Files.write(Paths.get(path), property.getBytes(), StandardOpenOption.APPEND);
        }
        catch(IOException e){System.out.println("Couldn't Erase the File Content.");}
    }

    String propertyNames(String[] com)
    {
        String path = ".//" + com[2] + ".txt";
        String content = "";

        try{content = new String(Files.readAllBytes(Paths.get(path)));}

        catch(IOException e){System.out.println("Couldn't Find the Table.");}

        String[] contents = content.split("#");
        
        return contents[0];
    }
}