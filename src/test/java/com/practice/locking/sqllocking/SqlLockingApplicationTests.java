package com.practice.locking.sqllocking;

import com.practice.locking.sqllocking.persistence.entity.Stock;
import com.practice.locking.sqllocking.persistence.repository.AuditRepository;
import com.practice.locking.sqllocking.persistence.repository.StockRepository;
import com.practice.locking.sqllocking.service.PurchaseOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SqlLockingApplicationTests {

	private static final String PRODUCT_ID = "id1";

	@Autowired private PurchaseOrder purchaseOrder;

	@Autowired private AuditRepository auditRepository;

	@Autowired private StockRepository stockRepository;

	private ExecutorService executorService = Executors.newFixedThreadPool(40);

	@BeforeEach
	public void beforeTest(){
		Stock stock = Stock.builder()
				.id(PRODUCT_ID)
				.count(400)
				.name("Samsung Galaxy S22")
				.version(0)
				.build();
		stockRepository.save(stock);
	}

	@AfterEach
	public void afterTest(){
		auditRepository.deleteAll();
		stockRepository.deleteAll();
	}

	@Test
	void test_failureScenario() {

		Runnable r = () -> purchaseOrder.purchase(PRODUCT_ID);
		List<Future<?>> list = new ArrayList<>();
		for(int i = 0; i<1000; i++){
			list.add(executorService.submit(r));
		}

		for (Future<?> future : list) {
			try {
				future.get();
			} catch (ExecutionException | InterruptedException e) {
				e.printStackTrace();
			}
		}

		long auditCount = auditRepository.count();
		Stock stock = stockRepository.getById(PRODUCT_ID);
		Assertions.assertNotEquals(0, stock.getCount());
		Assertions.assertEquals(1000, auditCount);
	}

	@Test
	void test_OptimisticLockingScenario() {

		Runnable r = () -> purchaseOrder.purchaseV1(PRODUCT_ID);
		List<Future<?>> list = new ArrayList<>();
		for(int i = 0; i<1000; i++){
			list.add(executorService.submit(r));
		}

		for (Future<?> future : list) {
			try {
				future.get();
			} catch (ExecutionException | InterruptedException e) {
				e.printStackTrace();
			}
		}

		long auditCount = auditRepository.count();
		Stock stock = stockRepository.getById(PRODUCT_ID);
		Assertions.assertEquals(auditCount, stock.getVersion());
	}


	@Test
	void test_PessimisticLockingScenario() {

		Runnable r = () -> purchaseOrder.purchaseV2(PRODUCT_ID);
		List<Future<?>> list = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			list.add(executorService.submit(r));
		}

		for (Future<?> future : list) {
			try {
				future.get();
			} catch (ExecutionException | InterruptedException e) {
				e.printStackTrace();
			}
		}

		long count = auditRepository.count();
		Stock stock = stockRepository.getById("id1");
		Assertions.assertEquals(400, count);
		Assertions.assertEquals(0, stock.getCount());
		Assertions.assertEquals(400, stock.getVersion());
	}

}