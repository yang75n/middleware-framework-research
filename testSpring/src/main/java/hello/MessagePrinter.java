package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagePrinter {

    final private MessageService service;

    @Autowired
    public MessagePrinter(MessageService service) {
        System.out.println("MessagePrient construct");
        this.service = service;
    }

    public void printMessage() {
        System.out.println(this.service.getMessage());
    }
}