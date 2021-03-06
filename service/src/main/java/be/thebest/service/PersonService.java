package be.thebest.service;

import be.thebest.domain.exception.IllegalFieldException;
import be.thebest.domain.objects.persons.*;
import be.thebest.domain.objects.persons.PersonRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Named
public class PersonService {
    private PersonRepository repository;

    @Inject
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Map<UUID, Person> getAllPeople() {
        //TODO is this method necessary?
        return repository.getPersonRepository();
    }

    public Map<UUID, Member> getMembers() {
        verifyPermission();
        return Collections.unmodifiableMap(repository.getMembersFromRepository());
    }

    public Member addMember(Member member) {
        emailAuthentication(member.getEmail());
        for (Member existingMember : repository.getMembersFromRepository().values()) {
            if (existingMember.getInss().equals(member.getInss())){
                throw new IllegalFieldException("INSS number already used");
            }

        }
        return repository.addMember(member);
    }

    public Admin addAdmin(Admin admin) {
        verifyPermission();
        emailAuthentication(admin.getEmail());
        return repository.addAdmin(admin);
    }

    public Librarian addLibrarian(Librarian librarian) {
        verifyPermission();
        emailAuthentication(librarian.getEmail());
        return repository.addLibrarian(librarian);
    }

    private void emailAuthentication(String email) {
        Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(email);
        if (!m.matches()){
            throw new IllegalFieldException("invalid email address");
        }
        for (Member existingMember : repository.getMembersFromRepository().values()) {
            if (existingMember.getEmail().equals(email)){
                throw new IllegalFieldException("email address already used");
            }
        }
    }

    private void verifyPermission() {
        //Asked Niels: can be ignored
    }
}
