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
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "imageId")
    private Image image;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int normalIroning;

    @Column(nullable = false)
    private int fastIroning;

    @Column(nullable = false)
    private int normalDetergent;

    @Column(nullable = false)
    private int fastDetergent;

    @Column(nullable = false)
    private int normalGloriousPressing;

    @Column(nullable = false)
    private int fastGloriousPressing;
}
