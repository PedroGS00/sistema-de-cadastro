package com.sistema.cadastro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    @Column(name = "description", length = 500)
    private String description;

    @NotBlank(message = "SKU é obrigatório")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "SKU deve conter apenas letras maiúsculas, números e hífens")
    @Size(min = 3, max = 50, message = "SKU deve ter entre 3 e 50 caracteres")
    @Column(name = "sku", nullable = false, unique = true, length = 50)
    private String sku;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    @Digits(integer = 10, fraction = 2, message = "Preço deve ter no máximo 10 dígitos inteiros e 2 decimais")
    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Estoque é obrigatório")
    @Min(value = 0, message = "Estoque não pode ser negativo")
    @Max(value = 999999, message = "Estoque deve ter no máximo 6 dígitos")
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @DecimalMin(value = "0.01", message = "Peso deve ser maior que zero")
    @Digits(integer = 8, fraction = 3, message = "Peso deve ter no máximo 8 dígitos inteiros e 3 decimais")
    @Column(name = "weight", precision = 11, scale = 3)
    private BigDecimal weight;

    @Size(max = 50, message = "Categoria deve ter no máximo 50 caracteres")
    @Column(name = "category", length = 50)
    private String category;

    @Size(max = 50, message = "Marca deve ter no máximo 50 caracteres")
    @Column(name = "brand", length = 50)
    private String brand;

    @Size(max = 255, message = "URL da imagem deve ter no máximo 255 caracteres")
    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void validate() {
        if (price != null && price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        
        if (stock != null && stock < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo");
        }
        
        if (weight != null && weight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Peso deve ser maior que zero");
        }
    }

    public void decreaseStock(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        
        if (this.stock < quantity) {
            throw new IllegalStateException("Estoque insuficiente. Disponível: " + this.stock + ", Solicitado: " + quantity);
        }
        
        this.stock -= quantity;
    }

    public void increaseStock(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        
        this.stock += quantity;
    }

    public boolean isInStock() {
        return this.stock > 0 && this.isActive;
    }

    public boolean hasLowStock() {
        return this.stock <= 10 && this.stock > 0;
    }

    public Long getId() { return this.id; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }
    public String getSku() { return this.sku; }
    public void setSku(String sku) { this.sku = sku; }
    public BigDecimal getPrice() { return this.price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getStock() { return this.stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public BigDecimal getWeight() { return this.weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }
    public String getCategory() { return this.category; }
    public void setCategory(String category) { this.category = category; }
    public String getBrand() { return this.brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getImageUrl() { return this.imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Boolean getIsActive() { return this.isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public LocalDateTime getCreatedAt() { return this.createdAt; }
    public LocalDateTime getUpdatedAt() { return this.updatedAt; }
}