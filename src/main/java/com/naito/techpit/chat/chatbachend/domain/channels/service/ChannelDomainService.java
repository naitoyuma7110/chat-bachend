package com.naito.techpit.chat.chatbachend.domain.channels.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.naito.techpit.chat.chatbachend.domain.channels.model.Channel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChannelDomainService {

  public Channel create(Channel channel) {
    // TODO: フロントエンドからIDを受け取らない仕様
    // 作成するレコードのIDはDBから取得する必要がある

    // Optional:null許容の型, Optional.of(1)はOptional型に1を含む(null)ではない値
    Optional<Integer> currentMaxId = Optional.of(1);

    // .orElse:nullの際のデフォルト値,この場合0を設定
    var newid = currentMaxId.orElse(0) + 1;
    channel.setId(newid);

    // TODO: DBに永続化する。

    return channel;
  }

  public List<Channel> findAll() {
    return Collections.emptyList();
  }
}
