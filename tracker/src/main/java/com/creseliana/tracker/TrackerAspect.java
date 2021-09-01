package com.creseliana.tracker;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Log4j2
@Aspect
public class TrackerAspect {
    private static final String MINUTES = "min";
    private static final String SECONDS = "s";
    private static final String MILLISECONDS = "ms";
    private static final int MILLISECONDS_IN_SECOND = 1000;
    private static final int SECONDS_IN_MINUTE = 60;

    @SneakyThrows
    @Around("@annotation(com.creseliana.tracker.annotation.Track)")
    public Object track(ProceedingJoinPoint joinPoint) {
        final StringBuilder builder = new StringBuilder();
        final long start = System.currentTimeMillis();
        final Object proceed = joinPoint.proceed();
        final long time = System.currentTimeMillis() - start;

        builder.append("\nCalled method: ")
                .append(joinPoint.getSignature().getName())
                .append("()\nWith arguments:\n");

        for (Object arg : joinPoint.getArgs()) {
            builder.append(arg).append("\n");
        }

        builder.append("Method returned: ")
                .append(proceed)
                .append("\nMethod completed in ")
                .append(convertMs(time))
                .append("\n");

        log.trace(builder);
        System.out.println(builder);
        return proceed;
    }

    private String convertMs(long ms) {
        long minutes, seconds, milliseconds;
        final StringBuilder builder = new StringBuilder();

        minutes = ms / MILLISECONDS_IN_SECOND / SECONDS_IN_MINUTE;
        seconds = ms / MILLISECONDS_IN_SECOND % SECONDS_IN_MINUTE;
        milliseconds = ms % MILLISECONDS_IN_SECOND;

        builder.append(ms).append(MILLISECONDS)
                .append("(")
                .append(minutes).append(MINUTES)
                .append(seconds).append(SECONDS)
                .append(milliseconds).append(MILLISECONDS)
                .append(")");
        return builder.toString();
    }
}
