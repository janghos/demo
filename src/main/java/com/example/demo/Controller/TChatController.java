package com.example.demo.Controller;

import com.example.demo.Service.StoreService;
import com.example.demo.Service.TChatService;
import com.example.demo.VO.Store;
import com.example.demo.VO.TChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/tchat")
public class TChatController {
    @Autowired
    private TChatService tChatService;

    @GetMapping("") // 전체 조회
    public List<TChat> getAll() {
        return tChatService.getAll();
    }
    @GetMapping("/{tchatid}")
    public List<TChat> getById(@PathVariable("tchatid") int tchatid) {
        return tChatService.getId(tchatid);
    }

    @PostMapping("/{tchatid}") // 추가
    public TChat post(@RequestBody TChat tchat, @PathVariable("tchatid") int tchatid) {
        tChatService.insert(tchat,tchatid);
        return tchat;
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        tChatService.delete(id);
        return id + "번 제품이 삭제되었습니다";
    }
}
