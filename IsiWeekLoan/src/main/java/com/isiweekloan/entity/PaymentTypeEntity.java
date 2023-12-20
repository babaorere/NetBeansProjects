package com.isiweekloan.entity;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_type", schema = "isiweekloanservices", catalog = "")
public class PaymentTypeEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaymentType", fetch = FetchType.LAZY)
    private Collection<PaymentDatailsEntity> paymentDatailsEntityCollection;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Basic
    @Column(name = "description", nullable = false, length = 512)
    private String description;

    @Basic
    @Column(name = "processing_fee", nullable = true, length = 512)
    private String processingFee; // Store any processing fees associated with specific payment types.

    @Basic
    @Column(name = "supported_currencies", nullable = true, length = 512)
    private String supportedCurrencies;

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public boolean equals(final Object inO) {
        if (this == inO) {
            return true;
        }
        if (inO == null || getClass() != inO.getClass()) {
            return false;
        }
        PaymentTypeEntity that = (PaymentTypeEntity) inO;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

}
