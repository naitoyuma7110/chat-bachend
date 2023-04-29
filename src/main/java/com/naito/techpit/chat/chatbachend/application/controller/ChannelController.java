package com.naito.techpit.chat.chatbachend.application.controller;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.naito.techpit.chat.chatbachend.application.service.ChannelService;
import com.naito.techpit.chat.chatbachend.domain.channels.model.Channel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/channels")
@CrossOrigin
public class ChannelController {
  private final ChannelService channelService;

  @PostMapping()
  public Channel create(@RequestBody Channel channel) {
    /*
     * @RequestBody modelのフィールド変数と同名のリクエストボディ内容を自動でマッピング
     * この場合channelはChannelクラスのフィールドにリクエスト内容をセットされたインスタンスとなる
     */

    return channelService.create(channel);
  }

  @GetMapping()
  public List<Channel> findAll() {
    return channelService.findAll();
  }

  @PutMapping("/{id}")
  public Channel update(@PathVariable("id") int id, @RequestBody Channel channel) {
    channel.setId(id);
    return channel;
  }



}
