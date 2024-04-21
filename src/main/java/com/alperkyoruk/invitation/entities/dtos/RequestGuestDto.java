package com.alperkyoruk.invitation.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestGuestDto {

        private int id;

        private String fullName;

        private String guestId;

        private boolean isAttending;

        private int guestCount;

        private int eventId;

}
