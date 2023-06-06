package com.studio.music.song.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: BinBin
 * @Date: 2023/03/28/14:49
 * @Description:
 */
@Configuration
public class RabbitmqConfig {

    public static final String QUEUE_INFORM_SAVE_MESSAGES = "queue_inform_save_messages";
    public static final String ROUTING_KEY_SAVE_MESSAGES = "inform.#.save_messages.#";
    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    public static final String ROUTING_KEY_EMAIL="inform.#.email.#";
    public static final String EXCHANGE_TOPICS_INFORM = "exchange_topics_inform";

    //声明交换机
    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange EXCHANGE_TOPICS_INFORM(){
        //durable(true) 持久化，mq重启之后交换机还在
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }

    @Bean(QUEUE_INFORM_SAVE_MESSAGES)
    public Queue QUEUE_INFORM_WAREHOUSE(){
        return new Queue(QUEUE_INFORM_SAVE_MESSAGES);
    }

    @Bean
    public Binding BINGING_ROUTING_KEY_WAREHOUSE(@Qualifier(QUEUE_INFORM_SAVE_MESSAGES) Queue queue,
                                                 @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_SAVE_MESSAGES).noargs();
    }

    @Bean(QUEUE_INFORM_EMAIL)
    public Queue QUEUE_INFORM_EMAIL(){
        return new Queue(QUEUE_INFORM_EMAIL);
    }

    @Bean
    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier(QUEUE_INFORM_EMAIL) Queue queue,
                                              @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_EMAIL).noargs();
    }

    @Bean
    public MessageConverter getMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

}
