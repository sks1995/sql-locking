# SqlLockingApplication
This is a small project to understand the role of optimistic and pessimistic locking in dealing with highly concurrent systems.

## Medium Article [Link](https://medium.com) 
It explains the Concurrency Control With Optimistic and Pessimistic Locking

## How to setup ?
Run mvn clean install -s settings.xml 

## Things to look for
1. [PurchaseOrder.java](https://github.com/sks1995/sql-locking/blob/master/src/main/java/com/practice/locking/sqllocking/service/PurchaseOrder.java) class has the logic for optimistic and pessimistic locking
2. [SqlLockingApplicationTests.java](https://github.com/sks1995/sql-locking/blob/master/src/test/java/com/practice/locking/sqllocking/SqlLockingApplicationTests.java) contains all the testcases.
    1. `test_failureScenario ` demonstrates The Lost Update anomaly.
    2. `test_OptimisticLockingScenario` demonstrates the solution using optimistic locking.
    3. `test_PessimisticLockingScenario` demonstrates the solution using pessimistic locking.
    
3. [StockRepository.java](https://github.com/sks1995/sql-locking/blob/master/src/main/java/com/practice/locking/sqllocking/persistence/repository/StockRepository.java) has different update statements fired for various locking scenarios.