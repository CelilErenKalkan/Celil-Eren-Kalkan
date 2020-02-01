import java.util.TreeMap;
import java.util.Iterator;
import java.util.*;

public class Exercise6
{
    public static void main(String[] Args)
    {
        TelephoneEntry t1 = new Person("Celil Eren", "Kalkan", "90-001", "+90", "5382583899");
        TelephoneEntry t2 = new Person("Celil Eren", "Kalkan", "90-001", "+48", "505942116");
        TelephoneEntry t3 = new Person("Cihat Erim", "Kalkan", "34762", "+90", "5385979265");
        TelephoneEntry t4 = new Company("CD Project Red", "03-301", "+48", "505942971"); 

        TreeMap<TelephoneNumber, TelephoneEntry> treeMap = new TreeMap<TelephoneNumber, TelephoneEntry>();

        treeMap.put(t1.getNumber(), t1);
        treeMap.put(t2.getNumber(), t2);
        treeMap.put(t3.getNumber(), t3);
        treeMap.put(t4.getNumber(), t4);

        ArrayList<String> Members = new ArrayList<String>();
        Members.add(t1.description());
        Members.add(t2.description());
        Members.add(t3.description());
        Members.add(t4.description());
       
        ListIterator<String> iterator = Members.listIterator();
        System.out.println("List: ");
        while(iterator.hasNext()){System.out.println(iterator.next());}
    }
}

class TelephoneNumber implements Comparable<TelephoneNumber>
{

    private String countryCode;
    private String localNumber;

    
    public int compareTo(TelephoneNumber other)
    {
        if(countryCode.compareTo(other.getCountryCode()) ==  0)
        {
            if(localNumber.compareTo(other.getLocalNumber()) > 0){return 1;}
            else if(localNumber.compareTo(other.getLocalNumber()) < 0){return -1;}
            else
                return 0;
        }
        else if(countryCode.compareTo(other.getCountryCode()) > 0){return 1;}
        else
            return -1;
    }

    public String getLocalNumber(){return localNumber;}

    public String getCountryCode(){return countryCode;}

    public void setLocalNumber(String l){localNumber = l;}

    public void setCountryCode(String c){countryCode = c;}
}

abstract class TelephoneEntry
{
    abstract String description();
    abstract TelephoneNumber getNumber();
}

class Person extends TelephoneEntry
{
    private String N, LN, address;
    private TelephoneNumber no = new TelephoneNumber();

    public Person(String n, String l, String a, String cc, String ln)
    {
        N = n;
        LN = l;
        address = a;
        no.setCountryCode(cc);
        no.setLocalNumber(ln);
    }

    public String description()
    {
        String desc = ("Name: " + N + " " + LN + "\nAddress: " + address + " Telephone No: " + no.getCountryCode() + no.getLocalNumber());
        return desc;
    }

    public TelephoneNumber getNumber(){return no;}
}

class Company extends TelephoneEntry
{
    private String N, address;
    private TelephoneNumber no = new TelephoneNumber();

    public Company(String n, String a, String cc, String ln)
    {
        N = n;
        address = a;
        no.setCountryCode(cc);
        no.setLocalNumber(ln);
    }

    public String description()
    {
        String desc = ("Name: " + N + "\nAddress: " + address + " Telephone No: " + no.getCountryCode() + no.getLocalNumber());
        return desc;
    }
    public TelephoneNumber getNumber(){
        return no;
    }
}
