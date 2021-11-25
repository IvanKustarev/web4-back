//package web4.back.webSocket;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//
//@Controller
//public class ChatController {
//
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//
//    @CrossOrigin
//    @MessageMapping("/chat")
//    public void processMessage() {
//
//        messagingTemplate.convertAndSendToUser("1", "/queue/messages","Win");
//    }
//}