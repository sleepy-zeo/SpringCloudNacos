package com.lullaby.condition.sub;

import com.lullaby.condition.judge.LinuxCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean("windows")
    public Person person1() {
        return new Person("Windows", 78);
    }

    @Bean("linux")
    public Person person2() {
        return new Person("Linux", 84);
    }

    @Bean(name = "notebookPC")
    public Computer computer1() {
        return new Computer("笔记本电脑");
    }

    @ConditionalOnBean(Computer.class)
    @Conditional(LinuxCondition.class)
    @Bean("reservePC")
    public Computer computer2() {
        return new Computer("备用电脑");
    }
}
