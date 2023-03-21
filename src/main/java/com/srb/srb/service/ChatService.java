package com.srb.srb.service;

import com.srb.srb.domain.dto.ChatRoomDto;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
public class ChatService {
    private Map<String, ChatRoomDto> chatRoomDtoMap;

    @PostConstruct
    private void init() {
        chatRoomDtoMap= new LinkedHashMap<>();
    }

    /**
     * 전체 채팅방 조회(채팅방 생성 순서를 최근순서로 반환)
     * @return
     */
    public List<ChatRoomDto> findAllRoom() {
        List chatRooms = new ArrayList(chatRoomDtoMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    /**
     * roomId 기준으로 채팅방 찾기
     * @param roomId
     * @return
     */
    public ChatRoomDto findRoomById(String roomId) {
        return chatRoomDtoMap.get(roomId);
    }

    /**
     * roomName 기준 채팅방 생성
     * @param roomName
     * @return
     */
    public ChatRoomDto createChatRoom(String roomName) {
        ChatRoomDto chatRoomDto = new ChatRoomDto().create(roomName);
        chatRoomDtoMap.put(chatRoomDto.getRoomId(), chatRoomDto);

        return chatRoomDto;
    }

    /**
     * 채팅방 인원 +1
     * @param roomId
     */
    public void plusUserCnt(String roomId) {
        ChatRoomDto roomDto = chatRoomDtoMap.get(roomId);
        roomDto.setUserCount(roomDto.getUserCount() + 1);
    }

    /**
     * 채팅방 인원 -1
     * @param roomId
     */
    public void minusUserCnt(String roomId) {
        ChatRoomDto roomDto = chatRoomDtoMap.get(roomId);
        roomDto.setUserCount(roomDto.getUserCount() - 1);
    }

    /**
     * 채팅방 유저리스트에 유저 추가
     * @param roomId
     * @param username
     * @return
     */
    public String addUser(String roomId, String username) {
        ChatRoomDto roomDto = chatRoomDtoMap.get(roomId);
        String userUUID = UUID.randomUUID().toString();

        roomDto.getUserList().put(userUUID, username);
        return userUUID;
    }

    /**
     * 채팅방 유저 이름 중복 확인
     * @param roomId
     * @param username
     * @return
     */
    public String isDuplicateName(String roomId, String username) {
        ChatRoomDto roomDto = chatRoomDtoMap.get(roomId);
        String tmp = username;

        while (roomDto.getUserList().containsValue(tmp)) {
            int ranNum = (int) (Math.random() * 100) + 1;
            tmp = username + ranNum;
        }
        return tmp;
    }

    /**
     * 채팅방 유저 리스트 삭제
     * @param roomId
     * @param userUUID
     */
    public void delUser(String roomId, String userUUID) {
        ChatRoomDto room = chatRoomDtoMap.get(roomId);
        room.getUserList().remove(userUUID);
    }

    /**
     * 채팅방 userName 조회
     * @param roomId
     * @param userUUID
     * @return
     */
    public String getUserName(String roomId, String userUUID) {
        ChatRoomDto room = chatRoomDtoMap.get(roomId);
        return room.getUserList().get(userUUID);
    }

    /**
     * 채팅방 전체 userList 조회
     * @param roomId
     * @return
     */
    public List<String> getUserList(String roomId) {
        List<String> list = new ArrayList<>();

        ChatRoomDto room = chatRoomDtoMap.get(roomId);

        room.getUserList().forEach((key, value) -> list.add(value));
        return list;
    }
}
