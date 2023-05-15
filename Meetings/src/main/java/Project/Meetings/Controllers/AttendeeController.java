package Project.Meetings.Controllers;

import Project.Meetings.Entities.Attendee;
import Project.Meetings.Services.AttendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/att")
public class AttendeeController {

    @Autowired
    private AttendeeService attendeeService;

    public AttendeeController(AttendeeService attendeeService) {
        this.attendeeService = attendeeService;
    }

    @GetMapping("/attendees")
    public List<Attendee> getAllAttendees() {
        return attendeeService.findAll();
    }

    @GetMapping("/attendee/{id}")
    public Attendee getAttendeeById(@PathVariable Long id) {
        return attendeeService.findAttendeeById(id);
    }

    @PostMapping
    public ResponseEntity<Attendee> createAttendee(@RequestBody Attendee attendee) throws URISyntaxException {
        Attendee newAttendee = attendeeService.createAttendee(attendee);
        return ResponseEntity.created(new URI("/attendees/" + newAttendee.getId())).body(newAttendee);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAttendee(@PathVariable Long id) {
        attendeeService.deleteAttendee(id);
        return ResponseEntity.ok().build();
    }
}
