import java.util.TreeMap;
import java.util.*;

public class exercise6
{
    public static void main(String[] Args)
    {
        TelephoneEntry td = new Person("Talha", "Doğrul", "Dorm 9", "+90", "5312786066");
        TelephoneEntry td2 = new Person("Talha", "Doğrul", "Dorm 9", "+48", "505942971");
        TelephoneEntry ad = new Person("Ahmet", "Doğrul", "34672", "+90", "5336974637");
        TelephoneEntry at = new Person("Adam", "Takacs", "Dorm 9", "+36", "302884802");
        TelephoneEntry ss = new Company("Sultan Sofrası", "Üsküdar", "+90", "5322050193"); 
        TelephoneEntry sc = new Company("SomeCompany", "Somewhere", "+65", "5322120193");

        TreeMap<TelephoneNumber, TelephoneEntry> treeMap = new TreeMap<TelephoneNumber, TelephoneEntry>();

        treeMap.put(td.getNumber(), td);
        treeMap.put(td2.getNumber(), td2);
        treeMap.put(ad.getNumber(), ad);
        treeMap.put(at.getNumber(), at);
        treeMap.put(ss.getNumber(), ss);
        treeMap.put(sc.getNumber(), sc);

        while()

        /*for(TelephoneNumber i : treeMap.keySet())
        {
            TelephoneEntry temp = treeMap.get(i);
            temp.description();
        }*/

    }
}

// ********** TELEPHONE NUMBER CLASS ***********
class TelephoneNumber implements Comparable<TelephoneNumber>
{
    // These should implement comparable interface
    private String countryCode;
    private String localNumber;

    
    public int compareTo(TelephoneNumber other)
    {
        if(countryCode.compareTo(other.getCountryCode()) ==  0)
        {
            if(localNumber.compareTo(other.getLocalNumber()) > 0)
                return 1;
            else if(localNumber.compareTo(other.getLocalNumber()) < 0)
                return -1;
            else
                return 0;
        }
        else if(countryCode.compareTo(other.getCountryCode()) > 0)
            return 1;
        else
            return -1;
    }

    public String getLocalNumber()
    {
        return localNumber;
    }

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setLocalNumber(String l)
    {
        localNumber = l;
    }

    public void setCountryCode(String c)
    {
        countryCode = c;
    }
}



// ********** TELEPHONE ENTRY CLASS ***********
abstract class TelephoneEntry
{
    abstract void description();
    abstract TelephoneNumber getNumber();
}



// ********** PERSON CLASS ***********
class Person extends TelephoneEntry
{
    private String name, lastName, address;
    private TelephoneNumber number = new TelephoneNumber();

    public Person(String n, String l, String a, String cc, String ln)
    {
        name = n;
        lastName = l;
        address = a;
        number.setCountryCode(cc);
        number.setLocalNumber(ln);
    }

    public void description()
    {
        System.out.print("Name: " + name + " " + lastName);
        System.out.print(", Address: " + address);
        System.out.println(", Telephone Number: " + number.getCountryCode() + number.getLocalNumber());
    }

    public TelephoneNumber getNumber(){
        return number;
    }
}



// ********** COMPANY CLASS ***********
class Company extends TelephoneEntry
{
    private String name, address;
    private TelephoneNumber number = new TelephoneNumber();

    public Company(String n, String a, String cc, String ln)
    {
        name = n;
        address = a;
        number.setCountryCode(cc);
        number.setLocalNumber(ln);
    }

    public void description()
    {
        System.out.print("Name: " + name);
        System.out.print(", Address: " + address);
        System.out.println(", Telephone Number: " + number.getCountryCode() + number.getLocalNumber());
    }

    public TelephoneNumber getNumber(){
        return number;
    }
}
