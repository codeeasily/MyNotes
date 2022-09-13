package com.example.demo.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @date 2022/08/30 11:27
 */
//@Configuration
//@EnableBatchProcessing
public class JobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    /**
     * 程序执行的入口
     *
     * @return
     */
    @Bean
    public Job helloJob1() {
        return jobBuilderFactory.get("helloJob-1").start(step1())
                .build();
    }

    @Bean
    public Job helloJob2() {
        return jobBuilderFactory.get("helloJob-2").start(step1()).on("COMPLETED").to(step2())
                .from(step2()).end()
                .build();
    }

    @Bean
    public Job helloJob3() {
        return jobBuilderFactory.get("helloJob-3").start(step1()).on("COMPLETED").to(step2())
                .from(step2()).on("COMPLETED").to(step3())
                .from(step3()).end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").tasklet(
                (stepContribution, chunkContext) -> {
                    System.out.println("hello spring batch");
                    return RepeatStatus.FINISHED;
                }
        ).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2").tasklet(
                (stepContribution, chunkContext) -> {
                    System.out.println("hello spring batch step2");
                    return RepeatStatus.FINISHED;
                }
        ).build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3").tasklet(
                (stepContribution, chunkContext) -> {
                    System.out.println("hello spring batch step3");
                    return RepeatStatus.FINISHED;
                }
        ).build();
    }
}

