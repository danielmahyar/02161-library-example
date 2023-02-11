package dtu.library.app;

public class Address {
   private String street, city;
   private int post_code;

   public Address(String street, int post_code, String city){
       this.street = street;
       this.post_code = post_code;
       this.city = city;
   }

    public String getStreet() {
        return street;
    }

    public int getPostCode() {
        return post_code;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Address)) {
            return false;
        }
        Address otherAddress = (Address) other;
        return street.equals(otherAddress.getStreet())
                && city.equals(otherAddress.getCity())
                && post_code == otherAddress.getPostCode();
    }

    @Override
    public int hashCode() {
        return street.hashCode() ^ post_code ^ city.hashCode();
    }


}
