package com.yuvraj.pfm.service;

import com.yuvraj.pfm.dto.TransactionRequest;
import com.yuvraj.pfm.dto.TransactionResponse;
import com.yuvraj.pfm.model.Transaction;
import com.yuvraj.pfm.model.TransactionType;
import com.yuvraj.pfm.model.User;
import com.yuvraj.pfm.repository.TransactionRepository;
import com.yuvraj.pfm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    private User getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public TransactionResponse create(TransactionRequest request) {
        User user = getLoggedInUser();

        Transaction transaction = Transaction.builder()
                .amount(request.getAmount())
                .date(request.getDate())
                .category(request.getCategory())
                .description(request.getDescription())
                .type(TransactionType.valueOf(request.getType().toUpperCase()))
                .user(user)
                .build();

        transactionRepository.save(transaction);

        return toDto(transaction);
    }

    public List<TransactionResponse> getAll() {
        User user = getLoggedInUser();
        return transactionRepository.findByUserOrderByDateDesc(user)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public TransactionResponse update(Long id, TransactionRequest request) {
        User user = getLoggedInUser();
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        transaction.setAmount(request.getAmount());
        transaction.setCategory(request.getCategory());
        transaction.setDescription(request.getDescription());
        transaction.setType(TransactionType.valueOf(request.getType().toUpperCase()));

        transactionRepository.save(transaction);

        return toDto(transaction);
    }

    public void delete(Long id) {
        User user = getLoggedInUser();
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        transactionRepository.delete(transaction);
    }

    private TransactionResponse toDto(Transaction tx) {
        return TransactionResponse.builder()
                .id(tx.getId())
                .amount(tx.getAmount())
                .date(tx.getDate())
                .category(tx.getCategory())
                .description(tx.getDescription())
                .type(tx.getType().name())
                .build();
    }
}
