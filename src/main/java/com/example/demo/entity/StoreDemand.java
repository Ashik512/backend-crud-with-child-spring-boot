package com.example.demo.entity;

import com.example.demo.constants.DepartmentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "store-demands")
public class StoreDemand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "internal_department")
    @Enumerated(EnumType.STRING)
    private DepartmentType departmentType = DepartmentType.INTERNAL;

    @Column(name = "voucher_no", unique = true, nullable = false)
    private String voucherNo;

    @Column(name = "valid_till")
    private LocalDate validTill;

    @Column(length = 8000)
    private String remarks;

    @Column(name = "work_order_no", length = 100)
    private String workOrderNo;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_active", columnDefinition="boolean default true", nullable = false)
    private Boolean isActive = true;

    @OneToMany(mappedBy = "storeDemand", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<StoreDemandItem> storeDemandItemList = new ArrayList<>();

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof StoreDemand)) return false;
        return Objects.nonNull(this.getId()) && Objects
                .equals(this.getId(), (((StoreDemand) object).getId()));
    }

    @Override
    public int hashCode() {
        if (Objects.isNull(this.getId())) {
            return this.getClass().hashCode();
        }
        return this.getId().hashCode();
    }

}
