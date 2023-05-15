package Project.Meetings.Repositories;

import Project.Meetings.Entities.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
