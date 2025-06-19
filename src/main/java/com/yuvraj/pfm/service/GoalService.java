package com.yuvraj.pfm.service;

import com.yuvraj.pfm.dto.GoalRequest;
import com.yuvraj.pfm.dto.GoalResponse;
import com.yuvraj.pfm.model.Goal;
import com.yuvraj.pfm.model.Transaction;
import com.yuvraj.pfm.model.TransactionType;
import com.yuvraj.pfm.model.User;
import com.yuvraj.pfm.repository.GoalRepository;
import com.yuvraj.pfm.repository.TransactionRepository;
import com.yuvraj.pfm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    private User getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public GoalResponse create(GoalRequest request) {
        User user = getLoggedInUser();

        Goal goal = Goal.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .targetAmount(request.getTargetAmount())
                .deadline(request.getDeadline())
                .user(user)
                .build();

        goalRepository.save(goal);

        return toDto(goal, user);
    }

    public List<GoalResponse> getAll() {
        User user = getLoggedInUser();
        return goalRepository.findByUser(user)
                .stream().map(goal -> toDto(goal, user))
                .collect(Collectors.toList());
    }

    public GoalResponse update(Long id, GoalRequest request) {
        User user = getLoggedInUser();
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        if (!goal.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        goal.setTitle(request.getTitle());
        goal.setDescription(request.getDescription());
        goal.setTargetAmount(request.getTargetAmount());
        goal.setDeadline(request.getDeadline());

        goalRepository.save(goal);
        return toDto(goal, user);
    }

    public void delete(Long id) {
        User user = getLoggedInUser();
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        if (!goal.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        goalRepository.delete(goal);
    }

    private GoalResponse toDto(Goal goal, User user) {
        List<Transaction> transactions = transactionRepository.findByUserOrderByDateDesc(user);

        double income = transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double expense = transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double balance = income - expense;
        double progress = Math.min(100.0, (balance / goal.getTargetAmount()) * 100);

        return GoalResponse.builder()
                .id(goal.getId())
                .title(goal.getTitle())
                .description(goal.getDescription())
                .targetAmount(goal.getTargetAmount())
                .deadline(goal.getDeadline())
                .progress(progress)
                .build();
    }
}
