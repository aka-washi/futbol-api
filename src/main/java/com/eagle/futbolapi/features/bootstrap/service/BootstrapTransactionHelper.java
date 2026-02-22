package com.eagle.futbolapi.features.bootstrap.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Helper component to execute bootstrap operations in isolated transactions.
 * This prevents one failed entity from rolling back the entire batch.
 */
@Component
public class BootstrapTransactionHelper {

  /**
   * Create an entity in a new transaction, isolated from other operations.
   * Each call starts a completely new transaction, so failures don't affect other entities.
   *
   * @param dto The DTO to create
   * @param createFunction The service creation function
   * @param <D> The DTO type
   * @return The created entity
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public <D> Object createEntityInNewTransaction(D dto, java.util.function.Function<D, ?> createFunction) {
    return createFunction.apply(dto);
  }
}
