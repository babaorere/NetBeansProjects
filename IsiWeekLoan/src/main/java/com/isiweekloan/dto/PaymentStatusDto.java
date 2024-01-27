package com.isiweekloan.dto;

import com.isiweekloan.entity.PaymentDatailsEntity;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Collection;

@ApiModel()
public class PaymentStatusDto extends AbstractDto<Collection<PaymentDatailsEntity>> {
    private Collection<PaymentDatailsEntity> paymentDatailsEntityCollection;
    private Long id;
    @Size(max = 128)
    @NotBlank
    private String name;
    @Size(max = 512)
    @NotBlank
    private String description;

    public PaymentStatusDto() {
    }

    public void setPaymentDatailsEntityCollection(java.util.Collection<com.isiweekloan.entity.PaymentDatailsEntity> paymentDatailsEntityCollection) {
        this.paymentDatailsEntityCollection = paymentDatailsEntityCollection;
    }

    public java.util.Collection<com.isiweekloan.entity.PaymentDatailsEntity> getPaymentDatailsEntityCollection() {
        return this.paymentDatailsEntityCollection;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}