package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	@Autowired
	private TestEntityManager entityManager;

	private Doctor d1;

	private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;

    private DateTimeFormatter formatter;

    @BeforeEach
    public void setUp() {
        d1 = new Doctor("John", "Doe", 35, "johndoe@mail.com");
        p1 = new Patient("Jane", "Doe", 25, "janedoe@mail.com");
        r1 = new Room("Room 1");

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        a1 = new Appointment(p1, d1, r1, LocalDateTime.parse("2021-01-01 10:00", formatter), LocalDateTime.parse("2021-01-01 11:00", formatter));
        a2 = new Appointment(p1, d1, r1, LocalDateTime.parse("2021-01-01 10:30", formatter), LocalDateTime.parse("2021-01-01 11:30", formatter));
        a3 = new Appointment(p1, d1, r1, LocalDateTime.parse("2021-01-01 12:00", formatter), LocalDateTime.parse("2021-01-01 13:00", formatter));
    }

    // Doctor tests
    @Test
    public void testCreateDoctor() {
        assertEquals(d1.getFirstName(), "John");
        assertEquals(d1.getLastName(), "Doe");
        assertEquals(d1.getAge(), 35);
        assertEquals(d1.getEmail(), "johndoe@mail.com");
    }

    @Test
    public void testSaveandRetrieveDoctor() {
        Doctor savedDoctor = entityManager.persistAndFlush(d1);
        Doctor retrievedDoctor = entityManager.find(Doctor.class, savedDoctor.getId());
        assertEquals(savedDoctor, retrievedDoctor);
    }

    @Test 
    public void testDoctorId() {
        long id = 1;
        d1.setId(id);
        assertEquals(d1.getId(), id);
    }
    
    // Patient tests
    @Test
    public void testCreatePatient() {
        assertEquals(p1.getFirstName(), "Jane");
        assertEquals(p1.getLastName(), "Doe");
        assertEquals(p1.getAge(), 25);
        assertEquals(p1.getEmail(), "janedoe@mail.com");
    }

    @Test
    public void testSaveandRetrievePatient() {
        Patient savedPatient = entityManager.persistAndFlush(p1);
        Patient retrievedPatient = entityManager.find(Patient.class, savedPatient.getId());
        assertEquals(savedPatient, retrievedPatient);
    }

    @Test
    public void testPatientId() {
        long id = 1;
        p1.setId(id);
        assertEquals(p1.getId(), id);
    }

    // Room tests
    @Test
    public void testCreateRoom() {
        assertEquals(r1.getRoomName(), "Room 1");
    }

    @Test
    public void testSaveandRetrieveRoom() {
        Room savedRoom = entityManager.persistAndFlush(r1);
        Room retrievedRoom = entityManager.find(Room.class, savedRoom.getRoomName());
        assertEquals(savedRoom, retrievedRoom);
    }

    // Appointment tests
    @Test
    public void testCreateAppointment() {
        assertEquals(a1.getPatient(), p1);
        assertEquals(a1.getDoctor(), d1);
        assertEquals(a1.getRoom(), r1);
        assertEquals(a1.getStartsAt(), LocalDateTime.parse("2021-01-01 10:00", formatter));
        assertEquals(a1.getFinishesAt(), LocalDateTime.parse("2021-01-01 11:00", formatter));
    }

    @Test
    public void testAppointmentId() {
        long id = 1;
        a1.setId(id);
        assertEquals(a1.getId(), id);
    }

    @Test
    public void testAppointmentOverlapsWithEarlierEnding() {
        assertThat(a1.overlaps(a2)).isTrue(); // a1 starts before a2 ends and a1 ends after a2 starts
    }

    @Test
    public void testAppointmentDoesNotOverlapWithLaterStarting() {
        assertThat(a1.overlaps(a3)).isFalse(); // a1 ends before a3 starts
    }
}
