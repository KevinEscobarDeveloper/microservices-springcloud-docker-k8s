package com.dailycodebuffer.PaymentService.service;

import com.dailycodebuffer.PaymentService.entity.TransactionDetails;
import com.dailycodebuffer.PaymentService.model.PaymentMode;
import com.dailycodebuffer.PaymentService.model.PaymentRequest;
import com.dailycodebuffer.PaymentService.model.PaymentResponse;
import com.dailycodebuffer.PaymentService.repository.TransactionDetailRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements  PaymentService{

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;
    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording Payment Details: {} ", paymentRequest);

        TransactionDetails transactionDetails = TransactionDetails.builder().paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS").orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber()).amount(paymentRequest.getAmount())
                .build();

        transactionDetailRepository.save(transactionDetails);
        log.info("Transaction completed with Id: {} ", paymentRequest.getOrderId());

        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(String orderId) {
        log.info("Getting payment details for the Order Id: {} ", orderId);
        TransactionDetails transactionDetails = transactionDetailRepository.findByOrderId(Long.parseLong(orderId));

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentId(transactionDetails.getId()).paymentDate(transactionDetails.getPaymentDate())
                .orderId(transactionDetails.getOrderId()).paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                .paymentDate(transactionDetails.getPaymentDate()).status(transactionDetails.getPaymentStatus()).amount(transactionDetails.getAmount())
                .build();
        return paymentResponse;
    }
}
