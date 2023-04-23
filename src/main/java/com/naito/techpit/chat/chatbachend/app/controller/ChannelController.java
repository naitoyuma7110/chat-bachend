package com.naito.techpit.chat.chatbachend.app.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.naito.techpit.chat.chatbachend.app.domain.channels.model.Channel;

@RestController
@RequestMapping("/channels")
@CrossOrigin
public class ChannelController {

  @PostMapping()
  public Channel create(@RequestBody Channel channel) {
    /*
     * @RequestBody modelのフィールド変数と同名のリクエストボディ内容を自動でマッピング
     * この場合channelはChannelクラスのフィールドにリクエスト内容をセットされたインスタンスとなる
     */
    // TODO:チャンネルを新規作成
    return channel;
  }

  @GetMapping()
  public List<Channel> findAll() {
    // TODO: 全てのチャンネル情報を取得し返す

    // List<Channel> myList = new ArrayList<>();
    // myList.add(new Channel(1, "hello"));
    // myList.add(new Channel(2, "Hi"));
    // myList.add(new Channel(3, "sample"));
    // Collections.shuffle(myList);
    // return myList;
    return Collections.emptyList();

  }

}
