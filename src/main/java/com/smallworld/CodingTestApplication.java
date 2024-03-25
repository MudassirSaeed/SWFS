package com.smallworld;

import com.smallworld.data.JsonDataReader;
import com.smallworld.model.Transaction;
import com.smallworld.service.TransactionDataFetcherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication()
public class CodingTestApplication {

	public static void main(String[] args) {

		SpringApplication.run(CodingTestApplication.class, args);
		try {
			TransactionDataFetcherService transactionDataFetcherService = new TransactionDataFetcherService(new JsonDataReader<>("transactions.json"));

			System.out.println("Total Transaction Amount: " + transactionDataFetcherService.getTotalTransactionAmount());

			System.out.println("The sum of the amounts of all transactions sent by Aunt Polly" + ": " + transactionDataFetcherService.getTotalTransactionAmountSentBy("Aunt Polly"));

			System.out.println("Highest transaction amount: " + transactionDataFetcherService.getMaxTransactionAmount());

			System.out.println(" The number of unique clients that sent or received a transaction: " + transactionDataFetcherService.countUniqueClients());

			System.out.println("Client that has at least one transaction with a open compliance issue: " + transactionDataFetcherService.hasOpenComplianceIssues("Tom Shelby"));

			System.out.println("All transactions indexed by beneficiary name : " + transactionDataFetcherService.getTransactionsByBeneficiaryName());

			System.out.println("Id's of all open compliance issues: " + transactionDataFetcherService.getUnsolvedIssueIds());

			System.out.println("All Solved Issue Messages: " + transactionDataFetcherService.getAllSolvedIssueMessages());

			System.out.println("3 transactions with highest amount sorted by amount descending : ");
			List<Object> transactionsByClient = transactionDataFetcherService.getTop3TransactionsByAmount();
			int count = 1;
			for (Object transaction :  transactionsByClient) {
				System.out.println("Transaction " + count++ + ": " + ((Transaction) transaction).getAmount());
			}

			System.out.println("Sender with the most total sent amount : " + transactionDataFetcherService.getTopSender());
		} catch (Exception e) {
			System.err.println("Error loading data from JSON file: " + e.getMessage());
		}
	}

}
