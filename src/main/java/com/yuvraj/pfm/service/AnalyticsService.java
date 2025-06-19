package com.yuvraj.pfm.service;

import com.yuvraj.pfm.dto.CategoryStatsResponse;
import com.yuvraj.pfm.dto.MonthlyStatsResponse;
import com.yuvraj.pfm.dto.SummaryResponse;
import com.yuvraj.pfm.model.Transaction;
import com.yuvraj.pfm.model.TransactionType;
import com.yuvraj.pfm.model.User;
import com.yuvraj.pfm.repository.TransactionRepository;
import com.yuvraj.pfm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.format.TextStyle;
import java.util.*;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    private User getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public SummaryResponse getSummary() {
        User user = getLoggedInUser();
        List<Transaction> transactions = transactionRepository.findByUserOrderByDateDesc(user);

        double income = transactions.stream()
                .filter(tx -> tx.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double expense = transactions.stream()
                .filter(tx -> tx.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();

        return new SummaryResponse(income, expense, income - expense);
    }

    public List<MonthlyStatsResponse> getMonthlyStats() {
        User user = getLoggedInUser();
        List<Transaction> transactions = transactionRepository.findByUserOrderByDateDesc(user);

        Map<String, Double> incomeMap = new HashMap<>();
        Map<String, Double> expenseMap = new HashMap<>();

        for (Transaction tx : transactions) {
            String month = tx.getDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            if (tx.getType() == TransactionType.INCOME) {
                incomeMap.put(month, incomeMap.getOrDefault(month, 0.0) + tx.getAmount());
            } else {
                expenseMap.put(month, expenseMap.getOrDefault(month, 0.0) + tx.getAmount());
            }
        }

        Set<String> months = new HashSet<>();
        months.addAll(incomeMap.keySet());
        months.addAll(expenseMap.keySet());

        return months.stream()
                .map(m -> new MonthlyStatsResponse(
                        m,
                        incomeMap.getOrDefault(m, 0.0),
                        expenseMap.getOrDefault(m, 0.0)))
                .sorted(Comparator.comparingInt(m -> Month.valueOf(m.getMonth().toUpperCase()).getValue()))
                .collect(Collectors.toList());
    }

    public List<CategoryStatsResponse> getCategoryStats() {
        User user = getLoggedInUser();
        List<Transaction> transactions = transactionRepository.findByUserOrderByDateDesc(user);

        return transactions.stream()
                .filter(tx -> tx.getType() == TransactionType.EXPENSE)
                .collect(Collectors.groupingBy(Transaction::getCategory, Collectors.summingDouble(Transaction::getAmount)))
                .entrySet().stream()
                .map(e -> new CategoryStatsResponse(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }
}
