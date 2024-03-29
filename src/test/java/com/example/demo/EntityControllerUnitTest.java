
package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest{

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        doctor = new Doctor("John", "Doe", 30, "john.doe@example.com");
    }

    @Test
    void shouldGetAllDoctors() throws Exception {
        when(doctorRepository.findAll()).thenReturn(Arrays.asList(doctor));

        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].age").value(30))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));
    }
    
    @Test
    void shouldGetDoctorById() throws Exception {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        mockMvc.perform(get("/api/doctors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void shouldCreateDoctor() throws Exception {
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        mockMvc.perform(post("/api/doctor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void shouldDeleteDoctor() throws Exception {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        doNothing().when(doctorRepository).deleteById(1L);

        mockMvc.perform(delete("/api/doctors/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteAllDoctors() throws Exception {
        doNothing().when(doctorRepository).deleteAll();

        mockMvc.perform(delete("/api/doctors"))
                .andExpect(status().isOk());
    }
}

@WebMvcTest(PatientController.class)
class PatientControllerUnitTest{

    @MockBean
    private PatientRepository patientRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient("John", "Doe", 30, "john.doe@example.com");
    }

    @Test
    void shouldGetAllPatients() throws Exception {
        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient));

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].age").value(30))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));
    }

    @Test
    void shouldGetPatientById() throws Exception {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        mockMvc.perform(get("/api/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void shouldCreatePatient() throws Exception {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/api/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void shouldDeletePatient() throws Exception {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        doNothing().when(patientRepository).deleteById(1L);

        mockMvc.perform(delete("/api/patients/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteAllPatients() throws Exception {
        doNothing().when(patientRepository).deleteAll();

        mockMvc.perform(delete("/api/patients"))
                .andExpect(status().isOk());
    }
}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest{

    @MockBean
    private RoomRepository roomRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Room room;

    @BeforeEach
    void setUp() {
        room = new Room("Dermatology");
    }

    @Test
    void shouldGetAllRooms() throws Exception {
        when(roomRepository.findAll()).thenReturn(Arrays.asList(room));

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roomName").value("Dermatology"));
    }

    @Test
    void shouldGetRoomByRoomName() throws Exception {
        when(roomRepository.findByRoomName("Dermatology")).thenReturn(Optional.of(room));

        mockMvc.perform(get("/api/rooms/Dermatology"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomName").value("Dermatology"));
    }

    @Test
    void shouldCreateRoom() throws Exception {
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        mockMvc.perform(post("/api/room")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roomName").value("Dermatology"));
    }

    @Test
    void shouldDeleteRoom() throws Exception {
        when(roomRepository.findByRoomName("Dermatology")).thenReturn(Optional.of(room));
        doNothing().when(roomRepository).deleteByRoomName("Dermatology");

        mockMvc.perform(delete("/api/rooms/Dermatology"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteAllRooms() throws Exception {
        doNothing().when(roomRepository).deleteAll();

        mockMvc.perform(delete("/api/rooms"))
                .andExpect(status().isOk());
    }
}
