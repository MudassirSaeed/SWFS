package com.smallworld.service;

import com.smallworld.data.IDataReader;
import com.smallworld.exceptions.runtime.DataNotFoundException;
import com.smallworld.exceptions.runtime.InputOutputException;
import com.smallworld.model.Transaction;
import com.smallworld.util.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class TransactionDataFetcherService {
    private final IDataReader<Transaction> transactions;

    public TransactionDataFetcherService(IDataReader<Transaction> transactions) {
        this.transactions = transactions;
    }


    /**
     * Retrieves Transaction Data from File
     */
    
    public List<Transaction> getTransactionData() {
        try {
            List<Transaction> fetchedTransactions = this.transactions.getAlreadyFetchedData(Transaction[].class);
            if (Objects.isNull(fetchedTransactions)){
                throw new DataNotFoundException(ErrorCodeEnum.DATA_NOT_FOUND);
            }
            return fetchedTransactions;
        }
        catch (IOException e){
            log.error("Failed to read data from file: " + e.getMessage());
            throw new InputOutputException(ErrorCodeEnum.UNABLE_TO_FETCH_DATA);
        }

    }

    /**
     * Returns the Stream of all unique transactions on the basis of MTN
     */

    private Stream<Transaction> getUniqueTransactions() {
        Set<Integer> uniqueTransactions = new HashSet<>();

        return this.getTransactionData()
                .stream()
                .filter(transaction -> uniqueTransactions.add(transaction.getMtn()));
    }



    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount() {
        
        return this.getUniqueTransactions()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) {
        
        return this.getUniqueTransactions()
                .filter(t -> t.getSenderFullName().equals(senderFullName))
                .mapToDouble(Transaction::getAmount)
                .sum();

    }

    /**
     * Returns the highest transaction amount
     */

    public double getMaxTransactionAmount() {

        Optional<Double> highestAmount = getMaxTransactionAmounts();
        return highestAmount.orElse(0.0);
    }

    public Optional<Double> getMaxTransactionAmounts() {
        
        return this.getUniqueTransactions()
                .map(Transaction::getAmount)
                .max(Comparator.naturalOrder());

    }



    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients() {
        
        return this.getUniqueTransactions()
                .flatMap(transaction -> Stream.of(transaction.getSenderFullName(), transaction.getBeneficiaryFullName()))
                .collect(Collectors.toSet())
                .size();
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName) {
        
       return this.getTransactionData()
               .stream()
               .filter(transaction -> transaction.getSenderFullName().equals(clientFullName) || transaction.getBeneficiaryFullName().equals(clientFullName))
               .anyMatch(transaction -> Objects.nonNull(transaction.getIssueId()) && !transaction.isIssueSolved());
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, List<Transaction>> getTransactionsByBeneficiaryName() {
        
        return this.getUniqueTransactions()
                .collect(Collectors.groupingBy(Transaction::getBeneficiaryFullName));
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds() {
        
        return this.getTransactionData().stream()
                .filter(transaction -> transaction.getIssueId() != null && !transaction.isIssueSolved())
                .map(Transaction::getIssueId)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages() {
        
        return this.getTransactionData().stream()
                .filter(transaction -> transaction.getIssueId() != null && transaction.isIssueSolved())
                .map(Transaction::getIssueMessage)
                .collect(Collectors.toList());
    }

    /**
     * Returns the 3 transactions with highest amount sorted by amount descending
     */
    public List<Object> getTop3TransactionsByAmount() {
        
        return this.getUniqueTransactions()
                .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    /**
     * Returns the sender with the most total sent amount
     */
    public Optional<Object> getTopSender() {
        
        Map<String, Double> totalSentAmountBySender = this.getUniqueTransactions()
                .collect(Collectors.groupingBy(Transaction::getSenderFullName,
                        Collectors.summingDouble(Transaction::getAmount)));

        return Optional.ofNullable(totalSentAmountBySender.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null));
    }
}
