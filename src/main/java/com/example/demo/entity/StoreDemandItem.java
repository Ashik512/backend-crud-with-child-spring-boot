package com.example.demo.entity;

import com.example.demo.constants.PriorityType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "store_demand_items")
public class StoreDemandItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity_demanded", nullable = false, columnDefinition = "int default 0")
    private Integer quantityDemanded;

    @Column(name = "issued_qty", nullable = false, columnDefinition = "int default 0")
    private Integer issuedQty;

    @Column(name = "priority_type")
    private PriorityType priorityType;

    @Column(name = "ipc_cmm", length = 8000)
    private String ipcCmm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_demand_id")
    private StoreDemand storeDemand;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_active", columnDefinition="boolean default true", nullable = false)
    private Boolean isActive = true;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof StoreDemandItem)) return false;
        return Objects.nonNull(this.getId()) && Objects.equals(this.getId(), (((StoreDemandItem) object).getId()));
    }

    @Override
    public int hashCode() {
        if (Objects.isNull(this.getId())) {
            return this.getClass().hashCode();
        }
        return this.getId().hashCode();
    }
}
