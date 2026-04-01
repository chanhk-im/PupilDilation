package com.chanhk.pupildilation.venue.domain;

import com.chanhk.pupildilation.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "venues")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Venue extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String address;
}
