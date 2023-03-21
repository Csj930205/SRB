package com.srb.srb.controller;

import com.srb.srb.domain.dto.ChatRoomDto;
import com.srb.srb.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatService chatService;

    /**
     * 채팅리스트 화면 출력
     * @return
     */
    @GetMapping("/")
    public Map<String, Object> goChatRoom() {
        List<ChatRoomDto> chatRoomList = chatService.findAllRoom();

        Map<String, Object> result = new HashMap<>();

        result.put("result", "success");
        result.put("code", HttpStatus.OK.value());
        result.put("chatRoomList", chatRoomList);

        return result;


    }

    /**
     * 채팅방 생성
     * @param name
     * @return
     */
    @PostMapping("createroom")
    public Map<String, Object> createRoom(@RequestParam String name) {
        ChatRoomDto room = chatService.createChatRoom(name);

        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        result.put("code", HttpStatus.OK.value());
        result.put("message", "채팅방이 생성되었습니다.");

        return result;
    }

    /**
     * 채팅방 입장화면
     * @param roomId
     * @return
     */
    @GetMapping("room")
    public Map<String, Object> roomDetail(String roomId) {
        ChatRoomDto roomDetail = chatService.findRoomById(roomId);

        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        result.put("code", HttpStatus.OK.value());
        result.put("roomDetail", roomDetail);
        return result;
    }
}
