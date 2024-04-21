package com.alperkyoruk.invitation.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestEventDto {

    private int id;

    private String name;

    private Date eventDate;

    private Date expireDate;

    private int maxGuests;

    private int maxGuestsPerPerson;

    private int userId;

}
