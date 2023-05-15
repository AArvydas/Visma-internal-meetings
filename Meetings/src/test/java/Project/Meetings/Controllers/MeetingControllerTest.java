package Project.Meetings.Controllers;

import Project.Meetings.Entities.Enums.CategoryEnum;
import Project.Meetings.Entities.Enums.TypeEnum;
import Project.Meetings.Entities.Meeting;
import Project.Meetings.Services.MeetingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@WebMvcTest(MeetingController.class)
public class MeetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeetingService meetingService;

    @Test
    void getAllMeetings() throws Exception {
        List<Meeting> meetings = new ArrayList<>();
        meetings.add(new Meeting("Test1", "Testing1", "Tester1", LocalDateTime.parse("2023-05-11T10:00:00"), LocalDateTime.parse("2023-05-11T10:30:00"), CategoryEnum.SHORT, TypeEnum.IN_PERSON));
        meetings.add(new Meeting("Test2", "Testing2", "Tester2", LocalDateTime.parse("2023-05-11T11:00:00"), LocalDateTime.parse("2023-05-11T11:30:00"), CategoryEnum.SHORT, TypeEnum.IN_PERSON));

        when(meetingService.getAllMeetings()).thenReturn(meetings);

        mockMvc.perform(MockMvcRequestBuilders.get("/meet/meetings"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Test2"));

        verify(meetingService, times(1)).getAllMeetings();
    }

    @Test
    void getMeetingById() throws Exception {
        Meeting meeting = new Meeting("Test", "Testing", "Tester", LocalDateTime.parse("2023-05-11T10:00:00"), LocalDateTime.parse("2023-05-11T10:30:00"), CategoryEnum.SHORT, TypeEnum.IN_PERSON);

        when(meetingService.getMeetingById(1L)).thenReturn(meeting);

        mockMvc.perform(MockMvcRequestBuilders.get("/meet/meeting/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test"));

        verify(meetingService, times(1)).getMeetingById(1L);
    }

    @Test
    void createMeeting() throws Exception {
        Meeting meeting = new Meeting("Test", "Testing", "Tester", LocalDateTime.parse("2023-05-11T10:00:00"), LocalDateTime.parse("2023-05-11T10:30:00"), CategoryEnum.SHORT, TypeEnum.IN_PERSON);

        when(meetingService.createMeeting(any(Meeting.class))).thenReturn(meeting);

        mockMvc.perform(MockMvcRequestBuilders.post("/meet")
                        .content("{\"name\":\"Test\",\"description\":\"Testing\",\"responsiblePerson\":\"Tester\",\"startTime\":\"2023-05-11T10:00:00\",\"endTime\":\"2023-05-11T10:30:00\",\"category\":\"SHORT\",\"type\":\"IN_PERSON\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("Test")));

        verify(meetingService, times(1)).createMeeting(any(Meeting.class));
    }

    @Test
    void addAttendeeToMeeting() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/meet/1/attendee/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(meetingService, times(1)).addAttendeeToMeet(1L, 1L);
    }

    @Test
    void deleteAttendeeFromMeeting() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/meet/1/attendee/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(meetingService, times(1)).removeAttendeeFromMeet(1L, 1L);
    }

    @Test
    void deleteMeeting() throws Exception {
        Meeting meeting = new Meeting("Test", "Testing", "Tester", LocalDateTime.parse("2023-05-11T10:00:00"), LocalDateTime.parse("2023-05-11T10:30:00"), CategoryEnum.SHORT, TypeEnum.IN_PERSON);
        meeting.setResponsiblePerson("Jonas");

        when(meetingService.getMeetingById(1L)).thenReturn(meeting);

        mockMvc.perform(MockMvcRequestBuilders.delete("/meet/delete/1/Jonas"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(meetingService, times(1)).deleteMeeting(1L);
    }

}