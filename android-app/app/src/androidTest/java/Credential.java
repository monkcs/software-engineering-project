


public class Credential
{
    public String email;
    public String password;

    public Credential(String email, String password)
    {
        this.email = email;
        this.password = password;

        if (!valid(email))
        {
            throw new IllegalArgumentException("Email is not in valid format");
        }

        if (password.isEmpty())
        {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        else if (password.length() < 12)
        {
            throw new IllegalArgumentException("Password cannot be shorter than 12 characters");
        }
    }

    static private boolean valid(String email)
    {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
