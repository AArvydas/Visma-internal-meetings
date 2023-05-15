package Project.Meetings.Services;

import Project.Meetings.Entities.Attendee;
import Project.Meetings.Entities.Meeting;
import Project.Meetings.Repositories.AttendeeRepository;
import Project.Meetings.Repositories.MeetingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class MeetingService {

    @Autowired
    private MeetingRepository meetRep;

    @Autowired
    private AttendeeRepository attRep;

    public List<Meeting> getAllMeetings() {
        return meetRep.findAll();
    }

    public Meeting getMeetingById(Long id) {
        return meetRep.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meeting does not exist by this id:" + id));
    }

    public Meeting createMeeting(Meeting meeting) {
        return meetRep.save(meeting);
    }

    public Meeting addAttendeeToMeet(Long meetId, Long attendeeId) {
        Meeting meeting = meetRep.findById(meetId)
                .orElseThrow(() -> new EntityNotFoundException("Meeting does not exist"));
        Attendee attendee = attRep.findById(attendeeId)
                .orElseThrow(() -> new EntityNotFoundException("Attendee does not exist"));

        List<Meeting> meetingsTime = meetRep.findAll();
        boolean attendeeAlreadyAdded = meetingsTime.stream()
                .anyMatch(otherMeeting ->
                        !otherMeeting.getId().equals(meetId) &&
                                otherMeeting.getStartDate().equals(meeting.getStartDate()) &&
                                otherMeeting.getAttendeesInMeeting().contains(attendee)
                );

        if (attendeeAlreadyAdded) {
            throw new IllegalStateException("Attendee is already in another meeting at the same time");
        }

        meeting.addAttendeeToMeet(attendee);
        return meetRep.save(meeting);
    }

    public Meeting removeAttendeeFromMeet(Long meetId, Long attendeeId) {
        Meeting meeting = meetRep.findById(meetId)
                .orElseThrow(() -> new EntityNotFoundException("Meeting does not exist"));
        Attendee attendee = attRep.findById(attendeeId)
                .orElseThrow(() -> new EntityNotFoundException("Attendee does not exist"));

        meeting.removeAttendeeFromMeet(attendee);
        return meetRep.save(meeting);
    }

    public void deleteMeeting(Long id) {
        Meeting meeting = meetRep.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Meeting not found"));

        meetRep.delete(meeting);
    }

}
