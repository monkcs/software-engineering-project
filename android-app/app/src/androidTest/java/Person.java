import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class Person
{
    public String forename;
    public String lastname;
    public LocalDate birthdate;
    public boolean appis;

    public Address address;

    public Person(String forename, String lastname, LocalDate birthdate, Address address)
    {
        this.forename = forename;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.address = address;

        if (forename.isEmpty() || lastname.isEmpty())
        {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        LocalDate today = LocalDate.now();
        if (Period.between(birthdate, today).getYears() < 18)
        {
            throw new IllegalArgumentException("Person need to be an adult");
        }
    };

    public boolean testis() {

    return true;

    }

}
