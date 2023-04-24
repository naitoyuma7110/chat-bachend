package com.naito.techpit.chat.chatbachend.application.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.naito.techpit.chat.chatbachend.domain.channels.model.Channel;
import com.naito.techpit.chat.chatbachend.domain.channels.service.ChannelDomainService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ChannelService {
  private final ChannelDomainService channelDomainService;

  public Channel create(Channel channel) {
    // TODO: Serviceを作成するまでは暫定的にリクエスト内容をそのまま返却する。
    return channelDomainService.create(channel);
  }

  public List<Channel> findAll() {
    // TODO: Serviceを作成するまでは暫定的に空のリストを返却する。
    return channelDomainService.findAll();
  }

}
