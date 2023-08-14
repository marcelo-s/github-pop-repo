package com.shopapotheke.githubpoprepo.adapter.out.rest.service;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@Slf4j
public class RestService {

    public <T> T executeRestCall(Supplier<T> serviceSupplier) {
        CircuitBreaker circuitBreaker = getCircuitBreaker();
        Retry retry = getRetry();

        Supplier<T> decoratedSupplier = Decorators.ofSupplier(serviceSupplier)
                                                  .withCircuitBreaker(circuitBreaker)
                                                  .withRetry(retry)
                                                  .decorate();

        return Try.ofSupplier(decoratedSupplier)
                  .get();
    }

    public Retry getRetry() {
        IntervalFunction exponentialBackoff = IntervalFunction.ofExponentialBackoff(1000, 2d);
        RetryConfig retryConfig = RetryConfig.custom()
                                             .maxAttempts(3)
                                             .intervalFunction(exponentialBackoff)
                                             .build();

        RetryRegistry registry = RetryRegistry.of(retryConfig);
        return registry.retry(RestService.class.getName() + "Retry", retryConfig);
    }

    public CircuitBreaker getCircuitBreaker() {
        CircuitBreakerConfig config = CircuitBreakerConfig
                .custom()
                .minimumNumberOfCalls(2)
                .slidingWindowSize(100)
                .failureRateThreshold(50)
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .build();
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        return registry.circuitBreaker(RestService.class.getName() + "CircuitBreaker");
    }
}
