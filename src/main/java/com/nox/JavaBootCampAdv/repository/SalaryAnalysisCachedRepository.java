package com.nox.JavaBootCampAdv.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SalaryAnalysisCachedRepository {
    private final RedisTemplate<String, String> redisTemplate;

    private static String getCacheKey(Integer year, Month month) {
        return "SalaryPayment:%s:%s".formatted(year, month);
    }

    public Optional<String> findByYearAndMonth(Integer year, Month month) {
        String cacheKey = getCacheKey(year, month);
        String analysisResult = redisTemplate.opsForValue().get(cacheKey);
        if (analysisResult == null) {
            return Optional.empty();
        } else {
            log.info("Results of the very long analysis for year = {} and month = {} found in cache", year, month);
            return Optional.of(analysisResult);
        }
    }

    public void cacheAnalysisResult(Integer year, Month month, String analysisResult) {
        String cacheKey = getCacheKey(year, month);
        redisTemplate.opsForValue().set(cacheKey, analysisResult);
    }

    public void invalidateCache(Integer year, Month month) {
        String cacheKey = getCacheKey(year, month);
        redisTemplate.delete(cacheKey);
        String yearKey = getCacheKey(year, null);
        redisTemplate.delete(yearKey);
    }
}
