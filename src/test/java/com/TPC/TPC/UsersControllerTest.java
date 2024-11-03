package com.TPC.TPC;

import com.TPC.TPC.controller.UsersController;
import com.TPC.TPC.model.Users;
import com.TPC.TPC.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersRepository usersRepository;

    private Users user;

    @BeforeEach
    public void setup() {
        user = Users.builder()
                .usersID(1)
                .nome("John")
                .sobrenome("Doe")
                .email("johndoe@example.com")
                .password("password")
                .telefone("123456789")
                .endereco("Rua A, 123")
                .numero("12")
                .complemento("Apt 1")
                .ativo('Y')
                .build();
    }

    @Test
    public void listarUsers_DeveRetornarListaDeUsuarios() throws Exception {
        when(usersRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(user)));

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value("John"));
    }

    @Test
    public void getUserById_DeveRetornarUsuarioPorId() throws Exception {
        when(usersRepository.findById(1)).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("John"));
    }

    @Test
    public void createUser_DeveCriarNovoUsuario() throws Exception {
        when(usersRepository.save(any(Users.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"John\", \"sobrenome\":\"Doe\", \"email\":\"johndoe@example.com\", \"password\":\"password\", \"telefone\":\"123456789\", \"endereco\":\"Rua A, 123\", \"numero\":\"12\", \"ativo\":\"Y\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("John"));
    }

    @Test
    public void updateUser_DeveAtualizarUsuarioExistente() throws Exception {
        when(usersRepository.findById(1)).thenReturn(Optional.of(user));
        when(usersRepository.save(any(Users.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"John Updated\", \"sobrenome\":\"Doe\", \"email\":\"johndoe@example.com\", \"password\":\"password\", \"telefone\":\"123456789\", \"endereco\":\"Rua A, 123\", \"numero\":\"12\", \"ativo\":\"Y\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("John Updated"));
    }

    @Test
    public void deleteUser_DeveDeletarUsuarioExistente() throws Exception {
        when(usersRepository.findById(1)).thenReturn(Optional.of(user));
        doNothing().when(usersRepository).delete(user);

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
