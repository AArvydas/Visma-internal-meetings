package Project.Meetings.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attendee")
public class Attendee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "attendees")
    @JsonIgnore
    private Set<Meeting> meetings = new HashSet<>();

    public Attendee(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}