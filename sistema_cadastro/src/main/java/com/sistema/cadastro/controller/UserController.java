package com.sistema.cadastro.controller;

import com.sistema.cadastro.dto.UserDTO;
import com.sistema.cadastro.entity.User;
import com.sistema.cadastro.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário com validação de CPF, email e CEP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "CPF ou email já cadastrado"),
            @ApiResponse(responseCode = "422", description = "CEP inválido ou não encontrado")
    })
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) {
        User createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<User> getUserById(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    @Operation(summary = "Listar usuários", description = "Retorna uma lista paginada de usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários recuperada com sucesso",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<User>> getAllUsers(
            @Parameter(description = "Parâmetros de paginação")
            @PageableDefault(size = 20) Pageable pageable) {
        Page<User> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar usuários com filtros", description = "Retorna uma lista paginada de usuários com base em filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários recuperada com sucesso",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<User>> getUsersByFilters(
            @Parameter(description = "Nome do usuário (parcial)")
            @RequestParam(required = false) String name,
            @Parameter(description = "Email do usuário (parcial)")
            @RequestParam(required = false) String email,
            @Parameter(description = "CPF do usuário (exato)")
            @RequestParam(required = false) String cpf,
            @Parameter(description = "Cidade do usuário (parcial)")
            @RequestParam(required = false) String city,
            @Parameter(description = "Estado do usuário (UF)")
            @RequestParam(required = false) String state,
            @Parameter(description = "Parâmetros de paginação")
            @PageableDefault(size = 20) Pageable pageable) {
        Page<User> users = userService.getUsersByFilters(name, email, cpf, city, state, pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar usuário por CPF", description = "Retorna os dados de um usuário específico pelo CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<User> getUserByCpf(
            @Parameter(description = "CPF do usuário (apenas números)", required = true)
            @PathVariable String cpf) {
        User user = userService.getUserByCpf(cpf);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar usuário por email", description = "Retorna os dados de um usuário específico pelo email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<User> getUserByEmail(
            @Parameter(description = "Email do usuário", required = true)
            @PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "CPF ou email já cadastrado para outro usuário")
    })
    public ResponseEntity<User> updateUser(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar usuário", description = "Remove um usuário do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats/today")
    @Operation(summary = "Estatísticas - Usuários criados hoje", description = "Retorna a quantidade de usuários criados no dia atual")
    @ApiResponse(responseCode = "200", description = "Estatística recuperada com sucesso")
    public ResponseEntity<Long> countUsersCreatedToday() {
        long count = userService.countUsersCreatedToday();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/stats/week")
    @Operation(summary = "Estatísticas - Usuários criados na semana", description = "Retorna a quantidade de usuários criados nos últimos 7 dias")
    @ApiResponse(responseCode = "200", description = "Estatística recuperada com sucesso")
    public ResponseEntity<Long> countUsersCreatedLastWeek() {
        long count = userService.countUsersCreatedLastWeek();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/stats/month")
    @Operation(summary = "Estatísticas - Usuários criados no mês", description = "Retorna a quantidade de usuários criados nos últimos 30 dias")
    @ApiResponse(responseCode = "200", description = "Estatística recuperada com sucesso")
    public ResponseEntity<Long> countUsersCreatedLastMonth() {
        long count = userService.countUsersCreatedLastMonth();
        return ResponseEntity.ok(count);
    }
}