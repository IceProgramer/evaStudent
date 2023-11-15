package com.itmo.evastudent.constant;

import com.itmo.evastudent.model.entity.Evaluation;
import com.itmo.evastudent.model.entity.Student;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2023/9/12 20:09
 */         
public class EvaluationContent {

    private static final ThreadLocal<Evaluation> evaluationStore = new ThreadLocal<>();

    public static void set(Evaluation evaluation) {
        evaluationStore.set(evaluation);
    }

    public static Evaluation get() {
        return evaluationStore.get();
    }

    public static void clear() {
        evaluationStore.remove();
    }

}
