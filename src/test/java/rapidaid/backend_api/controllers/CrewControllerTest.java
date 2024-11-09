//package rapidaid.backend_api.controllers;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import rapidaid.backend_api.services.CrewService;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(CrewController.class)
//public class CrewControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CrewService crewService;
//
//    @Test
//    public void shouldReturnAllCrews() throws Exception {
//        mockMvc.perform(get("/crews"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("getAllCrews"));
//    }
//
//    @Test
//    public void shouldCreateCrew() throws Exception {
//        mockMvc.perform(post("/crews"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("createCrew"));
//    }
//
//    @Test
//    public void shouldReturnCrewById() throws Exception{
//        mockMvc.perform(get("/crews/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("getCrew 1"));
//    }
//    @Test
//    public void shouldUpdateCrew() throws Exception {
//        mockMvc.perform(put("/crews/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("updateCrew 1"));
//    }
//
//    @Test
//    public void shouldPatchCrew() throws Exception {
//        mockMvc.perform(patch("/crews/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("patchCrew 1"));
//    }
//
//    @Test
//    public void shouldDeleteCrew() throws Exception {
//        mockMvc.perform(delete("/crews/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("deleteCrew 1"));
//    }
//}
