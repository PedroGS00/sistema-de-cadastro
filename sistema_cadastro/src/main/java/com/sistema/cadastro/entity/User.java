package com.sistema.cadastro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve conter apenas números e ter 11 dígitos")
    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "^\\d{8}$", message = "CEP deve conter apenas números e ter 8 dígitos")
    @Column(name = "cep", nullable = false, length = 8)
    private String cep;

    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    @Column(name = "address", length = 200)
    private String address;

    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    @Column(name = "city", length = 100)
    private String city;

    @Size(max = 2, message = "Estado deve ter 2 caracteres")
    @Column(name = "state", length = 2)
    private String state;

    @Size(max = 100, message = "Bairro deve ter no máximo 100 caracteres")
    @Column(name = "neighborhood", length = 100)
    private String neighborhood;

    @Size(max = 50, message = "Complemento deve ter no máximo 50 caracteres")
    @Column(name = "complement", length = 50)
    private String complement;

    @Size(max = 10, message = "Número deve ter no máximo 10 caracteres")
    @Column(name = "number", length = 10)
    private String number;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void validate() {
        if (cpf != null && !isValidCpf(cpf)) {
            throw new IllegalArgumentException("CPF inválido");
        }
    }

    private boolean isValidCpf(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("\\D", "");
        
        // Verifica se tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }
        
        // Verifica se todos os dígitos são iguais (CPF inválido)
        boolean allDigitsEqual = true;
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) {
                allDigitsEqual = false;
                break;
            }
        }
        if (allDigitsEqual) {
            return false;
        }
        
        // Calcula o primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit >= 10) {
            firstDigit = 0;
        }
        
        // Calcula o segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit >= 10) {
            secondDigit = 0;
        }
        
        // Verifica os dígitos verificadores
        return cpf.charAt(9) - '0' == firstDigit && cpf.charAt(10) - '0' == secondDigit;
    }

    public Long getId() {
        return this.id;
    }

    public String getCep() { return this.cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getAddress() { return this.address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return this.city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return this.state; }
    public void setState(String state) { this.state = state; }

    public String getNeighborhood() { return this.neighborhood; }
    public void setNeighborhood(String neighborhood) { this.neighborhood = neighborhood; }

    public String getComplement() { return this.complement; }
    public void setComplement(String complement) { this.complement = complement; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getNumber() { return this.number; }
    public void setNumber(String number) { this.number = number; }

    public String getCpf() { return this.cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}