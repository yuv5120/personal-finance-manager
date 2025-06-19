package com.yuvraj.pfm.service;

import com.yuvraj.pfm.dto.BudgetRequest;
import com.yuvraj.pfm.dto.BudgetResponse;
import com.yuvraj.pfm.model.Budget;
import com.yuvraj.pfm.model.Transaction;
import com.yuvraj.pfm.model.TransactionType;
import com.yuvraj.pfm.model.User;
import com.yuvraj.pfm.repository.BudgetRepository;
import com.yuvraj.pfm.repository.TransactionRepository;
import com.yuvraj.pfm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    private User getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public BudgetResponse createOrUpdate(BudgetRequest request) {
        User user = getLoggedInUser();

        Budget budget = budgetRepository.findByUserAndCategory(user, request.getCategory())
                .orElse(Budget.builder()
                        .category(request.getCategory())
                        .user(user)
                        .build());

        budget.setLimitAmount(request.getLimitAmount());
        budgetRepository.save(budget);

        return buildResponse(budget);
    }

    public List<BudgetResponse> getAll() {
        User user = getLoggedInUser();
        return budgetRepository.findByUser(user)
                .stream()
                .map(this::buildResponse)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        User user = getLoggedInUser();
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        if (!budget.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        budgetRepository.delete(budget);
    }

    private BudgetResponse buildResponse(Budget budget) {
        List<Transaction> transactions = transactionRepository.findByUserOrderByDateDesc(budget.getUser());

        double spent = transactions.stream()
                .filter(tx -> tx.getType() == TransactionType.EXPENSE && tx.getCategory().equalsIgnoreCase(budget.getCategory()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        return new BudgetResponse(
                budget.getId(),
                budget.getCategory(),
                budget.getLimitAmount(),
                spent,
                spent > budget.getLimitAmount()
        );
    }
}
