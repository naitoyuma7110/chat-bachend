package com.naito.techpit.chat.chatbachend.domain.channels.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.naito.techpit.chat.chatbachend.application.service.ChannelRepository;
import com.naito.techpit.chat.chatbachend.domain.channels.model.Channel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChannelDomainService {
  private final ChannelRepository channelRepository;

  public Channel create(Channel channel) {
    /*
     * UIからidを受け取らない仕様のためChannelモデルにはnameフィールドのみ値がある
     * 
     * 処理内容：DB情報を取得しChannelモデルに最新のidをセットしDB登録する
     */

    // Optional:null許容の型, Optional.of(1)はOptional型に1を含む(null)ではない値
    // var currentMaxId = Optional.of(1);
    var currentMaxId = channelRepository.getMaxId();

    // .orElse:nullの際のデフォルト値,この場合0を設定
    var newid = currentMaxId.orElse(0) + 1;
    channel.setId(newid);

    // DBに永続化する。
    channelRepository.insert(channel);

    return channel;
  }

  public List<Channel> findAll() {
    return Collections.emptyList();
  }

  public Channel update(Channel channel) {
    int updatedRows = channelRepository.update(channel);
    if (updatedRows == 0) {
      throw new RuntimeException("このidのレコードは見つからなかったよ" + channel.getId());
    }
    return channel;
  }
}
