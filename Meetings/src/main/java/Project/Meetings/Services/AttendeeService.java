package Project.Meetings.Services;

import Project.Meetings.Entities.Attendee;
import Project.Meetings.Repositories.AttendeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AttendeeService {

    @Autowired
    private AttendeeRepository attendeeRepository;

    public List<Attendee> findAll() {
        return attendeeRepository.findAll();
    }

    public Attendee findAttendeeById(Long id) {
        return attendeeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Person does not exist by this id:" + id));
    }

    public Attendee createAttendee(Attendee attendee) {
        return attendeeRepository.save(attendee);
    }

    public void deleteAttendee(Long id) {
        Attendee person = attendeeRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Person by this id:" + id + " does not exists"));

        attendeeRepository.delete(person);
    }
}
