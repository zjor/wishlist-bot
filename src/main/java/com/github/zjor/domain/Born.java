package com.github.zjor.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Data
@MappedSuperclass
public class Born extends Model {

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}
