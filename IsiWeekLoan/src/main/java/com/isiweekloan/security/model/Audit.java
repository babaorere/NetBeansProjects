package com.isiweekloan.security.model;

//import com.isiweekloan.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class Audit {

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

//    @Column(name = "created_by")
//    @CreatedBy
//    private User createdBy;
//
//    @Column(name = "modified_by")
//    @LastModifiedBy
//    private User modifiedBy;
}
