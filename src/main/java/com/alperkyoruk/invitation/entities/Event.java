package com.alperkyoruk.invitation.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "expire_date")
    private Date expireDate;

    @Column(name = "event_date")
    private Date eventDate;

    @Column(name = "event_url")
    private String eventUrl;

    @Column(name = "max_guests_per_person")
    private int maxGuestsPerPerson;

    @Column(name = "max_guests")
    private int maxGuests;

    @Column(name = "total_guests")
    private int totalGuests;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Guest> guests;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;



}
