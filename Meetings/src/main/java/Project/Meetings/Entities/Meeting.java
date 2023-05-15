package Project.Meetings.Entities;

import Project.Meetings.Entities.Enums.CategoryEnum;
import Project.Meetings.Entities.Enums.TypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meetings")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String responsiblePerson;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    @Enumerated(EnumType.STRING)
    private TypeEnum type;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "attendees_in_meeting",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "attendee_id")
    )
    private Set<Attendee> attendees = new HashSet<>();

    public void addAttendeeToMeet(Attendee attendee) {
        attendees.add(attendee);
        attendee.getMeetings().add(this);
    }

    public void removeAttendeeFromMeet(Attendee attendee) {
        attendees.remove(attendee);
        attendee.getMeetings().remove(this);
    }

    @JsonProperty("attendeesInMeeting")
    public Set<Attendee> getAttendeesInMeeting() {
        return attendees;
    }

    public Meeting(String name, String responsiblePerson, String description, LocalDateTime startDate, LocalDateTime endDate, CategoryEnum category, TypeEnum type) {
        this.name = name;
        this.responsiblePerson = responsiblePerson;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.type = type;
    }
}