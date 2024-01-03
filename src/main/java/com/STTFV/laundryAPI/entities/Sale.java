package com.STTFV.laundryAPI.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@Getter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "num"))
public class Sale extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleId;

    @Column(unique = true, nullable = false)
    private String num;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id", referencedColumnName = "customerId")
    private Customer customer;

    @Column(nullable = false)
    private int tax;

    @Column(nullable = false)
    private int discount;

    @Column(nullable = false)
    private String near;

    @Column(nullable = false)
    private int numberService;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private boolean isDelivered;
}
