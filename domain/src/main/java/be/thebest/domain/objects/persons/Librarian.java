package be.thebest.domain.objects.persons;


import java.util.UUID;

public class Librarian extends Person {

    public Librarian(){
    }

    public Librarian(UUID uniqueID, String lastName, String firstName, String email) {
        super(uniqueID, lastName, firstName, email);
        this.setAddressable(new HasNoAddress());
        this.setInssable(new HasNoInss());
    }

    public static class LibrarianBuilder extends PersonBuilder{

        public static LibrarianBuilder librarian(){
            return new LibrarianBuilder();
        }

        @Override
        public Person build() {
            Librarian librarian = new Librarian();
            librarian.setUniqueID(this.getUniqueID());
            librarian.setLastName(this.getLastName());
            librarian.setFirstName(this.getFirstName());
            librarian.setEmail(this.getEmail());
            return librarian;
        }
    }


}