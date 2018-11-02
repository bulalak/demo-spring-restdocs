package sk.vub.demo.demorestdoc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class PersonController {

    @GetMapping("/persons")
    public ResponseEntity<List<PersonDTO>> getPersons() {
        List<PersonDTO> persons = Arrays.asList(new PersonDTO("Janko", "Hrasko"), new PersonDTO("Jozko", "Mrkvicka"));
        return ResponseEntity.ok(persons);
    }
}
