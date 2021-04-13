package com.sleepy.amqp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApApplication {

    public static void main(String[] args) {
        ITrain iRun = new TrainImpl2();
        ITrain iRun1 = new TrainImpl();
        SpringApplication.run(ApApplication.class, args);
    }

    public interface ITrain {
        void walk();
        void run();
    }

    public static abstract class BaseTrain implements ITrain {
        @Override
        public void walk() {
        }

        @Override
        public void run() {
        }
    }

    public static class TrainImpl extends BaseTrain implements ITrain {

    }

    public static class TrainImpl2 extends BaseTrain {

    }
}
