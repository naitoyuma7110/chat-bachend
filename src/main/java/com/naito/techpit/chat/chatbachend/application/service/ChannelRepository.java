package com.naito.techpit.chat.chatbachend.application.service;

import java.util.List;
import java.util.Optional;
import com.naito.techpit.chat.chatbachend.domain.channels.model.Channel;

public interface ChannelRepository {
  void insert(Channel channel);

  List<Channel> findAll();

  Optional<Integer> getMaxId();
}
