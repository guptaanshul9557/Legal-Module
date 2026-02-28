package org.egov.legal.kafka;

import org.egov.legal.event.CaseFiledEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CaseEventProducer {

	@Value("${kafka.topic.case-filing}")
    private String caseFilingTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CaseEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCaseFiled(CaseFiledEvent event) {
        kafkaTemplate.send(
        		caseFilingTopic,
                event.diaryNumber(), // key for partitioning
                event
        );
    }
}
