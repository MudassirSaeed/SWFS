package com.smallworld;

import com.smallworld.data.IDataReader;
import com.smallworld.model.Transaction;
import com.smallworld.service.TransactionDataFetcherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CodingTestApplicationTests {

	@InjectMocks
	TransactionDataFetcherService transactionDataFetcherService;
	@Mock
	IDataReader<Transaction> iDataReader;

	List<Transaction> mockTransactions;

	@BeforeEach
	public void setUp() {

		Transaction transaction1 = new Transaction(663458, 430.2, "Tom Shelby",
				22, "Alfie Solomons", 33, 1, false,
				"Looks like money laundering");

		Transaction transaction2 = new Transaction(1284564, 150.2, "Tom Shelby",
				22, "Arthur Shelby", 60, 2, true,
				"Never gonna give you up");
		Transaction transaction3 = new Transaction(1284564, 150.2, "Tom Shelby",
				22, "Arthur Shelby", 60, 3, false,
				"Looks like money laundering");
		Transaction transaction4 = new Transaction(96132456, 67.8, "Aunt Polly",
				34, "Aberama Gold", 58, null, true, null);
		Transaction transaction5 = new Transaction(5465465, 985.0,
				"Arthur Shelby", 60, "Ben Younger", 47, 15,
				false, "Something's fishy");
		Transaction transaction6 = new Transaction(1651665, 97.66, "Tom Shelby",
				22, "Oswald Mosley", 37, 65, true,
				"Never gonna let you down");
		Transaction transaction7 = new Transaction(6516461, 33.22, "Aunt Polly",
				34, "MacTavern", 30, null, true, null);
		Transaction transaction8 = new Transaction(32612651, 666.0,
				"Grace Burgess", 31, "Michael Gray", 58, 54,
				false, "Something ain't right");
		Transaction transaction9 = new Transaction(32612651, 666.0,
				"Grace Burgess", 31, "Michael Gray", 58, 78,
				true, "Never gonna run around and desert you");
		Transaction transaction10 = new Transaction(32612651, 666.0,
				"Grace Burgess", 31, "Michael Gray", 58, 99,
				false, "Don't let this transaction happen");
		Transaction transaction11 = new Transaction(36448252, 154.15,
				"Billy Kimber", 58, "Winston Churchill",
				48, null, true, null);
		Transaction transaction12 = new Transaction(645645111, 215.17,
				"Billy Kimber", 58, "Major Campbell", 41, null,
				true, null);
		Transaction transaction13 = new Transaction(45431585, 89.77,
				"Billy Kimber", 58, "Luca Changretta", 46,
				null, true, null);

		mockTransactions = List.of(transaction1, transaction2, transaction3, transaction4, transaction5, transaction6,
				transaction7, transaction8, transaction9, transaction10, transaction11, transaction12, transaction13);
	}

	@Test
	void test_getTotalTransactionAmount(){
		Mockito.when(transactionDataFetcherService.getTransactionData()).thenReturn(mockTransactions);
		double totalAmount = transactionDataFetcherService.getTotalTransactionAmount();
		Assertions.assertEquals(totalAmount,2889.17);

	}
	@Test
	void test_getTotalTransactionAmountSentBy(){
		Mockito.when(transactionDataFetcherService.getTransactionData()).thenReturn(mockTransactions);
		double totalAmount = transactionDataFetcherService.getTotalTransactionAmountSentBy("Tom Shelby");
		Assertions.assertEquals(totalAmount,678.06);

	}

	@Test
	void test_getMaxTransactionAmount(){
		Mockito.when(transactionDataFetcherService.getTransactionData()).thenReturn(mockTransactions);
		double totalAmount = transactionDataFetcherService.getMaxTransactionAmount();
		Assertions.assertEquals(totalAmount,985.0);

	}

	@Test
	void test_countUniqueClients(){
		Mockito.when(transactionDataFetcherService.getTransactionData()).thenReturn(mockTransactions);
		double totalAmount = transactionDataFetcherService.countUniqueClients();
		Assertions.assertEquals(totalAmount,14);

	}

	@Test
	void test_hasOpenComplianceIssues(){
		Mockito.when(transactionDataFetcherService.getTransactionData()).thenReturn(mockTransactions);
		boolean isNotComplied = transactionDataFetcherService.hasOpenComplianceIssues("Tom Shelby");
		Assertions.assertTrue(isNotComplied);

	}

	@Test
	void test_getTransactionsByBeneficiaryName(){
		Mockito.when(transactionDataFetcherService.getTransactionData()).thenReturn(mockTransactions);
		Map<String, List<Transaction>> transactionsByClient = transactionDataFetcherService.getTransactionsByBeneficiaryName();
		Assertions.assertEquals(transactionsByClient.size(), 10);
	}

	@Test
	void test_getUnsolvedIssueIds(){
		Mockito.when(transactionDataFetcherService.getTransactionData()).thenReturn(mockTransactions);
		Set<Integer> transactionsByClient = transactionDataFetcherService.getUnsolvedIssueIds();
		Assertions.assertEquals(transactionsByClient.size(), 5);

	}

	@Test
	void test_getAllSolvedIssueMessages(){
		Mockito.when(transactionDataFetcherService.getTransactionData()).thenReturn(mockTransactions);
		List<String> transactionsByClient = transactionDataFetcherService.getAllSolvedIssueMessages();
		Assertions.assertEquals(transactionsByClient.size(), 3);

	}

	@Test
	void test_getTop3TransactionsByAmount(){
		Mockito.when(transactionDataFetcherService.getTransactionData()).thenReturn(mockTransactions);
		List<Object> transactionsByClient = transactionDataFetcherService.getTop3TransactionsByAmount();
		Assertions.assertEquals(transactionsByClient.size(), 3);
		Transaction transaction1 = (Transaction)transactionsByClient.get(0);
		Transaction transaction2 = (Transaction)transactionsByClient.get(1);
		Transaction transaction3 = (Transaction)transactionsByClient.get(2);
		Assertions.assertEquals(transaction1.getAmount(), 985.0);
		Assertions.assertEquals(transaction2.getAmount(), 666.0);
		Assertions.assertEquals(transaction3.getAmount(), 430.2);

	}

	@Test
	void test_getTopSender(){
		Mockito.when(transactionDataFetcherService.getTransactionData()).thenReturn(mockTransactions);
		Optional<Object> transactionsByClient = transactionDataFetcherService.getTopSender();
		Assertions.assertNotNull(transactionsByClient);
		String senderName = (String) transactionsByClient.get();
		Assertions.assertEquals(senderName, "Arthur Shelby");
	}


}
