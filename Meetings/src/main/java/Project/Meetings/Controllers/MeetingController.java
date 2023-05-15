package Project.Meetings.Controllers;

import Project.Meetings.Entities.Meeting;
import Project.Meetings.Services.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meet")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/meetings")
    public List<Meeting> getAllMeetings() {
        return meetingService.getAllMeetings();
    }

    @GetMapping("/meeting/{id}")
    public Meeting getMeetingById(@PathVariable Long id) {
        Meeting meeting = meetingService.getMeetingById(id);
        return meeting;
    }

    @PostMapping
    public ResponseEntity createMeeting(@RequestBody Meeting meeting) {
        meetingService.createMeeting(meeting);
        return ResponseEntity.ok(meeting);
    }

    @PostMapping("/{meetId}/attendee/{attendeeId}")
    public ResponseEntity addAttendeeToMeeting(@PathVariable Long meetId, @PathVariable Long attendeeId) {
        meetingService.addAttendeeToMeet(meetId, attendeeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{meetId}/attendee/{attendeeId}")
    public ResponseEntity deleteAttendeeFromMeeting(@PathVariable Long meetId, @PathVariable Long attendeeId) {
        meetingService.removeAttendeeFromMeet(meetId, attendeeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}/{responsiblePerson}")
    public ResponseEntity deleteMeeting(@PathVariable Long id, @PathVariable String responsiblePerson) {
        Meeting meeting = meetingService.getMeetingById(id);
        if (meeting.getResponsiblePerson().equals(responsiblePerson)) {
            meetingService.deleteMeeting(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // return 403 Forbidden status if the responsible person doesn't match
        }
    }

}
