package com.movieticketbooking.userservice.request;

import java.util.UUID;

public record ConfirmationRequest(String loginId, String bookingID) {
}
