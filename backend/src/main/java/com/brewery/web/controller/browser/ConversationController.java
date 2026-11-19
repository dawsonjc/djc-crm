package com.brewery.web.controller.browser;

import com.brewery.web.dto.ConversationDTO;
import com.brewery.web.model.Conversation;
import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.model.message.Message;
import com.brewery.web.model.User;
import com.brewery.web.services.ConversationService;
import com.brewery.web.services.MessagesService;
import com.brewery.web.services.UserTableService;
import com.brewery.web.user.SessionUser;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class ConversationController {
    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MessagesService messagesService;

    @Autowired
    private UserTableService userService;

    @GetMapping(value = { "/conversations" })
    public String doGet(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam(value = "conversationId") UUID conversationId
    ) {
        SessionUser user = (SessionUser) request.getSession(false).getAttribute(SessionUser.SESSION_USER);
        List<Message> messages = this.messagesService.getMessagesByConversationId(conversationId);

        ConversationDTO globalChat = this.conversationService.getGlobalChat();

        if(!conversationId.equals(globalChat.conversationId())) {
            ConversationDTO convo = this.conversationService.getConversationById(conversationId);

            if(convo == null || !convo.users().contains(user.userId())) {
                return "redirect:/conversations?conversationId=" + globalChat.conversationId();
            }
        }
        //ArrayList<ConversationDTO> channels = new ArrayList<ConversationDTO>(users);

        request.setAttribute("Messages", messages);
        // request.setAttribute("Channels", channels);

        return "chat";
    }

    @PostMapping(path = { "/add-friend" })
    @ResponseBody
    public ResponseEntity<ObjectNode> addFriend(HttpServletRequest request, @RequestParam(value = "userId") UUID potentialFriendId) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();
        responseJson.put("success", false);
        responseJson.put("message", "Not Implemented");
        responseJson.putObject("data");

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(responseJson);
    }

    @GetMapping(value = { "/test" } )
    public String test() {
        return "test";
    }
}
