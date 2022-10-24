package pro.sky.coursework2.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.sky.coursework2.service.JavaQuestionService;
import pro.sky.coursework2.service.MathQuestionService;
import pro.sky.coursework2.service.QuestionService;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class QuestionServiceConfig {

    @Bean
    public Map<String, QuestionService> questionServiceMap(
            JavaQuestionService javaQuestionService,
            MathQuestionService mathQuestionService) {

        Map<String, QuestionService> serviceMap =new  HashMap<>();
        serviceMap.put("JavaQuestionService", javaQuestionService);
        serviceMap.put("MathQuestionService", mathQuestionService);

        return serviceMap;
    }
}
