package com.example.mediumproject.domain.membership.service;

public class MembershipService {
    public void checkAmount(String orderId, String amount) {
        // 해당 주문번호의 최종 결제금액이 실제 amount와 일치하는지 로직
        boolean matched = true;

        if (!matched) throw new RuntimeException("결제금액이 일치하지 않습니다.");
    }

    public void setPaymentComplete(String orderId) {
    }
}
