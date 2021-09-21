

public class Address
{
    public String streetaddress;
    public int postcode;
    public String city;
    public String county;

    public Address(String streetaddress, int postcode, String city, String county)
    {
        this.streetaddress = streetaddress;
        this.postcode = postcode;
        this.city = city;
        this.county = county;

        if (streetaddress.isEmpty())
        {
            throw new IllegalArgumentException("Street address cannot be empty");
        }

        if (postcode < 10000 || postcode > 99999)
        {
            throw new IllegalArgumentException("Post code is not valid");
        }

        if (city.isEmpty())
        {
            throw new IllegalArgumentException("City name cannot be empty");
        }
    }
}
