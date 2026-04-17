package Data.Models;

public class Customer {
    private int id;
    private String idCard;
    private String firstName;
    private String lastName1;
    private String lastName2;
    private String phone;
    private String address;

    public Customer() {}

    public Customer(String idCard, String firstName, String lastName1, String lastName2, String phone, String address) {
        this.idCard = idCard;
        this.firstName = firstName;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.phone = phone;
        this.address = address;
    }

    // getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getIdCard() { return idCard; }
    public void setIdCard(String idCard) { this.idCard = idCard; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName1() { return lastName1; }
    public void setLastName1(String lastName1) { this.lastName1 = lastName1; }

    public String getLastName2() { return lastName2; }
    public void setLastName2(String lastName2) { this.lastName2 = lastName2; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
