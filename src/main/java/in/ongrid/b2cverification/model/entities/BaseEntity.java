package in.ongrid.b2cverification.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", updatable = false, nullable = false)
    @CreatedDate
    private Date created = new Date();


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_modified", nullable = false)
    @LastModifiedDate
    private Date modified = new Date();

    @Column(name = "date_deleted", nullable = true)
    private boolean deleted;
}
