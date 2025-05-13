package com.kims_convenience.order_service.dto.order_event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentInstrumentDto {

    private String paymentInstrumentId;
    private String paymentMethodType;
    private String provider;
    private String maskedCardNumber;
    private String cardHolderName;
    private String expiryMonth;
    private String expiryYear;
    private String lastUsedOn;
    private String instrumentToken;

    public String toLogString() {
        return String.format("PaymentInstrument[paymentInstrumentId=%s, type=%s, provider=%s, maskedCard=%s, holder=%s, expiry=%s/%s, lastUsedOn=%s, token=%s]",
                paymentInstrumentId,
                paymentMethodType,
                provider,
                maskedCardNumber,
                cardHolderName,
                expiryMonth,
                expiryYear,
                lastUsedOn,
                instrumentToken != null ? instrumentToken.substring(0, Math.min(6, instrumentToken.length())) + "..." : "N/A"
        );
    }
}
