package com.github.zjor.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@MappedSuperclass
public abstract class Model implements Serializable {

    @Id
    @GeneratedValue(generator = "short-id")
    @GenericGenerator(name = "short-id", strategy = "com.github.zjor.ext.hibernate.ShortIdGenerator")
    @Column(name = "id", unique = true, length = 36)
    private String id;

}
