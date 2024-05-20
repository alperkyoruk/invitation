package com.alperkyoruk.invitation.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "guest_id")
    private String guestId;

    @Column(name = "is_attending")
    private boolean isAttending;

    @Column(name = "guest_count")
    private int guestCount;

    @ManyToOne(targetEntity = Event.class, cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @JsonIgnore
    private Event event;

    @Column(columnDefinition = "bytea")
    private byte[] qrCode;

}
