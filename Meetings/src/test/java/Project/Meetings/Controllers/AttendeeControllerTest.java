package Project.Meetings.Controllers;

import Project.Meetings.Entities.Attendee;
import Project.Meetings.Services.AttendeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


@WebMvcTest(AttendeeController.class)
public class AttendeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttendeeService attendeeService;

    @Test
    void getAllAttendees() throws Exception {

        List<Attendee> attendees = new ArrayList<>();
        attendees.add(new Attendee(1L, "John"));
        attendees.add(new Attendee(2L, "Jane"));

        when(attendeeService.findAll()).thenReturn(attendees);

        mockMvc.perform(MockMvcRequestBuilders.get("/att/attendees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Jane"));

        verify(attendeeService, times(1)).findAll();
    }

    @Test
    void createAttendee() throws Exception {

        Attendee attendee = new Attendee(1L, "John");

        when(attendeeService.createAttendee(any(Attendee.class))).thenReturn(attendee);

        mockMvc.perform(MockMvcRequestBuilders.post("/att")
                        .content("{\"id\":1,\"name\":\"John\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"));

        verify(attendeeService, times(1)).createAttendee(any(Attendee.class));
    }

    @Test
    void deleteAttendee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/att/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(attendeeService, times(1)).deleteAttendee(1L);
    }
}