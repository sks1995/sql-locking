package com.practice.locking.sqllocking.persistence.exceptions;

public class OptimisticLockingException extends RuntimeException{

  public OptimisticLockingException(String message){
    super(message);
  }
}
